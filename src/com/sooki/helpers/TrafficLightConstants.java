package com.sooki.helpers;

public class TrafficLightConstants {
	static int DURATION_RED = 4, DURATION_YELLOW = 2, DURATION_GREEN = 10;
	
	
	public static int getDurationForSignal(MyColor my) 
	{
		
		if(MyColor.RED == my)
		{
			return DURATION_RED;
		}
		else if(MyColor.YELLOW == my)
		{
			return DURATION_YELLOW;
		}
		else
		{
			return DURATION_GREEN;
		}
	}

}
