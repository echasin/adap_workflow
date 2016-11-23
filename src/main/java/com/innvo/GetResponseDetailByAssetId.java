package com.innvo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetResponseDetailByAssetId implements WorkItemHandler {
	
	RESTClient restClient = null;
	private final Logger log = LoggerFactory.getLogger(GetResponseDetailByAssetId.class);

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		String assetIdVal = String.valueOf(workItem.getParameter("assetId"));
		long assetId = Long.parseLong(assetIdVal);
		String gatewayHostName = (String) workItem.getParameter("gatewayhostname");
		String assetNameVal = (String) workItem.getParameter("assetName");
		log.debug("Asset ID in GetResponseDetailByAssetId :" +assetId);
		ResponseDetailModelList responseDetailModelList = new ResponseDetailModelList();
		GetResponseDetailByAssetId http= new GetResponseDetailByAssetId();
		try {
			responseDetailModelList=http.sendGetResponseDetail(assetId,assetNameVal,gatewayHostName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assetId", assetId);
		params.put("gatewayhostname", gatewayHostName);
		params.put("responseDetailModelList", responseDetailModelList);
		manager.completeWorkItem(workItem.getId(), params);
		
	}

	private ResponseDetailModelList sendGetResponseDetail(long assetId,String assetName,String gatewayHostName) throws ClientProtocolException, RESTClientException, JSONException, IOException {
		
		restClient = new RESTClient();
		String token = restClient.getToken(gatewayHostName);

		String url = "http://" + gatewayHostName + "/adap_assessment/api/responsembrsobject/" + assetId;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty ("Authorization", "Bearer " + token);

		int responseCode = con.getResponseCode();
		log.debug("\nSending 'GET' request to URL : " + url);
		log.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseValues = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			responseValues.append(inputLine);
		}
		log.debug("Response from GetResponseDetailByAssetId :" +responseValues.toString());
		in.close();
		JSONArray jsonArray = new JSONArray(responseValues.toString());
		ResponseDetailModelList detailModelList = new ResponseDetailModelList();
		List<ResponsedetailModel> listOfResponse =new ArrayList<ResponsedetailModel>();
		ResponsedetailModel responseDetailModel= new ResponsedetailModel();
		for (int i=0;i<jsonArray.length();i++)
		{
			
			JSONObject obj1 = jsonArray.getJSONObject(i);
			responseDetailModel.setAssetId(assetId);
			responseDetailModel.setAssetName(assetName);
			responseDetailModel.setResponseId(obj1.getLong("responseId"));
			if(!obj1.isNull("questionnaireId")){
				responseDetailModel.setQuestionnaireId(obj1.getLong("questionnaireId"));
			}
			
			responseDetailModel.setQuestiongroupId(obj1.getLong("questiongroupId"));
			if(!obj1.isNull("subquestionId")){
				responseDetailModel.setSubquestionId(obj1.getLong("subquestionId"));
			}
			
			responseDetailModel.setResponse(obj1.getString("response"));
			listOfResponse.add(responseDetailModel);
		}
		log.debug("List Of Response : " +listOfResponse);
		detailModelList.setListOfResponse(listOfResponse);
		return detailModelList;
	}

}
