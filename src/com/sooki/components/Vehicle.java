package com.sooki.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sooki.entity.RoadMap;
import com.sooki.events.VehicleBeginEvent;
import com.sooki.events.VehicleEndEvent;
import com.sooki.main.Main;
import com.sooki.simulator.EventListHolder;
import com.sooki.simulator.VehicleListHolder;
import com.sooki.stats.StatsHolder;

import edu.uci.ics.jung.graph.util.Pair;


public class Vehicle  {
	static int currentId =0;
	private int id;
	private float velocity;
	private MyNode source;
	private MyNode previous;
	private MyNode destination;
	private MyNode current;
	private MyNode next;
	private int startId;
	private int stopdId;
	private int startTime;
	private int timeToNextStop;
	private int vehicleClock;
	volatile float current_x;
	volatile float current_y;
	//public RoadMap rm;
	private List<MyNode>  shortestPathNode;
	public Vehicle()
	{
		
	}
	public Vehicle(int velocity,MyNode source,MyNode destination,int vehicleClock)
	{
		this.startTime = vehicleClock;
		this.id = currentId++;
		this.velocity = velocity;
		this.source = source;
		this.destination = destination;
		this.current = source;
		this.current_x = source.getX();
		this.current_y = source.getY();
		this.timeToNextStop = 1000;
		previous = null;
		this.vehicleClock = vehicleClock;
	//	this.rm  = RoadMap.getRoadMap();
		this.shortestPathNode = this.calculateShortestPathNode(source,destination);
		
		this.next = getNextPostion();
	}
	
