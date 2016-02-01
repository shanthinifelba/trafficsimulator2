package com.sooki.components;

public class Sensor {
	private int x;
	private int y;
	private RoadSegment rs;
	public Sensor(RoadSegment rs) {
		// TODO Auto-generated constructor stub
		this.rs = rs;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public RoadSegment getRs() {
		return rs;
	}

	public void setRs(RoadSegment rs) {
		this.rs = rs;
	}
	
	
}
