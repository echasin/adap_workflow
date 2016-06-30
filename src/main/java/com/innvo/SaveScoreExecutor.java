package com.innvo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.innvo.security.jwt.TokenProvider;



public class SaveScoreExecutor {
	@Inject
	private AuthenticationManager authenticationManager;

	@Inject
	private TokenProvider tokenProvider;
	
	@Autowired
	private HttpServletRequest request;
	JSONObject jsonObject;

	private final Logger log = LoggerFactory.getLogger(SaveScoreExecutor.class);

	RESTClient restClient = null;

	public void saveScore(ScoreModel score,String gatewayHostName) throws RESTClientException, IOException, JSONException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(score);//
			log.info("JSON DATA :" + json);
			restClient = new RESTClient();
			String token = restClient.getToken(gatewayHostName);
			String response = restClient.getInstance().request(RESTClient.RestHttpMethod.POST,
					"http://"+gatewayHostName+"/adap_core/api/scores", null, json,token);
			log.info("Rest response After  Save Score" + ": " + response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}

	}
	

}