package com.elevator.design.ElevatorSchedulor;
import java.util.*;

import com.elevator.design.ElevatorSchedulor.Request.Button;
import com.elevator.design.ElevatorSimulator.Elevator;
import com.elevator.design.ElevatorSimulator.Elevators;
import static com.elevator.design.Configs.*;

public class ElevatorScheduler {
	
	public void requestlistener(Elevators elevators) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			Request newRequest = new Request();
			
			System.out.println("Enter source floor: ");
			newRequest.setSource(scanner.nextInt());
			System.out.println("Press the button - 1.UP  2.DOWN\n");
			if(scanner.nextInt()==1) {
				newRequest.setDirectionToGo(Button.UP);
			} {
				newRequest.setDirectionToGo(Button.DOWN);
			}
			
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

	private int findTimeFromRequestList(Elevator elevator, Request request) {
		int time=0;
		int positionTrack = elevator.getCurrentPosition();
		for(Request previousReq : elevator.getRequestList()) {
			
			if(!previousReq.isPickedUp()) {
				if(previousReq.getSource()>=positionTrack) {
					if(request.getSource()>=positionTrack && request.getSource()<=previousReq.getSource()) {
						time+=Math.abs(request.getSource()-positionTrack);
						positionTrack=request.getSource();
						break;
					}
					else if(request.getSource()>=previousReq.getSource() && previousReq.getDirectionToGo()==Button.UP) {
						time+=Math.abs(request.getSource()-positionTrack);
						positionTrack=request.getSource();
						break;
					}
					
					if(previousReq.getDirectionToGo()==Button.UP) {
						time+=Math.abs(positionTrack-Max_floor);
						positionTrack=Max_floor;
					}
					else {
						time+=Math.abs(positionTrack-previousReq.getSource());
						positionTrack=previousReq.getSource();
					}
				}
				else {
					if(request.getSource()<=positionTrack && request.getSource()>=previousReq.getSource()) {
						time+=Math.abs(request.getSource()-positionTrack);
						positionTrack=request.getSource();
						break;
					}
					else if(request.getSource()<=previousReq.getSource() && previousReq.getDirectionToGo()==Button.DOWN) {
						time+=Math.abs(request.getSource()-positionTrack);
						positionTrack=request.getSource();
						break;
					}
					
					if(previousReq.getDirectionToGo()==Button.DOWN) {
						time+=Math.abs(positionTrack-Min_floor);
						positionTrack=Min_floor;
					}
					else {
						time+=Math.abs(positionTrack-previousReq.getSource());
						positionTrack=previousReq.getSource();
					}
				}
			}
			else {
				if(request.getSource()>=positionTrack && request.getSource()<=previousReq.getDestination()) {
					time+=Math.abs(request.getSource()-positionTrack);
					positionTrack=request.getSource();
					break;
				}
				else if(request.getSource()<=positionTrack && request.getSource()>=previousReq.getDestination()) {
					time+=Math.abs(request.getSource()-positionTrack);
					positionTrack=request.getSource();
					break;
				}
				positionTrack=request.getDestination();
			}
		}
		return time + Math.abs(request.getSource()-positionTrack);
	}
}
