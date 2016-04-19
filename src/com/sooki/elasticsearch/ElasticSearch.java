package com.sooki.elasticsearch;

import java.io.IOException;

import com.sooki.distributed.helper.Message;
import com.sooki.events.VehicleEvent;
import com.sooki.main.Main;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;


public class ElasticSearch {
	private static JestClientFactory factory; /*using JEST API to talk with Elastic search java API */
	private static  JestClient client;
	private static final String INDEX_NAME_VEHCILE = Main.INDEX_NAME;
	private static final String ELASTIC_SEARCH_IP = Main.ELASTIC_SEARCH_IP;
	private static final String INDEX_NAME_ROLLBACK = Main.INDEX_NAME;
	
	public static void EstablishConnection() {
		
		factory = new JestClientFactory();
		System.out.println("The elastic search ip is" + ELASTIC_SEARCH_IP);
        factory.setHttpClientConfig(new HttpClientConfig.Builder(ELASTIC_SEARCH_IP)
                .multiThreaded(true)
                .build());
        client = factory.getObject();
      System.out.println("Elastic search service started");
        
	}
	
	
	
	public static void postToElasticQueue(Message  me) {
		
		 Index index = new Index.Builder(me).index(INDEX_NAME_VEHCILE).type(Main.runName).build();
		 System.out.println("executing");
	         try {
				client.execute(index);
			} catch (IOException IO) {
				// TODO Auto-generated catch block
				IO.printStackTrace();
			} 
			
	}
	public static void postToElasticQueue(VehicleEvent ve) {
		
		 Index index = new Index.Builder(ve).index(INDEX_NAME_VEHCILE).type(Main.runName).build();
		 System.out.println("executing");
	         try {
				client.execute(index);
			} catch (IOException IO) {
				// TODO Auto-generated catch block
				IO.printStackTrace();
			} 
			
	}
	
	public static void postToElasticQueue(String rollback) {
		 Index index = new Index.Builder(rollback).index(INDEX_NAME_ROLLBACK).type(Main.runName).build();
		 System.out.println("executing");
	         try {
				client.execute(index);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
	}

}
