package com.sooki.events;

import com.sooki.main.Main;
import com.sooki.simulator.MyProcess;

public class ControlEvent  extends IEvent {
	boolean should_run = false;
	String nameOfSimulation;
	
	public ControlEvent(boolean should_run, String nameOfSimulation) {
		super();
		this.should_run = should_run;
		this.nameOfSimulation = nameOfSimulation;
	}

	public boolean getStart_stop() {
		return should_run;
	}

	public void setStart_stop(boolean should_run) {
		this.should_run = should_run;
	}

	public String getNameOfSimulation() {
		return nameOfSimulation;
	}

	public void setNameOfSimulation(String nameOfSimulation) {
		this.nameOfSimulation = nameOfSimulation;
	}

	@Override
	public void eventHandler() {
		// TODO Auto-generated method stub
		if(this.should_run == false)
		{
		 MyProcess.setShouldRun(false);
		 System.out.println("Recieved Stop Event");
		}
		else {
			 MyProcess.setShouldRun(true);
			 Main.runName = this.nameOfSimulation;
			System.out.println("Started" + this.nameOfSimulation);
			Main.start_simulation = true;
		}

	}

	@Override
	public String getEventType() {
		// TODO Auto-generated method stub
		return "ControlEvent";
	}

}
