package com.sooki.events;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sooki.components.Vehicle;



public class VehicleEvent extends IEvent {

	static int event= 2;
	private Vehicle v;
	
	String message = "travelling";


	public VehicleEvent(int time, Vehicle v)
	{
		this.time = time;
		this.v = v;
	}

	@Override
	public void eventHandler() {
		// TODO Auto-generated method stub
		this.toJson();
	//	v.scheduleFutureEvent();
	
		
	}
	 @Override
	 public  String getEventType(){
			
		 return  "VehicleEvent" + " for " + this.v.getId();
	 }
	 

	@Override
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void toJson() {
		  GsonBuilder builder = new GsonBuilder();
	      Gson gson = builder.create();
	//   System.out.println(gson.toJson(this));
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
