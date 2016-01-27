package com.sooki.components;

import java.util.concurrent.atomic.AtomicInteger;

import com.sooki.events.LightEvent;
import com.sooki.helpers.MyColor;
import com.sooki.helpers.TrafficLightConstants;
import com.sooki.simulator.EventListHolder;



public class TrafficLight extends MyNode {
	static int counter;
	int endTime;
	private MyColor current ;
	public TrafficLight()
	{
	
	}
	
	public TrafficLight(MyColor current)
	{
		this.id = counter++;
		this.current = current;
		this.name = "T";
		this.type = 1;
		
	}
	
	
	public void changeColor(int time)
	{	
		if(MyColor.RED == current)
		{
			this.current = MyColor.YELLOW;
		}
		else if(MyColor.YELLOW == current)
		{
			this.current = MyColor.GREEN;
		}
		else if(MyColor.GREEN == current)
		{
			this.current = MyColor.RED;
		}
		MyColor current_color = this.getCurrent();
		// TODO Auto-generated method stub
		AtomicInteger ai =  new AtomicInteger(time);
		EventListHolder elh = EventListHolder.getEventList();
		LightEvent new_le = null;
	
			if(MyColor.RED == current_color)
			{ // p;
				new_le = new LightEvent(ai.addAndGet(TrafficLightConstants.getDurationForSignal(MyColor.RED)),this);
				
			}
			else if(MyColor.YELLOW == current_color)
			{
				new_le = new LightEvent(ai.addAndGet(TrafficLightConstants.getDurationForSignal(MyColor.YELLOW)),this);
				
			}
			else if(MyColor.GREEN == current_color)
			{
				new_le = new LightEvent(ai.addAndGet(TrafficLightConstants.getDurationForSignal(MyColor.GREEN)),this);
				
			}
			
			if(new_le != null)
			elh.addEvent(new_le);
			
		
		
	}
	
	public MyColor getCurrent() {
		return current;
	}
	
	public String colorInText() {
		if(MyColor.RED == current)
		{
			return "Red";
		}
		else if(MyColor.YELLOW == current)
		{
			return "Yellow";
		}
		else 
		{
			return "Green";
		}
	}

	
	public int getCurrentEventEndTime() {
		return  endTime;
	}
	
	public int getId() {
		return  id;
	}
	
	
	public int setCurrentEventEndTime(int end) {
		this.endTime = end;
		return this. endTime;
	}
	
	
	public void setCurrent(MyColor current) {
		this.current = current;
	}
	public String toString()
	{
		return "T" + id;
		
	}
}
