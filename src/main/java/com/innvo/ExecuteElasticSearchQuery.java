package com.innvo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteElasticSearchQuery implements WorkItemHandler {

	private final Logger log = LoggerFactory.getLogger(ExecuteElasticSearchQuery.class);

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		String assetIdVal = String.valueOf(workItem.getParameter("assetId"));
		long assetId = Long.parseLong(assetIdVal);
		String assetName = String.valueOf(workItem.getParameter("assetName"));
		String ec2UrlVal = (String) workItem.getParameter("ec2url");
		String ec2Url=ec2UrlVal.replace("{", "").replace("}", "");
		log.debug("Asset Name in ExecuteElasticSearchQuery :" + assetName);
		log.debug("Ec2Url in ExecuteElasticSearchQuery :" + ec2Url);
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet("http://" +ec2Url + assetName);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

			System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());

			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();

			JSONObject obj = new JSONObject(response.toString());
			JSONObject totalHits = obj.getJSONObject("hits");
			int total = totalHits.getInt("total");
			String totalSize = String.valueOf(total);
			log.debug("Total Size :" + totalSize);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("assetId", assetId);
			params.put("assetName", assetName);
			params.put("totalSize", totalSize);
			manager.completeWorkItem(workItem.getId(), params);

			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
