package com.innvo.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innvo.RESTClient;
import com.innvo.RESTClientException;
import com.innvo.domain.AlertModel;
import com.innvo.domain.EventModel;



public class GetEventsHandler implements WorkItemHandler {

	RESTClient restClient = null;

	private final Logger log = LoggerFactory.getLogger(GetEventsHandler.class);

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());

	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		String startDateTime = null;
		String gatewayHostName =null;
		String alertMessage = String.valueOf(workItem.getParameter("alertmessage"));
		log.info("Alert message :" + alertMessage);
		List<EventModel> eventModels= new ArrayList<EventModel>();
		try {
			JSONObject jsonObject = new JSONObject(alertMessage);
			startDateTime = jsonObject.get("startdatetime").toString();
			gatewayHostName ="localhost:8099";
			log.info("startDateTime :" + startDateTime);
			GetEventsHandler http = new GetEventsHandler();
			eventModels = http.getEvents(startDateTime, gatewayHostName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		AlertModel alertModel = new AlertModel();
		
		alertModel.setListOfEvent(eventModels);
		params.put("alertStartDateTime", startDateTime);
		params.put("EventModel", alertModel);
		params.put("gatewayHostName",gatewayHostName);
		manager.completeWorkItem(workItem.getId(), params);

	}

	protected List<EventModel> getEvents(String startDateTime, String gatewayHostName)
			throws JsonParseException, JsonMappingException, IOException, RESTClientException, JSONException {

		restClient = new RESTClient();
		String token = restClient.getToken(gatewayHostName);

		String url = "http://" + gatewayHostName + "/adap_event/api/eventobject/" + startDateTime;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + token);

		int responseCode = con.getResponseCode();
		log.debug("\nSending 'GET' request to URL : " + url);
		log.debug("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		log.debug("API Response :" + response.toString());
			JSONArray jsonArray = new JSONArray(response.toString());
			List<EventModel> eventModel =new ArrayList<EventModel>();
			int count = jsonArray.length();
			for(int i=0 ; i< count; i++){
				jsonArray.getString(i); 
				EventModel event = new ObjectMapper().readValue(jsonArray.getString(i), EventModel.class);
				eventModel.add(event);
				log.debug("Event id : name = " + i + ": " + event.getId());
			}
		return eventModel;
	}
}