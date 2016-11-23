package com.innvo;

import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.drools.core.event.DebugProcessEventListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.innvo.handlers.GetEventsHandler;


@Component
public class TopicReceiver {
	
	private final Logger log = LoggerFactory.getLogger(TopicReceiver.class);
	@Autowired
	ConfigurableApplicationContext context;
	
	@JmsListener(destination = "AlertMessageQueue")
	//below method will trigger whenever a alert message is received in queue. 
	public void receiveMessage(String message) throws JMSException, JSONException, IOException, ParseException, RESTClientException {
		log.debug("TopicReceiver----Received <" + message + ">");
		log.debug("*****-----Mutiple alert message using ActiveMQ Topic----******");
		
		String messageValues =message.replace("=", ":");

		JSONObject jsonObject = new JSONObject(messageValues);
		long assetIdVal=jsonObject.getLong("asset_id");
		String  assetId = Long.toString(assetIdVal);
		log.debug("AssetID :" +assetId);
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-process");
		kSession.getWorkItemManager().registerWorkItemHandler("RetrieveAssetNameByAssetId", new RetrieveAssetNameByAssetId());
		kSession.getWorkItemManager().registerWorkItemHandler("ExecuteElasticSearchQuery", new ExecuteElasticSearchQuery());
		kSession.addEventListener(new DebugProcessEventListener());
		kSession.addEventListener(new DebugAgendaEventListener());
		kSession.addEventListener(new DebugRuleRuntimeEventListener());
		ks.getLoggers().newFileLogger(kSession, "./workflowlog");
		TopicReceiver topicReceiver=new TopicReceiver();
		String fullfilename =URLDecoder.decode(topicReceiver.getClass().getResource("/config/application-dev.yml").getFile(), "UTF-8");
		
		YamlReader reader = new YamlReader(new FileReader(fullfilename));
		Object fileContent = reader.read();
		Map map = (Map) fileContent;
		
		String gatewayHostName = map.get("gatewayhostname").toString();
		String ec2Url=map.get("ec2url").toString();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assetId", assetId);
		params.put("gatewayhostname", gatewayHostName);
		params.put("ec2url", ec2Url);
		kSession.startProcess("elasticsearchdns", params);
		kSession.fireAllRules();
		kSession.dispose();
		
		}
}
