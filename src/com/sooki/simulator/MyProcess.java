package com.sooki.simulator;

import java.util.concurrent.atomic.AtomicInteger;

import org.jfree.ui.RefineryUtilities;

import com.sooki.distributed.helper.Message;
import com.sooki.events.IEvent;
import com.sooki.events.VehicleEvent;
import com.sooki.main.Main;
import com.sooki.stats.DrawGraph;



public class MyProcess implements Runnable {
	AtomicInteger currentTime;
	private EventListHolder eventListHolder; 
	static int counter;
	static volatile boolean ShouldRun = true;
	public MyProcess(EventListHolder elh) {
		eventListHolder = elh;
		currentTime = new AtomicInteger();
	}
	
	public void startProcessing() {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		currentTime.set(0);
		IEvent e;
		while(ShouldRun)
		{
		// TODO Auto-generated method stub
	
		try {
			e = eventListHolder.getEventQueue().take();
			if(e != null){
				//check for recovery
				System.out.println("time" + currentTime.get());
				if(e.getTime() < currentTime.get())
				{
					System.out.println("need to recover");
					// exceu
				}
				if(currentTime.get() > 2500)
				{
					System.out.println("Test.");
					break;
					
				}
				currentTime.set(e.getTime());
			//	System.out.println(e.getTime() +  ": " + e.getEventType() );
				eventListHolder.getProcessedEventQueue().add(e);
			//	if(e instanceof VehicleEvent)
				{
				//	Message me = new Message(e,Main.machine);
				//	ElasticSearch.postToElasticQueue(me);
				}
			
				e.eventHandler();
				try{
					//Thread.sleep(50);
					}
					catch(Exception ex)
					{
						System.out.println(ex);
					}
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
	}
	
		
	}
	
	public static synchronized void setShouldRun(boolean state) {
		ShouldRun = state;
	}

	
	
	public synchronized AtomicInteger getCurrentTime() {
		return currentTime;
	}
	public synchronized void setCurrentTime(AtomicInteger currentTime) {
		this.currentTime = currentTime;
	}

	public EventListHolder getEventListHolder() {
		return eventListHolder;
	}

	public void setEventListHolder(EventListHolder eventListHolder) {
		this.eventListHolder = eventListHolder;
	}
	
	
}