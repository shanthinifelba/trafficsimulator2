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
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.jfree.ui.RefineryUtilities;

import processing.core.PApplet;

import com.shanthini.visualization.Sketch;
import com.shanthini.visualization.Visualisation;
import com.shanthini.visualization.VisualisationA;
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
	public static String FileName = "input/Grids.json";
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
	public static int type = 1;
	public static volatile int Generation_rate = 20;
	@SuppressWarnings("restriction")
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

		
		ElasticSearch.EstablishConnection();
		//for(MyNode m : listOfLocalPlaces)
		//	ProcessRoutingTable.addEntry(m,elh);
		
		// create event will add to event List automatically
		
		
	
		System.out.println("StartedSimulating");
		//Sketch.main(new String[] { "com.shanthini.visualization.Sketch" });
	

	//	while(start_simulation == false)
		{
			//do nothing
		}
	
		//Visualisation.launch(Visualisation.class);
	//	new VisualisationA();
		
		NOW = Instant.now();
	
		p.startProcessing();
		for(int i=0;i< 10000;i++)
		{
			int timeForVehicle = i/Generation_rate;
			CreateEvent ce = new CreateEvent(timeForVehicle);
			Random rn = new Random(0);
			int Low = 80;
			int High = 100;
			int velocity = rn.nextInt(High-Low) + Low;
			
			int des = rn.nextInt(listOfLocalPlaces.size()) ;
			int start = rn.nextInt(listOfLocalPlaces.size()) ;
		//	System.out.println("the numbers were" + des + " " + start);
			
			Vehicle v = new Vehicle(velocity, listOfLocalPlaces.get(start), listOfLocalPlaces.get(des),timeForVehicle );
			
			VehicleListHolder.getVehicleListHolder().listOfVehicles.add(v);
			
			VehicleBeginEvent ve = new VehicleBeginEvent(timeForVehicle, v);
			
			elh.addEvent(ce);
			elh.addEvent(ve);
			System.out.println("adding cars");
		}
		
	
		System.out.println("wait for user input 2");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println("got user input");
		type = 2;
		System.out.println("type 2 is set, can i start second simulation");
		sc.nextLine();
		EventListHolder elh2 = EventListHolder.ref();
		MyProcess p2 = new MyProcess(elh2); 
		p2.startProcessing();
		
		for(int i=0;i< 10000;i++)
		{
			int timeForVehicle = i/Generation_rate;
			CreateEvent ce = new CreateEvent(timeForVehicle);
			Random rn = new Random(0);
			int Low = 50;
			int High = 100;
			int velocity = rn.nextInt(High-Low) + Low;
			
			int des = rn.nextInt(listOfLocalPlaces.size()) ;
			int start = rn.nextInt(listOfLocalPlaces.size()) ;
		//	System.out.println("the numbers were" + des + " " + start);
			
			Vehicle v = new Vehicle(velocity, listOfLocalPlaces.get(start), listOfLocalPlaces.get(des),timeForVehicle );
			
			VehicleListHolder.getVehicleListHolder().listOfVehicles.add(v);
			
			VehicleBeginEvent ve = new VehicleBeginEvent(timeForVehicle, v);
			
			elh2.addEvent(ce);
			elh2.addEvent(ve);
		
		}
		System.out.println("Running next Simulation");
		sc.nextLine();
		
		System.out.println("Going to draw graph");
		final DrawGraph demo = new DrawGraph("Comparsion Sensor data input");
	    demo.pack();
	    RefineryUtilities.centerFrameOnScreen(demo);
	     demo.setVisible(true);
	        
		System.out.println("Waiting for user input for graphs");
		sc.nextLine();
		
		StopEvent stopEvent = new StopEvent(50000);
		EventListHolder.getEventList().addEvent(stopEvent);
		
		System.out.println("Exited ");
		sc.close();
	//	EventListHolder.getEventList().addEvent(new DrawEvent(2));
		System.out.println("The delay 1 is " + StatsHolder.getDelay1());
		System.out.println("The delay 2 is " + StatsHolder.getDelay2());
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
