package com.sooki.simulator;

import java.util.concurrent.PriorityBlockingQueue;

import com.sooki.events.IEvent;



public class EventListHolder {
	

	private PriorityBlockingQueue<IEvent> pendingEvenList;
	private PriorityBlockingQueue<IEvent> processedEvenList;
	private PriorityBlockingQueue<IEvent> outGoingList;
	private static EventListHolder elh;
	private EventListHolder () {
		pendingEvenList= new PriorityBlockingQueue<>();
		processedEvenList = new PriorityBlockingQueue<IEvent>();
		outGoingList = new PriorityBlockingQueue<IEvent>();
	}
	
	public static EventListHolder getEventList() {
		if(elh == null)
		{
		elh = new EventListHolder();
		}
		
		return elh;
	}
	
	public static EventListHolder ref() {

		return elh = new EventListHolder();
		
		
		
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
}
