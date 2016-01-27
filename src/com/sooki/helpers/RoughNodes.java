package com.sooki.helpers;

public class RoughNodes {
	int id;
	String name;
	int type;
	int machine;
	String machineName;
	public RoughNodes()
	{
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMachine() {
		return machine;
	}
	public void setMachine(int machine) {
		this.machine = machine;
	}
	
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	
	@Override
	public String toString() {
		return "RoughNodes [id=" + id + ", name=" + name + ", type=" + type
				+ ", machine=" + machine + "]";
	}
	
	
}
