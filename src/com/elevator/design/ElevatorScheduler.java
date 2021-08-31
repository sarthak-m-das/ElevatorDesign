package com.elevator.design;
import java.util.*;

import com.elevator.design.Elevators.Elevator;
import com.elevator.design.Elevators.Elevator.Direction;


public class ElevatorScheduler {
	
	public void requestlistener(Elevators elevators) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			Request newRequest = new Request();
			
			System.out.println("Enter source floor: ");
			newRequest.setSource(scanner.nextInt());
			
			String lift = selectLift(elevators, newRequest);
			
			System.out.println("Elevator "+lift+" is arriving");
		}
	}
	
	private String selectLift(Elevators elevators, Request request) {
		List<Integer> timeTakenByLifts = new ArrayList<Integer>();
		for(Elevator elevator : elevators.getElevators()) {
			int time=0;
			switch(elevator.getDirection()) {
			case STEADY:
				time = Math.abs(elevator.getCurrentPosition()-request.getSource());
				break;
			case UP:
				if(request.getSource() >= elevator.getCurrentPosition())
					time = Math.abs(elevator.getCurrentPosition()-request.getSource());
				else 
					time = -1; //-1 means indefinite
				break;
			case DOWN:
				if(request.getSource() <= elevator.getCurrentPosition())
					time = Math.abs(elevator.getCurrentPosition()-request.getSource());
				else 
					time = -1; //-1 means indefinite
				break;
			}
			timeTakenByLifts.add(time);
		}
		
		//finding the lift that takes minimum time
		int minTime=Integer.MAX_VALUE;
		Elevator assignedElevator = elevators.getElevators().get(0);
		for(int i=1;i<timeTakenByLifts.size();i++) {
			if(timeTakenByLifts.get(i)<minTime && timeTakenByLifts.get(i)!=-1) {
				minTime = timeTakenByLifts.get(i);
				assignedElevator = elevators.getElevators().get(i);
			}
		}
		
		assignedElevator.addToPickUp(request);
		assignedElevator.setNewState();
		
		return assignedElevator.getName();
	}
	
	public class Request {
		int source;
		int destination;
		
		public int getSource() {
			return source;
		}
		public void setSource(int source) {
			this.source = source;
		}
		public int getDestination() {
			return destination;
		}
		public void setDestination(int destination) {
			this.destination = destination;
		}
	}
}