	public int getStartId() {
		return startId;
	}
	public void setStartId(int startId) {
		this.startId = startId;
	}
	public int getStopdId() {
		return stopdId;
	}
	public void setStopdId(int stopdId) {
		this.stopdId = stopdId;
	}
	public int getId() {
		return id;
		
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getVelocity() {
		return velocity;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	public MyNode getStart() {
		return source;
	}
	public void setStart(MyNode source) {
		this.source = source;
	}
	public MyNode getDestination() {
		return destination;
	}
	public void setDestination(MyNode destination) {
		this.destination = destination;
	}
					
	public float getCurrent_x() {
		return current_x;
	}
	public void setCurrent_x(float current_x) {
		this.current_x = current_x;
	}
	public float getCurrent_y() {
		return current_y;
	}
	public void setCurrent_y(float current_y) {
		this.current_y = current_y;
	}
	public List<MyNode> getShortestPathNode() {
		return shortestPathNode;
	}
	
	public ArrayList<MyNode> calculateShortestPathNode(MyNode source,MyNode destination) {
		// if current your destination, return 1;
		shortestPathNode =  RoadMap.getRoadMap().findShortestPathNode(source, destination);
		ArrayList<MyNode> arraylist = new ArrayList<MyNode>(shortestPathNode);
		return arraylist;
		
	}
	
	public void updateCarPosition()
	{
		//check which direction of car
		// if sx coordinate are zero, the car moves in y
	
		if(current.getX() - next.getX() == 0)
		{
			this.current_y = this.current_y + (next.getY() - current.getY())/this.timeToNextStop;
			this.current_x = current.getX();
	//		System.out.println("y" + this.current_y);
		}
		else if(current.getY() - next.getY() == 0)
		{
			//System.out.println("The starting position was" + this.current_x);
	//		System.out.println("difference" +  this.id + " " + (next.getX() - current.getX()));
			this.current_x = this.current_x +  (next.getX() - current.getX())/timeToNextStop;
			this.current_y = current.getY();
	//		System.out.println(this.id +" x " + this.current_x);
		}
	
		
	}
	
	
	public void drawCar() {
	//	System.out.println("Drawing car at these location");
	}

	private int futureEvent() {
	
		// if current your destination, return 1;
		if (current.equals(destination) )
		{
			if(Main.type == 1)
			{
			StatsHolder.addToHashMap(this.vehicleClock);
			StatsHolder.delay1(this.vehicleClock - this.startTime);
			EventListHolder.getEventList().getAvgCalculator().average(this.vehicleClock - this.startTime);
			
			}
			else {
				StatsHolder.addToHashMap2(this.vehicleClock);
				StatsHolder.delay2(this.vehicleClock - this.startTime);
				EventListHolder.getEventList().getAvgCalculator().average(this.vehicleClock - this.startTime);
			}
			VehicleListHolder.getVehicleListHolder().listOfVehicles.remove(this);
		//	System.out.println("Vehicle " + this.id + "reached destination");
			return 1;
		}
		else {
				
			// the actual path of node
			this.next = getNextPostion();
			RoadSegment rs = RoadMap.getRoadMap().getAssociatedRoadSegement(current, this.next);
			
			int currentLoad = rs.getCurrentLoad(); 
		//	System.out.println("The current load is" + currentLoad);
			// since the load capactiy has reached more than of the capacity, change
			ArrayList<RoadSegment> listOfAvailableRoad = new ArrayList<RoadSegment>();
			if(Main.type == 1)
			{
			if( currentLoad > rs.getCapacity())
			{ // the below logic is for re routing
				ArrayList<MyNode> neighbhours =  RoadMap.getRoadMap().getListOfAssociatedNodes(current);
				for(MyNode m : neighbhours )
				{
					if((previous !=null && !m.equals(previous)) || previous == null) 
					{
						RoadSegment roadsegement = RoadMap.getRoadMap().getAssociatedRoadSegement(current, m);
						listOfAvailableRoad.add(roadsegement);
					}
				}
				
				Collections.sort(listOfAvailableRoad, (RoadSegment rs1,RoadSegment rs2) -> {return rs1.getCurrentLoad() - rs2.getCurrentLoad(); }); 
				RoadSegment least_used = listOfAvailableRoad.get(0);
				if(least_used.getCurrentLoad() > rs.getCurrentLoad())
				{
					least_used = rs;
				}
				else {
					rs = least_used;
				}
				
				
				Pair<MyNode> pp = RoadMap.getRoadMap().getNodeAssociatedWithEdge(rs);
				if (pp.getFirst() == current )
					this.next = pp.getSecond();
				else {
					this.next = pp.getFirst();
				}
		
			}
			}
			rs.incrementCurrentLoad();
			this.velocity = rs.getVelocity();
			if(this.velocity < 0)
				this.velocity = 10;
			rs.decrementVelocity();
			
			// check all other roads for traffic
			
		//	System.out.println(rs.ge.tLength());
		//	System.out.println(this.velocity);
			timeToNextStop =  (int) (Math.ceil(rs.getLength() / this.velocity));
		//	System.out.println(timeToNextStop);
			int timeToSchedule = timeToNextStop + this.vehicleClock;
		
			this.vehicleClock =  timeToSchedule;
			
		//	EventListHolder elh = ProcessRoutingTable.getEntryEventListHolder(next); 
			EventListHolder elh = EventListHolder.getEventList();
			//previous = current;
			//current = this.next;
			
				// Add to event queue new event
			VehicleEndEvent v1 = new VehicleEndEvent(timeToSchedule, this);
			this.shortestPathNode = this.calculateShortestPathNode(next,destination);
			
			if(elh != null)
			{
				if(current.equals(destination) )
				{
		//		v1.setMessage("destination reached");
				}
				elh.addEvent(v1);
			}
		//	else {
			//	String machineToForward = NodeAssociationMap.getEntryEventListHolder(current);
				
			//	EventListHolder.getOutGoingEventQueue().add(v1);
			//	PushlishToExchange.sendVehicleEventToExchange(v1,machineToForward);
				
			//}
			return 0;
		
		}
	}
	
	public void nodeReached() {
		RoadSegment roadsegement = RoadMap.getRoadMap().getAssociatedRoadSegement(current, next);
		roadsegement.decrementCurrentLoad();
		roadsegement.incrementtVelocity();
		previous = current;
		current = next;
	//	System.out.println(this.id + "Reached" + next);
		EventListHolder elh = EventListHolder.getEventList();
		VehicleBeginEvent v1 = new VehicleBeginEvent(this.vehicleClock, this);
		elh.addEvent(v1);
		
	}
	
	public void endEvent() {
		this.nodeReached();
	}
	
	public MyNode getCurrentPositon() {
		return current;
		
	}
	
	private MyNode getNextPostion()
	{
		int current_start = shortestPathNode.indexOf(this.current);
	//	for(MyNode n : shortestPathNode)
		//	System.out.print(n.toString() + " ");
	//	System.out.println();
	//	System.out.println(this.current.toString());
		if(shortestPathNode.size() > 1)
		{
		 next = (MyNode) shortestPathNode.get(current_start+1);
		}
		else {
			next = current;
		}
		
		return next;
	}

	public void beginEvent() {
		// TODO Auto-generated method stub
		this.futureEvent();
	}
	
	
	

}
