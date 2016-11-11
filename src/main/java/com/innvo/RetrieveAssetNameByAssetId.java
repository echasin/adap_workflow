package com.innvo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RetrieveAssetNameByAssetId implements WorkItemHandler {

	RESTClient restClient = null;
	private final Logger log = LoggerFactory.getLogger(RetrieveAssetByAssetId.class);

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		String assetIdVal = String.valueOf(workItem.getParameter("assetId"));
		long assetId = Long.parseLong(assetIdVal);
		String gatewayHostName = (String) workItem.getParameter("gatewayhostname");
		String assetName = null;
		AssetModel assetModel = new AssetModel();
		RetrieveAssetNameByAssetId http = new RetrieveAssetNameByAssetId();
		try {
			assetModel = http.sendGetAssetLocation(assetId, gatewayHostName);
			assetName=assetModel.getName();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assetId", assetId);
		params.put("assetmodel", assetModel);
		params.put("gatewayhostname", gatewayHostName);
		params.put("assetName", assetName);
		manager.completeWorkItem(workItem.getId(), params);
	}

	private AssetModel sendGetAssetLocation(long assetId, String gatewayHostName) throws Exception {
		
		restClient = new RESTClient();
		String token = restClient.getToken(gatewayHostName);

		String url = "http://" + gatewayHostName + "/adap_core/api/assetname/" + assetId;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty ("Authorization", "Bearer " + token);

		int responseCode = con.getResponseCode();
		log.debug("\nSending 'GET' request to URL : " + url);
		log.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		System.out.println("Response from RetrieveAssetNameByAssetId :" +response.toString());
		in.close();
		AssetModel ob = new ObjectMapper().readValue(response.toString(), AssetModel.class);
		return ob;

	}

}
