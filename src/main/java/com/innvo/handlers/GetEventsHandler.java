package com.innvo.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innvo.RESTClient;
import com.innvo.RESTClientException;
import com.innvo.domain.EventModel;



public class GetEventsHandler{

	RESTClient restClient = null;

	private final Logger log = LoggerFactory.getLogger(GetEventsHandler.class);

	public List<EventModel> getEvents(String startDateTime, String gatewayHostName)
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
			List<EventModel> listOfEvent =new ArrayList<EventModel>();
			for(int i=0 ; i< jsonArray.length(); i++){
				EventModel event = new ObjectMapper().readValue(jsonArray.getJSONObject(i).toString(), EventModel.class);
				listOfEvent.add(event);
				log.debug("Event(" + i + ")id:=  " + event.getId());
			}
		return listOfEvent;
	}
}