package com.sooki.entity;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import com.google.gson.Gson;
import com.sooki.components.MyNode;
import com.sooki.components.MyPlaces;
import com.sooki.components.RoadSegment;
import com.sooki.components.Sensor;
import com.sooki.components.TrafficLight;
import com.sooki.helpers.MyColor;
import com.sooki.helpers.RoughBase;
import com.sooki.helpers.RoughEdge;
import com.sooki.helpers.RoughNodes;
import com.sooki.main.Main;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;



public class RoadMap {
	int edgeCount = 0; 
	private static RoadMap r;
	DirectedGraph<MyNode, RoadSegment> network;
	DijkstraShortestPath<MyNode,RoadSegment> alg;
	private ArrayList<MyNode> listOfPlacesInMap;
	private ArrayList<MyNode> listOfDestination;
	private ArrayList<TrafficLight> listOfTrafficLight;
	private ArrayList<MyNode> listOfLocalPlaces;
	private ArrayList<Sensor> listOfSensor;
	private RoughBase myTypes;
	
	
	
	private RoadMap()
	{
		network = getRoadMapNetwork();
	}
	
	
	
	
	public static RoadMap getRoadMap()
	{
		if( r == null)
		{
			r = new RoadMap();
		}
		return r;
	}
	
	
	
	private DirectedGraph<MyNode, RoadSegment> getRoadMapNetwork()
	{	
		if(network == null)
		{
			network =  createSimpleGraph();
		}
		  
		return network;
	}
	
	public List<RoadSegment>  findShortestPath(MyNode source, MyNode destination)
	{
        List<RoadSegment> l = alg.getPath(source, destination);
       
        return l;      
	}
	
	public List<MyNode>  findShortestPathNode(MyNode source, MyNode destination)
	{
		if(source == null || destination ==null )
			System.out.println("Some went wrong");
		List<RoadSegment> roadsegments  = alg.getPath(source, destination);
		ArrayList<MyNode> listOfNodeInShortestPath = new ArrayList<MyNode>();
		listOfNodeInShortestPath.add(source);
		for(int i=0; i < roadsegments.size(); i++)
		{
			
			Pair<MyNode> p = getRoadMap().getNodeAssociatedWithEdge(roadsegments.get(i)); 
			listOfNodeInShortestPath.add(p.getSecond());
			
		}
		return listOfNodeInShortestPath;
       
            
	}
	
	   public  void calcWeightedShortestPath() {
	        Transformer<RoadSegment, Double> wtTransformer = new Transformer<RoadSegment,Double>() {
	            public Double transform(RoadSegment link) {
	                return link.getLength();
	            }
	        };
	        alg = new DijkstraShortestPath<MyNode, RoadSegment>(network, wtTransformer);

	    }
	
    private  DirectedGraph<MyNode, RoadSegment> createSimpleGraph()
    {
    	MyNode n1;
    	TrafficLight SIGNAL;
    	RoadSegment r1;
    	network =
                new DirectedSparseMultigraph<MyNode,RoadSegment >();
        	listOfPlacesInMap = new ArrayList<MyNode>();
        	listOfDestination = new ArrayList<MyNode>();
        	listOfTrafficLight = new ArrayList<TrafficLight>();
        	listOfLocalPlaces = new ArrayList<MyNode>();
        	listOfSensor = new ArrayList<Sensor>();
    	Gson gson = new Gson();
    	try {
		myTypes = gson.fromJson(new FileReader(Main.FileName), RoughBase.class);
		
		for(RoughNodes rf : myTypes.nodes){
			if(rf.getType() == 0)
			{
				n1 = new MyPlaces(rf.getName());
				listOfPlacesInMap.add(n1);
				listOfDestination.add(n1);
				 String machineName = "machine" + rf.getMachine();
				 rf.setMachineName(machineName);
				// if it is a local place
				if(rf.getMachineName().equals(Main.machine))
				{
					  listOfLocalPlaces.add(n1);
				//	  System.out.println("node " + n1);
				}
				
			//	 NodeAssociationMap.addEntry(n1, rf.getMachineName());
				 network.addVertex(n1);
			   
				
			}
			else if(rf.getType() == 1)
			{
				
				 SIGNAL = new TrafficLight(MyColor.GREEN);
				 listOfTrafficLight.add(SIGNAL);
				// if it is a local place
				 String machineName = "machine" + rf.getMachine();
				 rf.setMachineName(machineName);
				 listOfPlacesInMap.add(SIGNAL);
				 
				 if(rf.getMachineName().equals(Main.machine))
				  {
						  listOfLocalPlaces.add(SIGNAL);
				  }
					
				//	NodeAssociationMap.addEntry(SIGNAL, rf.getMachineName());
					network.addVertex(SIGNAL);
					   
			}
			
			
			
		}
	
		for(RoughEdge re : myTypes.edges){
			
			  r1 = new  RoadSegment(re.getLength(),re.getCapacity());
		//	  System.out.println("source " + (re.getSource() - 1));
		//	  System.out.println("desintation " + (re.getTarget() - 1));
		//	  System.out.println();
		//	  System.out.println(listOfPlacesInMap.size());
			  MyNode source = listOfPlacesInMap.get( re.getSource() - 1);
			  MyNode destination = listOfPlacesInMap.get(re.getTarget() - 1);
			  network.addEdge(r1,source,destination,EdgeType.DIRECTED );
			  listOfSensor.add(new Sensor(r1));
		}
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        calcWeightedShortestPath();	
        return network;
    }
    
    public ArrayList<MyNode> getListOfPlacesInMap(){
		return listOfPlacesInMap;
    	
    }
    
    public ArrayList<MyNode> getListOfLocalPlaces(){
  		return listOfLocalPlaces;
      	
      }
    
    public ArrayList<TrafficLight> getlistOfTrafficLight(){
		return listOfTrafficLight;
    }
    
    public Pair<MyNode> getNodeAssociatedWithEdge(RoadSegment rs){
		Pair<MyNode> p = network.getEndpoints(rs);
		return p;
    	
    }
    
    public ArrayList<MyNode> getListOfDestination(){
  		return listOfDestination;
      	
      }
    
    public ArrayList<Sensor> getListOfSensor(){
  		return listOfSensor;
      	
      }
    
    public ArrayList<MyNode> getListOfAssociatedNodes(MyNode node)
    {
    	ArrayList<MyNode> myNodeList = new ArrayList<MyNode>(network.getNeighbors(node));
    	
    	return myNodeList;
    }
    
    
    
    public RoadSegment getAssociatedRoadSegement(MyNode source,MyNode destination){
 		RoadSegment rs = network.findEdge(source, destination);
 		return rs;
     	
     }
    
    public RoughBase getRoughNodes() {
    	return myTypes;
    }

    
    

}
