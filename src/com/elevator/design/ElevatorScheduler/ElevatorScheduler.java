package com.elevator.design.ElevatorScheduler;
import java.util.*;

import com.elevator.design.ElevatorScheduler.Request.Button;
import com.elevator.design.ElevatorSimulator.Elevator;
import com.elevator.design.ElevatorSimulator.ElevatorSimulator;
import static com.elevator.design.Configs.*;

public class ElevatorScheduler {
	
	public void requestlistener(ElevatorSimulator elevators) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			Request newRequest = new Request();
			
			System.out.println("Enter source floor and button - 1.UP  2.DOWN\n");
			newRequest.setSource(scanner.nextInt());
			if(scanner.nextInt()==1) 
				newRequest.setDirectionToGo(Button.UP);
			else
				newRequest.setDirectionToGo(Button.DOWN);
			
			String lift = selectLift(elevators, newRequest);
			System.out.println("Elevator "+lift+" is arriving");
		}
	}
	
	private String selectLift(ElevatorSimulator elevators, Request request) {
		List<Integer> timeTakenByLifts = new ArrayList<Integer>();
		for(Elevator elevator : elevators.getElevators()) {
			int time=0;
			switch(elevator.getDirection()) {
			case STEADY:
				time = Math.abs(elevator.getCurrentPosition()-request.getSource());
				break;
			case UP:
					time = this.findTimeFromRequestList(elevator, request);
				break;
			case DOWN:
					time = this.findTimeFromRequestList(elevator, request);
				break;
			}
			timeTakenByLifts.add(time);
		}
		
		//finding the lift that takes minimum time
		int minTime=Integer.MAX_VALUE;
		Elevator assignedElevator = elevators.getElevators().get(0);
		for(int i=0;i<timeTakenByLifts.size();i++) {
			if(timeTakenByLifts.get(i)<minTime) {
				minTime = timeTakenByLifts.get(i);
				assignedElevator = elevators.getElevators().get(i);
			}
			System.out.println("Time taken to call lift "+elevators.getElevators().get(i).getName()+" is "+timeTakenByLifts.get(i));
		}
		
		assignedElevator.addToRequestList(request);
		assignedElevator.setNewState();
		
		return assignedElevator.getName();
	}
	
	private int findTimeFromRequestList(Elevator originalElevator, Request request) {
		Elevator elevator = new Elevator(originalElevator);
		int time=0, i=0;
		List<Request> estimationRequestList = elevator.getRequestList();
		int positionTrack = new Integer(elevator.getCurrentPosition());
		int target = new Integer(request.getSource());
		int currentTarget = this.nextTarget(estimationRequestList,target);
		while(positionTrack!=target) {
			if(currentTarget==positionTrack) {
				currentTarget = this.nextTarget(estimationRequestList,target);
			}
			time++;
			if(currentTarget>positionTrack)
				positionTrack++;
			else if(currentTarget<positionTrack)
				positionTrack--;
			
			for(Request req : estimationRequestList) {
				if(req.getSource()==positionTrack && !req.isPickedUp()) 
					req.setPickedUp(true);
			}
			
			List<Integer> toDroplist = new ArrayList<Integer>();
			for(int k=0;k< estimationRequestList.size();k++) {
				Request req = estimationRequestList.get(k);
				if( req.isPickedUp() && req.getDestination()==elevator.getCurrentPosition())
					toDroplist.add(i);
			}
			for(int toDrop:toDroplist)
				estimationRequestList.remove(toDrop);

//			System.out.println("positionTrack -> "+positionTrack+" currentTarget -> "+currentTarget+" target->"+target);
		}
		
//		System.out.println("positionTrack -> "+positionTrack);
		return time + Math.abs(request.getSource()-positionTrack);
	}

	private int nextTarget(List<Request> estimationRequestList, int target) {
		if(estimationRequestList.size()==0)
			return target;
		
		if(estimationRequestList.get(0).isPickedUp()) {
			if(estimationRequestList.get(0).getDestination()==-1) {
				int nextTar;
				if(estimationRequestList.get(0).getDirectionToGo()==Button.UP)
					nextTar = Max_floor;
				else
					nextTar =  Min_floor;
				estimationRequestList.remove(0);
				return nextTar;
			}
			else {
				return estimationRequestList.get(0).getDestination();
			}
		}
		else {
			return estimationRequestList.get(0).getSource();
		}
	}
}
