package com.sooki.stats;

public class AverageCaluclator {
	static int counter = 0;
	double average = 0;
	
	public void average(double val)
	{
		average = (average * counter + val ) / (counter + 1);
	}
	
	public double getaverage()
	{
		return average;
	}


}
