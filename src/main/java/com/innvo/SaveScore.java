package com.innvo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class SaveScore implements WorkItemHandler {
	public static List<ScoreModel> scoreValueList;
	RESTClient restClient = null;

	public static void addScore(ScoreModel scoreModel) {
		if (scoreValueList == null) {
			scoreValueList = new ArrayList<ScoreModel>();
		}
		scoreValueList.add(scoreModel);

	}
	
	public SaveScore() {
		// empty
	}



	private final Logger log = LoggerFactory.getLogger(SaveScore.class);

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
		}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		String gatewayHostName="localhost:8099";
		try {
			log.info("scoreVal in save score :" +scoreValueList );
			saveScore(scoreValueList, gatewayHostName);
		} catch (Exception e) {
			 log.error("Threw a Exception in SaveScore::executeWorkItem, full stack trace follows:", e);
		} 

	}
	
	public void saveScore(List<ScoreModel> score,String gatewayHostName) throws RESTClientException, IOException, JSONException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		ScoreModel element = null;
		try {
			 Iterator<ScoreModel> itr = score.iterator();
			while (itr.hasNext()) {
				element = itr.next();
				String json = ow.writeValueAsString(element);//
				log.info("JSON DATA :" + json);
				restClient = new RESTClient();
				String token = restClient.getToken(gatewayHostName);
				String response = restClient.getInstance().request(RESTClient.RestHttpMethod.POST,
						"http://" + gatewayHostName + "/adap_core/api/scores", null, json, token);
				log.info("Rest response After  Save Score" + ": " + response);
			}
		      score.clear();
		      
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

	}

}
