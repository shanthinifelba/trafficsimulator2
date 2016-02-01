package com.sooki.events;

import com.sooki.components.Vehicle;
import com.sooki.simulator.EventListHolder;
import com.sooki.simulator.VehicleListHolder;

public class DrawEvent extends IEvent {
	
	 public DrawEvent(int time) {
		// TODO Auto-generated constructor stub
		 	this.time = time;
	

	}
	
	@Override
	public void eventHandler() {
		// TODO Auto-generated method stub
		
		for(Vehicle v : VehicleListHolder.getVehicleListHolder().listOfVehicles)
		{
			v.updateCarPosition();
		}
		DrawEvent de = new DrawEvent(this.time + 1);
		EventListHolder.getEventList().addEvent(de);
	}

	@Override
	public String getEventType() {
		// TODO Auto-generated method stub
		return "Draw Event";
	}

}
