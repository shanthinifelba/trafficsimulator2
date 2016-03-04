package com.sooki.events;


import com.sooki.simulator.EventListHolder;
import com.sooki.simulator.MyProcess;

public class StopEvent extends IEvent {
	
	public StopEvent(int time) {
		// TODO Auto-generated constructor stub
		this.time = time;
	}
	@Override
	public void eventHandler() {
		// TODO Auto-generated method stub
		this.time = this.time + 1;
		StopEvent de = new StopEvent(this.time);
		MyProcess.setShouldRun(false);
		EventListHolder.getEventList().addEvent(de);
		
	}

	@Override
	public String getEventType() {
		// TODO Auto-generated method stub
		return null;
	}

}
