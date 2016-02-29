package com.shanthini.visualization;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;


import com.sooki.components.MyNode;
import com.sooki.components.RoadSegment;
import com.sooki.components.Sensor;
import com.sooki.components.Vehicle;
import com.sooki.entity.RoadMap;
import com.sooki.helpers.RoughBase;
import com.sooki.helpers.RoughNodes;
import com.sooki.simulator.VehicleListHolder;

import edu.uci.ics.jung.graph.util.Pair;


public class VisualisationA extends JFrame
{
    private Drawing drawing = new Drawing(); 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	ArrayList<MyNode> listOfPlaces;
	
	public VisualisationA()
    {
        // Initialize img here.
    	setLayout(new BorderLayout());
    	setSize(900,900);
    	setTitle("Traffic Simulator");
    	add("Center",drawing);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
    	setVisible(true);
  
    }

    private class Drawing extends JPanel implements MouseListener {
    	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int numberOfNodes;
		private int gridWidth; 
		private int gridHeight;
		private int DrawingSpaceWidth;
		private int DrawingSpaceHeight;
		private int OffSetX = 40;
		private int OffSetY = 40;
		private ArrayList<RoughNodes> nodes;
		private int nodeWidth = 25;
		private int nodeLength = 25;
		private int sensorWidth = 20;
		private int sensorLength = 20;
		private ArrayList<Sensor> sensors;
		public Drawing() {
		
		DrawingSpaceWidth = 800 -OffSetX*2;
		DrawingSpaceHeight = 800 - OffSetY*2;
		RoughBase myTypes = RoadMap.getRoadMap().getRoughNodes();
		//total number of nodes from json file
		numberOfNodes = myTypes.nodes.size();
		nodes = myTypes.nodes;
		listOfPlaces = RoadMap.getRoadMap().getListOfLocalPlaces();
		sensors = RoadMap.getRoadMap().getListOfSensor();
		gridWidth = (int)((DrawingSpaceWidth)/(Math.sqrt(numberOfNodes)-1));
		gridHeight = (int)((DrawingSpaceHeight)/(Math.sqrt(numberOfNodes)-1));
		populateCoordinates(numberOfNodes);
		populateSesors();
		addMouseListener(this);
		ToolTipManager.sharedInstance().registerComponent(this);
		}
		
    	 protected void paintComponent(Graphics g) {
    		  super.paintComponent(g);       
    		 	drawBasicGrid(gridWidth,gridHeight,g);
    			drawNodes(numberOfNodes,g);
    			drawSensor(g);
    			CopyOnWriteArrayList<Vehicle> all_cars= VehicleListHolder.getVehicleListHolder().listOfVehicles;
    			drawCars(all_cars,g);
    			repaint();
    			
    		 // g.drawOval(10, 10, 10, 10);
    	 
    	 }
    	 
    		public  void drawBasicGrid(int gridWidth,int gridHeight,Graphics g)
    		{	
    			 
    			 int startX = 0,startY = 0;
    			 for (int i = 0; i < 4; i++) {
    				   startX = i * gridWidth + OffSetX;
    				  g.drawLine(startX, OffSetY, startX, DrawingSpaceHeight+OffSetY);
    				
    				  
    				}
    				for (int i = 0; i < 4; i++) {
    				   startY = i * gridHeight + OffSetY;
    				//   System.out.println("Hello whats happening");
    				   g.drawLine(OffSetX, startY, DrawingSpaceWidth+OffSetX, startY);
    				  
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
    			 	if( k!= (Math.sqrt(number) -1) )
    			 		System.out.println("Wait");
    			 	//System.out.println("coodrinate" + x + " " + y);
    			 	//System.out.println(nodes.get(calc).getNodex() + " " + nodes.get(calc).getNodey());
    			 }
    			 
    		 }

    	}
    	 
	 private void populateSesors() {
		 		for(Sensor sensor: sensors)
		 		{
		 			RoadSegment rs = sensor.getRs();
		 			Pair<MyNode> p = RoadMap.getRoadMap().getNodeAssociatedWithEdge(rs);
		 			MyNode first = p.getFirst();
		 			MyNode second = p.getSecond();
		 			if(first.getY() - second.getY() == 0)
		 			{
		 				if(first.getX() < second.getX())
		 				{
		 				sensor.setX( (first.getX() + gridWidth/2) - sensorWidth/2 );
		 				sensor.setY( (first.getY() - sensorLength));
		 				}
		 				else {
		 					sensor.setX( (first.getX() - gridWidth/2) - sensorWidth/2);
			 				sensor.setY( (first.getY() + sensorLength));
		 				}
		 	
		 			}
		 			
		 			else if(first.getX() - second.getX() == 0)
		 			{
		 				if(first.getY() < second.getY())
		 				{
		 				sensor.setX( first.getX() - sensorWidth  );
		 				sensor.setY( (first.getY() + gridHeight/2) - sensorLength );
		 				}
		 				else {
		 					sensor.setX( first.getX() + sensorWidth  );
			 				sensor.setY( (first.getY() - gridHeight/2) - sensorLength );
		 				}
		 	
		 			}
		 			
		 		}
    	}
    	 
