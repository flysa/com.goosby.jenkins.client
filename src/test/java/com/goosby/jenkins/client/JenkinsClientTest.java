package com.goosby.jenkins.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JenkinsClientTest {
	public static JenkinsClient client;
	public static String URL = "http://192.168.138.62:8081/jenkins";
	
	
	@BeforeClass
	public static void setUp(){
		client = new JenkinsClient(URL);
	}
	
	
	@Test
	public void testBuidlJob(){
		boolean result = client.buidlJob("test-git");
		assertTrue(result);
	}
	
	@Test
	public void testGetJobDetailJSON(){
		String result = client.getJobJSON("test-git");
		assertNotNull(result);
	}
	
	@Test
	public void testGetApiJson(){
		String result = client.getJenkinsApiJson();
		System.out.println(result);
		
	}
	
	@Test
	public void testGetBuildDetails(){
		String result = client.getBuildDetails("INFS_CAP_PE_INTERFACE", 2247l);
		System.out.println(result);
	}
	
	@Test
	public void testIsBuilding(){
		boolean result = client.isBuilding("MA_CPS_NJ_MRS", 1777l);
		assertTrue(result);
	}
	@Test
	public void testGetQueueJobItems(){
		String result = client.getQueueJobs();
		assertNotNull(result);
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray array = jsonObject.getJSONArray("items");
		System.out.println(array.size());
	}
	
	
	@Test
	public void testCancelQueueJob(){
		String result = client.getQueueJobs();
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray array = jsonObject.getJSONArray("items");
		assertNotNull(array);
		for(int i=0;i<array.size();i++){
			String actions = array.get(i).toString();
			JSONObject action = JSON.parseObject(actions);
			long id = action.getLongValue("id");
			client.cancelQueueJobById(id);
		}
		
	}
	@Test
	public void testGetAllJobs(){
		List<String> list = client.getAllJobs();
		assertTrue(list.size() > 0);
	}
}
