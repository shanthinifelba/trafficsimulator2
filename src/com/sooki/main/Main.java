package com.sooki.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import processing.core.PApplet;

import com.shanthini.visualization.Sketch;
import com.sooki.components.MyNode;
import com.sooki.components.TrafficLight;
import com.sooki.components.Vehicle;
import com.sooki.distributed.rabbitmq.MessageConsumer;
import com.sooki.distributed.rabbitmq.PushlishToExchange;
import com.sooki.elasticsearch.ElasticSearch;
import com.sooki.entity.RoadMap;
import com.sooki.events.CreateEvent;
import com.sooki.events.DrawEvent;
import com.sooki.events.VehicleBeginEvent;
import com.sooki.events.VehicleEvent;
import com.sooki.simulator.EventListHolder;
import com.sooki.simulator.MyProcess;
import com.sooki.simulator.VehicleListHolder;

import java.time.Instant;



public class Main {
	
	public static String machine = "machine1";
	public static String FileName = "input/grid2.json";
	public static String  runName =  "sooki";
	public static volatile boolean  start_simulation =  false;
	public static String ELASTIC_SEARCH_IP;
	public static String RABBIT_MQ_IP;
	public static String INDEX_NAME;
	public static String RABBIT_MQ_USERNAME;
	public static String RABBIT_MQ_PASSWORD;
	public static String RABBIT_MQ_PORT;
	public static Instant NOW;
	static Properties prop = new Properties();
	static InputStream input = null;
	static String env = "prod";
	public static void main(String args[])
	{
		System.out.println(runName.toString());
		PrintWriter writer;
		try {
			writer = new PrintWriter("RunName.txt", "UTF-8");
			writer.println(runName);
			writer.close();
			input = new FileInputStream("config.properties");
			prop.load(input);
			ELASTIC_SEARCH_IP = prop.getProperty(env + ".elasticsearch.ip");
			RABBIT_MQ_IP = prop.getProperty(env + ".rabbitmq.ip");
			INDEX_NAME =  prop.getProperty(env + ".elasticsearch.indexname");	
			RABBIT_MQ_USERNAME = prop.getProperty(env + ".rabbitmq.username");
			RABBIT_MQ_PASSWORD = prop.getProperty(env + ".rabbitmq.password");
			RABBIT_MQ_PORT = prop.getProperty(env + ".rabbitmq.port");
					
			System.out.println(ELASTIC_SEARCH_IP);
			System.out.println(RABBIT_MQ_IP);
			System.out.println(INDEX_NAME);
			System.out.println(RABBIT_MQ_PORT);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// load a properties file
	

		// get the property value and print it out
	
	
		ArrayList<TrafficLight> listOfTrafficLights = RoadMap.getRoadMap().getlistOfTrafficLight();
	
		EventListHolder elh = EventListHolder.getEventList();
		MyProcess p = new MyProcess(elh); 
	//	PushlishToExchange.connectRabbitMQ();
	//	MessageConsumer.creatMessageConsumer(elh);
		
	
		
		
		ArrayList<MyNode> destinNodes = RoadMap.getRoadMap().getListOfDestination();
		ArrayList<MyNode> listOfLocalPlaces = RoadMap.getRoadMap().getListOfLocalPlaces();
		System.out.println(listOfLocalPlaces.get(1));
		System.out.println( RoadMap.getRoadMap().getListOfAssociatedNodes(listOfLocalPlaces.get(1)));
		
		ElasticSearch.EstablishConnection();
		//for(MyNode m : listOfLocalPlaces)
		//	ProcessRoutingTable.addEntry(m,elh);
		
		// create event will add to event List automatically
		
		
	
		System.out.println("StartedSimulating");
	//	Sketch.main(new String[] { "com.shanthini.visualization.Sketch" });
		p.startProcessing();
	//	while(start_simulation == false)
		{
			//do nothing
		}
		NOW = Instant.now();
		System.out.println(listOfLocalPlaces.get(0));
		System.out.println(listOfLocalPlaces.get(3));
		
		for(int i=0;i< 1;i++)
		{
			int timeForVehicle = 1;
			CreateEvent ce = new CreateEvent(0);
			Random rn = new Random();
			int Low = 50;
			int High = 100;
			int velocity = rn.nextInt(High-Low) + Low;
			
			int des = rn.nextInt(destinNodes.size());
			int start = rn.nextInt(listOfLocalPlaces.size());
			
			Vehicle v = new Vehicle(velocity, listOfLocalPlaces.get(0), listOfLocalPlaces.get(3),timeForVehicle);
			EventListHolder.getEventList().addEvent(new DrawEvent(1));
			VehicleListHolder.getVehicleListHolder().listOfVehicles.add(v);
			
			VehicleBeginEvent ve = new VehicleBeginEvent(timeForVehicle, v);
			
			elh.addEvent(ce);
			elh.addEvent(ve);
		
		}
		

	
		/*
		CreateEvent ce2 = new CreateEvent(2);
		timeForVehicle = 25;
		Vehicle v1 = new Vehicle(20, allNodes.get(0), allNodes.get(3),timeForVehicle);
		VehicleEvent ve2 = new VehicleEvent(timeForVehicle, v1);
		elh.addEvent(ve2);
		elh.addEvent(ce);
		elh.addEvent(ce2);

		LightEvent le = new LightEvent(18, listOfTrafficLights.get(0));
		LightEvent le2 = new LightEvent(122, listOfTrafficLights.get(1));
		elh.addEvent(le);
		elh.addEvent(le2);
		p.startProcessing();
		*/
		
	}
	


}
