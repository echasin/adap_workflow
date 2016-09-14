package com.innvo;

import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.innvo.domain.AlertModel;
import com.innvo.domain.CSVBeanWriter;
import com.innvo.domain.EventModel;
import com.innvo.domain.OutputCSVModel;
import com.innvo.handlers.GetEventsHandler;

@Component
public class Receiver {
	public static List<OutputCSVModel> eventList;
	
	private final Logger log = LoggerFactory.getLogger(Receiver.class);

	/**
	 * Get a copy of the application context
	 */
	@Autowired
	ConfigurableApplicationContext context;

	/**
	 * When you receive a Alert message, check it and get corresponding correlated events and convert to csv.
	 * Finally, clean all events and listen to next alert message.
	 * @throws ParseException 
	 * @throws RESTClientException 
	 */
	@JmsListener(destination = "AlertMessageQueue")
	//below method will trigger whenever a alert message is received in queue. 
	public void receiveMessage(String message) throws JMSException, JSONException, IOException, ParseException, RESTClientException {
		log.debug("Receiver------Received <" + message + ">");
		
		String messageValues =message.replace("=", ":");

		JSONObject jsonObject = new JSONObject(messageValues);
		String  alertCategory = jsonObject.getString("category");
		String  alertSubCategory = jsonObject.getString("subcategory");
		String  alertSubtype = jsonObject.getString("subtype");
		String  alertType = jsonObject.getString("type");
		String  alertName = jsonObject.getString("name");

		// gateway conditions to check
		if (!alertCategory.contains("system") && alertSubCategory.contains("database") && alertSubtype.contains("I/O")
				&& alertType.contains("secs/read") && alertName.contains("Greater than 600ms")) {
			log.debug("Alert message doesnt match required conditions");
			return;
		}
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("/process/workflowevent.drl", Receiver.class), ResourceType.DRL);
		if (kbuilder.hasErrors()) {
			System.err.println(kbuilder.getErrors().toString());
		}
		
		//Events in each stream must be time-ordered
		//When using the STREAM, the engine knows the concept of flow of time and the concept of "now", 
		//i.e., the engine understands how old events are based on the  timestamp.
		KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		config.setOption(EventProcessingOption.STREAM);

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(config);
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		//Entry points are declared implicitly in Drools by directly making use of them in rules. 
		//I.e. referencing an entry point in a rule will make the engine, at compile time, 
		//to identify and create the proper internal structures to support that entry point.
		WorkingMemoryEntryPoint AlertEntryPoint = ksession.getWorkingMemoryEntryPoint("entryOneAlert");
		WorkingMemoryEntryPoint EventsEntryPoint = ksession.getWorkingMemoryEntryPoint("entryTwoEvents");
		
		String startDateTime = jsonObject.getString("startdatetime");
		
		Receiver receiver=new Receiver();
		String fullfilename =URLDecoder.decode(receiver.getClass().getResource("/config/application-dev.yml").getFile(), "UTF-8");
		
		YamlReader reader = new YamlReader(new FileReader(fullfilename));
		Object fileContent = reader.read();
		Map map = (Map) fileContent;
		
		String gatewayHostName = map.get("gatewayhostname").toString();
		GetEventsHandler http = new GetEventsHandler();
		
		//getting list of events by REST api call
		List<EventModel> listOfEvent= http.getEvents(startDateTime, gatewayHostName);
		
		//inserting events into EventsEntryPoint
		    for(EventModel event:listOfEvent){
		    EventsEntryPoint.insert(event);
		}
		
		AlertModel alert=new AlertModel();
		
		alert.setId(jsonObject.getLong("id"));
		alert.setName(jsonObject.getString("name"));
		alert.setDescription(jsonObject.getString("description"));
		alert.setCategory(jsonObject.getString("category"));
		alert.setSubcategory(jsonObject.getString("subcategory"));
		alert.setSubtype(jsonObject.getString("subtype"));
		alert.setType(jsonObject.getString("type"));
		alert.setEnddatetime(jsonObject.getString("enddatetime"));
		alert.setStatus(jsonObject.getString("status"));
		alert.setLastmodifiedby(jsonObject.getString("lastmodifiedby"));
		alert.setLastmodifieddatetime(dateFormater(jsonObject.getString("lastmodifieddatetime")));
		alert.setDomain(jsonObject.getString("domain"));
		alert.setStartdatetime(dateFormater(jsonObject.getString("startdatetime")));
		
		//inserting alert into AlertEntryPoint
		AlertEntryPoint.insert(alert);
		
		ksession.fireAllRules();
		
		DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String currentTimeStamp = timeStampPattern.format(java.time.LocalDateTime.now());
		String csvFileName = "correlated_Events_"+ alert.getId()+"_"+currentTimeStamp+".csv";
		
		CSVBeanWriter writer=new CSVBeanWriter();
		//calling csv bean writter to store list of correlated events in csv file
		writer.writeCSVFile(csvFileName, eventList);
		eventList.clear();
	}
	public Date dateFormater(String startdatetime) throws ParseException{
		ZonedDateTime date = ZonedDateTime.parse(startdatetime);
		Date stdDate = Date.from(date.toInstant());
		SimpleDateFormat stdDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String myTime=stdDateFormat.format(stdDate);
		String replaceZone = myTime.replace("Z", "");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date exactDate = simpleDateFormat.parse(replaceZone);
		return exactDate;
	}
	public static void addevent(OutputCSVModel outputCsvModelEvent) {
		if (eventList == null) {
			eventList = new ArrayList<OutputCSVModel>();
		}
		eventList.add(outputCsvModelEvent);
	}
}
