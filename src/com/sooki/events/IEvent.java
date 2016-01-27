package com.sooki.events;

public abstract class IEvent implements Comparable<IEvent> {

	protected int time;
	public abstract  void  eventHandler();
	public abstract String getEventType();
	static int event;
	@Override
	public int compareTo(IEvent that) {
	
		return Integer.compare(this.time, that.time);
	}
	
	public int getTime() {
		return time;
	}

}
