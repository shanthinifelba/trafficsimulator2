package com.sooki.simulator;

import java.util.concurrent.PriorityBlockingQueue;

import com.sooki.events.IEvent;
import com.sooki.stats.AverageCaluclator;



public class EventListHolder {
	

	private PriorityBlockingQueue<IEvent> pendingEvenList;
	private PriorityBlockingQueue<IEvent> processedEvenList;
	private PriorityBlockingQueue<IEvent> outGoingList;
	private static EventListHolder elh;
	private boolean withSensor = false;
	private AverageCaluclator avg; 
		private EventListHolder (boolean withSensor) {
		pendingEvenList= new PriorityBlockingQueue<>();
		processedEvenList = new PriorityBlockingQueue<IEvent>();
		outGoingList = new PriorityBlockingQueue<IEvent>();
		avg = new AverageCaluclator();
		this.withSensor = withSensor;
	}
	
	public static EventListHolder getEventList() {
			
		return elh;
	}
	
	public static EventListHolder ref(boolean withSensor) {

		return elh = new EventListHolder(withSensor);
		
		
		
	}

	
	public  void addEvent(IEvent e)
	{
		pendingEvenList.put(e);
		
	}
	
	public void addProcessedEvent(IEvent e) {
		processedEvenList.put(e);
	}
	
	public  void  addOutGoingList(IEvent e) {
		outGoingList.put(e);
	}
	
	
	public  PriorityBlockingQueue<IEvent> getEventQueue()
	{
		return this.pendingEvenList;
	}
	
	public  PriorityBlockingQueue<IEvent> getProcessedEventQueue()
	{
		return this.processedEvenList;
	}
	
	public PriorityBlockingQueue<IEvent> getOutGoingEventQueue()
	{
		return outGoingList;
	}
	
	public AverageCaluclator getAvgCalculator() {
		return avg;
	}


}