    		public void drawNodes(int number,Graphics g)
    		{
    		//	System.out.println("number of nodes" + number);
    			for (int j=0; j <numberOfNodes; j++) 
    		    {
    		    	
    		    	int type = nodes.get(j).getType();
    		    	
    		    	int StartX = listOfPlaces.get(j).getX();
    		    	int StartY = listOfPlaces.get(j).getY();
    		    	 switch(type)
    		         {
    		            case 0 :
    		            	//regular nodes
    		            	
    		            	g.setColor(new Color(255, 223, 11));
    				    	g.fillRect(StartX-nodeWidth/2,StartY-nodeLength/2,nodeWidth,nodeLength);
    				     //yellow
    		               break;
    		            case 1 :
    		            	//traffic signal	
    		            	g.setColor(new Color(0,191,255));
    		            	g.fillRect(StartX-25/2,StartY-25/2,25,25);
    			    		//blue
    			               break;
    		            default:
    		            	System.out.println("Invalid node type");
    		         }
    		    		
    		    }
    		
    		}
    		
    		public void drawSensor(Graphics g)
    		{
    		//	System.out.println("number of nodes" + number);
    			for(Sensor sensor: sensors)
    			{
    				int StartX = sensor.getX();
    		    	int StartY = sensor.getY();
    		    	g.setColor(new Color(102, 102, 153));
    				g.fillOval(StartX-sensorWidth/2,StartY-sensorLength/2,nodeWidth,nodeLength);
    			}
  
    		
    		}
    		
    	     public String getToolTipText( MouseEvent evt ) {
    	    	
    	            Iterator iter = listOfPlaces.iterator();
    	            System.out.println("getToolTipCalled");
    	            while ( iter.hasNext() ) {
    	                MyNode c = (MyNode)iter.next();
    	                Rectangle bounds = new Rectangle( c.getX() - nodeWidth/2,
     	                        c.getY()-nodeLength/2, nodeWidth, nodeLength);
    	                if ( bounds.contains( evt.getPoint() ) ) {
    	                    return c.getName();
    	                }
    	            }
    	            return (String)null;
    	    }
    	         

    		private void drawCars(CopyOnWriteArrayList<Vehicle> all_cars, Graphics g) {
    			
    		
    			for(Vehicle v : all_cars )
    			{
    				  
    				  g.setColor(new Color(153, 0, 76));
    				//  System.out.println("executing draw cars");
    				 //   rect(v.getCurrent_x(), v.getCurrent_y(), 20, 10);
    				  //  ellipse(v.getCurrent_x()+5, v.getCurrent_y() +10, 5, 5);
    				  //  ellipse(v.getCurrent_x()+10, v.getCurrent_y()+10, 5, 5);
    				  g.fillOval((int)v.getCurrent_x(),(int) v.getCurrent_y(), 10, 10);
    				 
    				  g.drawString(String.valueOf(v.getId()), (int)v.getCurrent_x()+10, (int)v.getCurrent_y());
    				
    				
    			}	
    			
    		}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse was clicked");
				 Iterator iter = sensors.iterator();
 	            System.out.println("getToolTipCalled");
 	            while ( iter.hasNext() ) {
 	                Sensor c = (Sensor)iter.next();
 	                Rectangle bounds = new Rectangle( c.getX() - nodeWidth/2,
 	                        c.getY()-nodeLength/2, nodeWidth, nodeLength);
 	                if ( bounds.contains( e.getPoint() ) ) {
 	                    System.out.println(c.getName());
 	                    new Stats(c);
 	                 
 	                }
 	            }
 	        
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
    }
    
    private class Stats extends JFrame implements MouseListener {
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String frameName;
    	
    	public  Stats(Sensor s) {
    			frameName = s.getName();
		    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		    	StatsDrawing contentPane = new StatsDrawing(s);
            	getContentPane().add(contentPane);
            	
            	setSize(300, 300);
            	setTitle(frameName);
            	setVisible(true);
		}
    	
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    
    }
    
    private class StatsDrawing extends JPanel implements MouseListener {

    
    	Sensor s;
    	public StatsDrawing(Sensor s){
    	 this.s = s;
     }
    	
   	 protected void paintComponent(Graphics g) {
   		  super.paintComponent(g);       
   		 
   		  g.drawString("CurrentLoad: " + s.getRs().getCurrentLoad(), 20, 20);
   		  g.drawString ("Maximum Capacity: " + s.getRs().getCapacity(), 20,40);
   		  g.drawString ("Maximum Velocity: " + s.getRs().getVelocity(), 20, 60);
   		  repaint();
   	 
   	 }
    	
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
   
    }

}