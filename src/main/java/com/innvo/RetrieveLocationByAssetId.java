package com.innvo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveLocationByAssetId implements WorkItemHandler {
	private final Logger log = LoggerFactory.getLogger(RetrieveLocationByAssetId.class);

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		String assetIdVal = String.valueOf(workItem.getParameter("assetId"));
		long assetId = Long.parseLong(assetIdVal);
		String hostName = (String) workItem.getParameter("hostname");
		String gatewayHostName = (String) workItem.getParameter("gatewayhostname");
		List<String> statecode = null;
		RetrieveLocationByAssetId http = new RetrieveLocationByAssetId();
		try {
			statecode = http.sendGet(assetId, hostName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		AssetModel assetStatecodeValues = new AssetModel();
		assetStatecodeValues.setId(assetId);
		assetStatecodeValues.setStateCodesValues(statecode);
		params.put("assetId", assetId);
		params.put("statecodes", assetStatecodeValues);
		params.put("hostname", hostName);
		params.put("gatewayhostname", gatewayHostName);
		manager.completeWorkItem(workItem.getId(), params);
	}

	private List<String> sendGet(long assetId, String hostName) throws Exception {

		String url = "http://" + hostName + "/api/assetlocations/" + assetId;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

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
		JSONObject jsonObj = new JSONObject(response.toString());
		JSONObject jsonObj1 = null;
		JSONArray jsonArray = jsonObj.getJSONArray("locations");
		List<String> stateCodeList = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObj1 = (JSONObject) jsonArray.get(i);
			stateCodeList.add(jsonObj1.getString("statecode"));
		}
		return stateCodeList;

	}
}