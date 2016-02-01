package com.sooki.components;

public class RoadSegment {
	static int edgeCount;
	
	private double capacity; // should be private
	int id;
	private double length; // should be private for good practice
	private int currentLoad; 
	
	public RoadSegment(double length, double capacity) {
	 this.id = edgeCount++; 
	 this.length = length;
	 this.capacity = capacity;
	 this.currentLoad = 0;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	
	public double getLength() {
		return length;
	}
	
	public void setLength(double length) {
		this.length = length;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public static int getEdgeCount() {
		return edgeCount;
	}
	
	public static void setEdgeCount(int edgeCount) {
		RoadSegment.edgeCount = edgeCount;
	}
	

	public void incrementCurrentLoad() {
		currentLoad = currentLoad + 1;
	}
	
	public void decrementCurrentLoad() {
		currentLoad = currentLoad - 1;
	}
	

	
	public int getCurrentLoad() {
		return currentLoad;
	}

	
	 public String toString() { // Always good for debugging
	 return "E "+ id;
	 }

}
