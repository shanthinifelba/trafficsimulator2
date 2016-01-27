package com.sooki.distributed.rabbitmq;

import java.io.IOException;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sooki.events.ControlEvent;
import com.sooki.events.VehicleEvent;
import com.sooki.main.Main;
import com.sooki.simulator.EventListHolder;
import com.rabbitmq.client.Channel;

public class MessageConsumer {
	private static final String EXCHANGE_NAME = "direct_logs";
	private static final String EXCHANGE_NAME2 = "control_message";
	private static Channel channel;
	private static  ConnectionFactory factory;
	private static  Connection connection; 

    private static Gson gson;
    private static final String MACHINE_NAME = Main.machine;
    private static  String queueName;
    private static  String queueName2;
	public static void creatMessageConsumer(EventListHolder elh) {
		try {
	    factory = new ConnectionFactory();
	    factory.setUsername(Main.RABBIT_MQ_USERNAME);
	    factory.setPassword(Main.RABBIT_MQ_PASSWORD);
	    System.out.println("username is " + Main.RABBIT_MQ_USERNAME);
	    System.out.println("password is " + Main.RABBIT_MQ_PASSWORD);
	    factory.setPort(Integer.parseInt(Main.RABBIT_MQ_PORT));
	    factory.setHost(Main.RABBIT_MQ_IP);
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	   
	    GsonBuilder builder = new GsonBuilder();
	    gson = builder.create();
	    
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
	    channel.exchangeDeclare(EXCHANGE_NAME2, "fanout");
	    queueName = channel.queueDeclare().getQueue();
	    queueName2 = channel.queueDeclare().getQueue();
	    channel.queueBind(queueName, EXCHANGE_NAME, MACHINE_NAME);
	    channel.queueBind(queueName2, EXCHANGE_NAME2, "");
	    
	    
	    System.out.println("Message Consumers Started");
	    Consumer consumer = new DefaultConsumer(channel) {
		      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope,
		                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
		    	String message = new String(body,"UTF-8");
		    //	System.out.println(message);
		    	VehicleEvent ve =  gson.fromJson(message, VehicleEvent.class);
		    	elh.addEvent(ve);
		      //  System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + ve.toString() + "'");
		      }
		    };
		    channel.basicConsume(queueName, true, consumer);
		    
		    Consumer consumer2 = new DefaultConsumer(channel) {
			      @Override
			      public void handleDelivery(String consumerTag, Envelope envelope,
			                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
			    	String message = new String(body,"UTF-8");
			 
			    	System.out.println(message);
			    	ControlEvent ce =  gson.fromJson(message, ControlEvent.class);
			       	elh.addEvent(ce);
			  //      System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + ce.toString() + "'");
			      }
			    };
			    channel.basicConsume(queueName, true, consumer);
			    channel.basicConsume(queueName2, true, consumer2);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	

}
