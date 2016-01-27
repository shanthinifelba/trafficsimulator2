package com.sooki.events;

import com.sooki.components.TrafficLight;

public class LightEvent  extends IEvent  {

	private TrafficLight t;
	static int event= 1;
	private int endTime;
	public LightEvent(int time, TrafficLight t)
	{
		this.time = time;
		this.t = t;
		this.endTime = this.time;
		this.t.setCurrentEventEndTime(endTime);
	
	}
	
	@Override
	public void eventHandler() {
		
		this.t.changeColor(this.time);

	}

	@Override
	public String getEventType() {
		// TODO Auto-generated method stub
		return "Light " + this.t.colorInText() + "Over" + "for" + this.t.getId();
	}
	

}
