package com.sooki.events;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.sooki.components.MyNode;
import com.sooki.components.Vehicle;
import com.sooki.entity.RoadMap;
import com.sooki.main.Main;
import com.sooki.simulator.EventListHolder;
import com.sooki.simulator.VehicleListHolder;


 
public class CreateEvent extends IEvent{
	
	static int event= 0;

	

	 public CreateEvent(int time) {
		// TODO Auto-generated constructor stub
		 	this.time = time;
	

	}

	 @Override
	 public  String getEventType(){
		
		 return  "CreateEvent";
	 }
	 
	 
	@Override
	public void eventHandler() {
		// TODO Auto-generated method stub
		AtomicInteger ai =  new AtomicInteger(this.time);
		int time = ai.addAndGet(2);
		Random r = new Random();
		int velocity = r.nextInt(120);
	//	CreateEvent ce = new CreateEvent(time);
		int timeForVehicle = time + 1;
		ArrayList<MyNode> destinNodes = RoadMap.getRoadMap().getListOfLocalPlaces();
		ArrayList<MyNode> localNodes = RoadMap.getRoadMap().getListOfLocalPlaces();
		Random rn = new Random();
		
		int des = rn.nextInt(localNodes.size());
		int start = rn.nextInt(localNodes.size());
		
	//	Vehicle v = new Vehicle(velocity, localNodes.get(start), destinNodes.get(des),timeForVehicle+1);
		
	//	VehicleListHolder.getVehicleListHolder().listOfVehicles.add(v);
		
	//	CreateEvent ce = new CreateEvent(timeForVehicle+1);
	//	VehicleEvent ve = new VehicleEvent(timeForVehicle+1, v);
		
		EventListHolder elh = EventListHolder.getEventList();
		for(int i=0; i<Main.Generation_rate;i++)
		{
		//	elh.addEvent(ce);
		//	elh.addEvent(ve);
		}
		
		
	//	System.out.println("Create event handler called");
	}



}
