package com.innvo.resources.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;

public class ElasticSearchAPIRuntimeMemory {

	private static final String GET_URL = "http://ec2-52-54-140-219.compute-1.amazonaws.com:9200/capstone/dns/_search?q=source_computer:C4653&size=";
	private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
            return bytes / MEGABYTE;
    }
    /*public static void main(String args[]) throws IOException, JSONException, ParseException {
    	
    	getEvents(1000,1);
    	getEvents(2000,2);
    	getEvents(3000,3);
    	getEvents(4000,4);
    	

   }*/

	private static void getEvents(int splitTotal,int time ) throws IOException, ParseException {
		String beforeResponse=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
		System.out.println("Before API Response  : " +beforeResponse);
		CloseableHttpClient httpClient1 = HttpClients.createDefault();
		HttpGet httpGet1 = new HttpGet(GET_URL + splitTotal);
		CloseableHttpResponse httpResponse1 = httpClient1.execute(httpGet1);
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(httpResponse1.getEntity().getContent()));
		String inputLine1;
		StringBuffer response1 = new StringBuffer();
		while ((inputLine1 = reader1.readLine()) != null) {
			response1.append(inputLine1);
		}
		reader1.close();
		System.out.println("Getting the DNS events for " +time+ "k");
		String afterResponse=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
		System.out.println("After API Response : " +afterResponse);
		SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date d1 = format.parse(beforeResponse);
		Date d2 = format.parse(afterResponse);
		long diffResponse = d2.getTime() - d1.getTime();
		long diffMinutes = diffResponse / (60 * 1000) % 60;
		long diffSeconds = diffResponse / 1000 % 60;
		System.out.println("Difference between before and after response in minutes and seconds:" +diffMinutes+"mins" +":" +diffSeconds+"secs");
		Runtime runtime = Runtime.getRuntime();
        runtime.gc();
		long usageMemory=runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Total Memory :" +runtime.totalMemory());
		System.out.println("Free Memory :" +runtime.freeMemory());
		System.out.println("Usage Memory : "+usageMemory);
		System.out.println("Used memory is bytes: " + usageMemory);
        System.out.println("Used memory is megabytes: "
                         + bytesToMegabytes(usageMemory));
		System.out.println("=================================================");		
	}
}
