package com.sooki.distributed.helper;


import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import com.sooki.events.IEvent;
import com.sooki.main.Main;

public class Message {
	IEvent event;
	String machine;
	UUID uuid;
	long time_elapsed;
	public Message(IEvent event,String machine) {
		this.event = event;
		this.uuid = UUID.randomUUID();
		this.machine = machine;
//		this.time_elapsed = Duration.between(Main.NOW,Instant.now()).toMillis()/1000;
	}
	

}
