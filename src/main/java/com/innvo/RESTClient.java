package com.innvo;

import static com.innvo.RESTClient.RestHttpMethod.DELETE;
import static com.innvo.RESTClient.RestHttpMethod.GET;
import static com.innvo.RESTClient.RestHttpMethod.POST;
import static com.innvo.RESTClient.RestHttpMethod.PUT;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
//import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RESTClient {

	public static final String NAME = "RESTClient";

	private final Logger log = LoggerFactory.getLogger(RESTClient.class);

	protected boolean failOnUnknowProperties = false;

	public enum RestHttpMethod {
		GET, POST, PUT, DELETE
	}

	/**
	 * get instance
	 * 
	 * @return
	 */
	public static RESTClient getInstance() {
		return new RESTClient();
	}

	/**
	 * executes REST http request for given httpMethod and urls
	 * 
	 * @param restHttpMethod
	 * @param url
	 * @return
	 * @throws RESTClientException
	 */
	public String request(RestHttpMethod restHttpMethod, String url, Map<String, String> params, String body,
			String token) throws RESTClientException {
		CloseableHttpClient httpClient = getHttpClient();
		HttpRequestBase requestMethod = getRequestMethod(restHttpMethod, url, body);

		requestMethod.setHeader("Authorization", "Bearer " + token);

		// String response = null;
		log.debug("request - method=" + restHttpMethod + ", url=" + url);
		String response = "";
		CloseableHttpResponse responseStatus = null;
		try {
			responseStatus = httpClient.execute(requestMethod);

			try {
				ResponseHandler<String> handler = new BasicResponseHandler();
				response = handler.handleResponse(responseStatus);

			} finally {
				responseStatus.close();
			}

			// response = sb.toString();
			if (responseStatus.getStatusLine().getStatusCode() != HttpStatus.SC_OK
					&& responseStatus.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
				log.error("REST call failed with response code " + responseStatus.getStatusLine().getStatusCode());
				throw new RESTClientException(response.toString());
			}

		} catch (Throwable t) {
			throw new RESTClientException(t);
		} finally {
			requestMethod.releaseConnection();
		}

		log.debug("response=" + response);
		return response;
	}

	/**
	 * executes REST http request for given httpMethod and urls
	 * 
	 * @param restHttpMethod
	 * @param url
	 * @return
	 * @throws RESTClientException
	 * @throws JSONException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String getToken(String hostName) throws RESTClientException, JSONException, ClientProtocolException, IOException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", "admin");
		jsonObject.put("password", "admin");

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		    HttpPost request = new HttpPost("http://127.0.0.1:8099/api/authenticate");
		    StringEntity params = new StringEntity(jsonObject.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    HttpResponse response=httpClient.execute(request);
			System.out.println("Post parameters : " + response.getEntity());
			System.out.println("Response Code : " + 
					response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println("Token :" +result.toString());
			


		JSONObject jsonObj = new JSONObject(result.toString());

		log.debug("response=" + result.toString());

		String token = jsonObj.getString("id_token");
		log.debug("token =" + token);
		return token;
	}

	/**
	 * return new instance of http client
	 * 
	 * @return
	 */
	protected CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	/**
	 * prepare and return apache httpMethod for given httpMethod
	 * 
	 * @param restHttpMethod
	 * @param url
	 * @return
	 */
	protected HttpRequestBase getRequestMethod(RestHttpMethod restHttpMethod, String url, String body) {
		HttpRequestBase requestMethod = null;
		if (restHttpMethod == POST) {
			requestMethod = new HttpPost(url);

			if (body != null) {
				try {
					HttpEntity entity = new ByteArrayEntity(body.getBytes("UTF-8"));
					((HttpPost) requestMethod).setEntity(entity);
				} catch (UnsupportedEncodingException e) {
					log.error("error while setting the entity body " + e.getMessage(), e);
				}
			}
		} else if (restHttpMethod == PUT) {
			requestMethod = new HttpPut(url);
		} else if (restHttpMethod == DELETE) {
			requestMethod = new HttpDelete(url);
		} else {
			requestMethod = new HttpGet(url);
		}

		requestMethod.setHeader("Content-Type", "application/json");
		requestMethod.setHeader("accept", "application/json");

		;

		return requestMethod;
	}

	/**
	 * perform GET request and return json String
	 * 
	 * @param url
	 * @return
	 * @throws RESTClientException
	 */
	public String getJson(String url, String token) throws RESTClientException {
		return request(GET, url, null, null, token);
	}

	/**
	 * get new instance of objectMapper
	 * 
	 * @return
	 */
	/*protected ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}*/

	/**
	 * set to ignore or throw errors when ignoring properties
	 * 
	 * @param ignoreUnknownProperties
	 */
	public void setFailOnUnknowProperties(boolean failOnUnknowProperties) {
		this.failOnUnknowProperties = failOnUnknowProperties;
	}

}
