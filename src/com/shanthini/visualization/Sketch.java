 package com.shanthini.visualization;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;
import com.sooki.components.MyNode;
import com.sooki.components.Vehicle;
import com.sooki.entity.RoadMap;
import com.sooki.helpers.RoughBase;
import com.sooki.helpers.RoughNodes;
import com.sooki.simulator.VehicleListHolder;

import processing.core.PApplet;
import processing.core.PVector;

//basic sketch drawing a grid. Right now grid is not based on the json file
public class Sketch extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numberOfNodes;
	private int gridWidth; 
	private int gridHeight;
	private int DrawingSpaceWidth;
	private int DrawingSpaceHeight;
	private int OffSetX = 20;
	private int OffSetY = 20;
	
	private ArrayList<RoughNodes> nodes = new ArrayList<>();

	ArrayList<MyNode> listOfPlaces;
	
	public void setup() {
		
		settings();
		background(152);
		DrawingSpaceWidth = 800 -OffSetX*2;
		DrawingSpaceHeight = 800 - OffSetY*2;
		
		 
		try {
						
			RoughBase myTypes = RoadMap.getRoadMap().getRoughNodes();
			//total number of nodes from json file
			numberOfNodes = myTypes.nodes.size();
			nodes = myTypes.nodes;
			listOfPlaces = RoadMap.getRoadMap().getListOfLocalPlaces();
			gridWidth = (int)((DrawingSpaceWidth)/(Math.sqrt(numberOfNodes)-1));
			gridHeight = (int)((DrawingSpaceHeight)/(Math.sqrt(numberOfNodes)-1));
			
			System.out.println(myTypes.nodes.get(0).toString());
			System.out.println(myTypes.edges.get(1).toString());
			
			populateCoordinates(numberOfNodes);
			
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	



	private void populateCoordinates(int number) {
		 
		 for(int j=0; j<Math.sqrt(number);j++)
		 {
			 int y = gridWidth*j + OffSetY;
			 
			 for (int k=0; k<Math.sqrt(number); k++) 
			 {
			 	int x = gridHeight*k + OffSetX;
			 	int calc =  (int) (Math.sqrt(number)*j + k);
			 	System.out.println("X= " + x + "Y= " +y);
			 	listOfPlaces.get(calc).setX(x);
			 	listOfPlaces.get(calc).setY(y);
			 	//System.out.println("coodrinate" + x + " " + y);
			 	//System.out.println(nodes.get(calc).getNodex() + " " + nodes.get(calc).getNodey());
			 }
			 
		 }

	}
	
	public void draw() {
		
		background(255,255,255);
		 //draws the basic grid 
		drawBasicGrid(gridWidth,gridHeight);
		drawNodes(numberOfNodes);
		CopyOnWriteArrayList<Vehicle> all_cars= VehicleListHolder.getVehicleListHolder().listOfVehicles;
		drawCars(all_cars);
		try{
		Thread.sleep(500);
		}
		catch(Exception e)
		{
			
		}
	}

	public void settings() 
	{
		  size(800, 800);
	}
	
	public  void drawBasicGrid(int gridWidth,int gridHeight)
	{	
		 stroke(0,0,0); 
		 int startX = 0,startY = 0;
		 for (int i = 0; i < 4; i++) {
			   startX = i * gridWidth + OffSetX;
			  line (startX, OffSetY, startX, DrawingSpaceHeight+OffSetY);
			
			  
			}
			for (int i = 0; i < 4; i++) {
			   startY = i * gridHeight + OffSetY;
			//   System.out.println("Hello whats happening");
			  line (OffSetX, startY, DrawingSpaceWidth+OffSetX, startY);
			  
			}
			
			
	}

	public void drawNodes(int number)
	{
		stroke(0);
		
	  
	    for (int j=0; j <numberOfNodes; j++) 
	    {
	    	
	    	
	    	int type = nodes.get(j).getType();
	    	
	    	int StartX = listOfPlaces.get(j).getX();
	    	int StartY = listOfPlaces.get(j).getY();
	    	 switch(type)
	         {
	            case 0 :
	            	//regular nodes
	            	
	            	fill(255,223,11);
			    	rect(StartX-25/2,StartY-25/2,25,25,7);
			     //yellow
	               break;
	            case 1 :
	            	//traffic signal	
	            	fill(0,191,255);
		    		rect(StartX-25/2,StartY-25/2,25,25,7);
		    		//blue
		               break;
	            default:
	            	System.out.println("Invalid node type");
	         }
	       
	    		
	 //  	System.out.println("Node coordinates for node" + " " + nodes.get(j).name+" "+ "x:"+ nodes.get(j).Nodex+" "+ "y:"+ nodes.get(j).Nodey);
	    		
	    }
	
	} 

	private void drawCars(CopyOnWriteArrayList<Vehicle> all_cars) {
		
	
		for(Vehicle v : all_cars )
		{
			  noStroke();
			  fill(153,0,76);
			 //   rect(v.getCurrent_x(), v.getCurrent_y(), 20, 10);
			  //  ellipse(v.getCurrent_x()+5, v.getCurrent_y() +10, 5, 5);
			  //  ellipse(v.getCurrent_x()+10, v.getCurrent_y()+10, 5, 5);
			  ellipse(v.getCurrent_x(), v.getCurrent_y(), 10, 10);
			  text(v.getId(), v.getCurrent_x()+10, v.getCurrent_y());
			
			
		}	
		
	}
	

	
	
	
}
	

	
	
	
	
	
	
	
	








