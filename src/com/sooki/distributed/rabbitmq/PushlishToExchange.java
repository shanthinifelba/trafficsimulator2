package com.sooki.distributed.rabbitmq;

import java.io.IOException;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sooki.events.VehicleEvent;
import com.sooki.main.Main;

public class PushlishToExchange {
	
	private static final String EXCHANGE_NAME = "direct_logs";
	private static Channel channel;
	private static  ConnectionFactory factory;
	private static  Connection connection; 
	private static GsonBuilder builder;
    private static Gson gson;
	public static void connectRabbitMQ () {
		try {
			builder = new GsonBuilder();
			gson = builder.create();
			factory = new ConnectionFactory();
		    factory.setUsername(Main.RABBIT_MQ_USERNAME);
		    factory.setPassword(Main.RABBIT_MQ_PASSWORD);
		    factory.setPort(Integer.parseInt(Main.RABBIT_MQ_PORT));
		    factory.setHost(Main.RABBIT_MQ_IP);
		    connection = factory.newConnection();
		    channel = connection.createChannel();
		    System.out.println("sooki");
		    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void sendVehicleEventToExchange(VehicleEvent v,String machineToForward)  {
		 try {
			  System.out.println("Message Sender Started");
			 
			 channel.basicPublish(EXCHANGE_NAME, machineToForward, null,  gson.toJson(v).getBytes("UTF-8"));
		
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}
	
	public static void sendStorageEvent(VehicleEvent v) 
	{
		 try {
		//channel.basicPublish(EXCHANGE_NAME, "elasticsearch", null,  gson.toJson(v).getBytes("UTF-8"));
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void disconnectRabbitMQ()  {
		 try {
			 channel.close();
			 connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
	}

}
