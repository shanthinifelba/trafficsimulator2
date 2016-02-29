package com.sooki.simulator;


import java.util.concurrent.CopyOnWriteArrayList;

import com.sooki.components.Vehicle;


public class VehicleListHolder {
	public CopyOnWriteArrayList<Vehicle> listOfVehicles;
	
	static VehicleListHolder vehicleList;
	private VehicleListHolder()
	{
		listOfVehicles = new CopyOnWriteArrayList<Vehicle>();
	}
	
	public static VehicleListHolder getVehicleListHolder() 
	{
		if(vehicleList == null)
			vehicleList = new VehicleListHolder();
		return vehicleList;
	}
}
