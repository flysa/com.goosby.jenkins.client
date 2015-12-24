package com.goosby.jenkins.httpclient;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {
	
	
	public static String getHeader(String url,String headerName){
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet method = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(method);
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				Header[] headers = response.getHeaders(headerName);
				System.out.println(headers);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url,Map<String,String> params){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet method = new HttpGet(url);
		HttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(method);
			if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				entity = response.getEntity();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			method.abort();
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		return entity.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @param xmlBody
	 * @return
	 */
	public static int post(String url,String xmlBody){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost method = new HttpPost(url);
		StringEntity entity = null;
		HttpResponse response = null;
		try {
			if(null != xmlBody && !"".equals(xmlBody)){
				entity = new StringEntity(xmlBody,ContentType.create("text/xml", "utf-8"));
				method.setEntity(entity);
			}
			response = httpClient.execute(method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int code = response.getStatusLine().getStatusCode();
		System.out.println("---- -- :" + code);
		return code;
		
	}
	
	
}
