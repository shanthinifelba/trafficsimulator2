package com.sooki.stats;

import java.util.HashMap;
import java.util.TreeMap;

public class StatsHolder {
	static TreeMap<Integer, Integer> hp = new TreeMap<Integer,Integer>();
	static TreeMap<Integer, Integer> hp2 = new TreeMap<Integer,Integer>();
	public static TreeMap<Integer, Double> withSensorAverage = new TreeMap<Integer,Double>();
	public static TreeMap<Integer, Double> withOutSensorAverage = new TreeMap<Integer,Double>();

	static double delay1 = 0;
	static double delay2 = 0;
	static int counter_for_run = 0;
	static double average = 0;
	public static void addToHashMap(int time)
	{
		if( hp.get(time) == null )
		{
			hp.put(time, 1);
		}
		else {
			int value = hp.get(time);
			value = value + 1;
			if(value < 1)
			System.out.println("THe value" + value);
			hp.put(time, value);
		}
	}
	
	public static void addToHashMap2(int time)
	{
		if( hp2.get(time) == null )
		{
			hp2.put(time, 1);
		}
		else {
			int value = hp2.get(time);
			value = value + 1;
			hp2.put(time, value);
		}
	}
	
	public static void addToHashMapAverage(Double avg)
	{
		if( withSensorAverage.get(counter_for_run) == null )
		{
			withSensorAverage.put(counter_for_run, avg);
		}
	}
	
	public static void addToHashMapAverage2(Double avg)
	{
		if( withOutSensorAverage.get(counter_for_run) == null )
		{
			withOutSensorAverage.put(counter_for_run, avg);
		}
		counter_for_run++;
	}
	
	
	public static void delay1(double val)
	{
		delay1= delay1 + val;
	}
	
	public static void delay2(double val2)
	{
		delay2 = delay2 + val2;
	}
	
	public static double getDelay1()
	{
		return delay1;
	}
	
	public static double getDelay2()
	{
		return delay2;
	}

}
