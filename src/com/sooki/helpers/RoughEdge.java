package com.sooki.helpers;

public class RoughEdge {
	int source;
	int target;
	boolean directed;
	int length;
	int capacity;
	public RoughEdge()
	{
		
	}
	
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public boolean isDirected() {
		return directed;
	}
	public void setDirected(boolean directed) {
		this.directed = directed;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "RoughEdge [source=" + source + ", target=" + target
				+ ", directed=" + directed + ", length=" + length
				+ ", capacity=" + capacity + "]";
	}
	
}
