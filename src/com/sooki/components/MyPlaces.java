package com.sooki.components;



public class MyPlaces extends MyNode {
	
	static int counter;
	public MyPlaces()
	{
		
	}
	public MyPlaces(String a)
	{
		this.name = a;
		this.id = counter++;
		this.type = 0;
		
	}
	
	public String toString()
	{
		return this.name;
		
	}


}
