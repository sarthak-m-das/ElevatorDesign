package com.elevator.design.ElevatorSimulator;

import java.util.LinkedList;
import java.util.List;

import com.elevator.design.ElevatorSchedulor.Request;

public class Elevator{
	private String name;
	private int nextDestination=0;
	private int currentPosition=0;
	private Direction direction=Direction.STEADY;
	private List<Request> requestList=new LinkedList<Request>();
	
	public enum Direction{
		STEADY, UP, DOWN;
	}
	
	@Override
	public String toString() {
		return "Elevator [name=" + name + ", nextDestination=" + nextDestination + ", currentPosition="
				+ currentPosition + ", direction=" + direction + ", requestList=" + requestList + "]";
	}
	
	public int getNextDestination() {
		return nextDestination;
	}
	
	public void setNewState() {
		if(this.requestList.isEmpty()) {
			this.setDirection(Direction.STEADY);
			return;
		}
		
		Request req = this.requestList.get(0);
		if(req.isPickedUp())
			this.setNextDestination(req.getDestination());
		else
			this.setNextDestination(req.getSource());
		
		if(this.nextDestination < this.getCurrentPosition())
			this.setDirection(Direction.DOWN);
		else if(this.nextDestination > this.getCurrentPosition())
			this.setDirection(Direction.UP);
		else
			this.setDirection(Direction.STEADY);
		
		System.out.println("New State of Elevator "+this.name+ " : "+this.toString());
	}

	public int findNextDestination() {
		return 0;
	}

	public void changeCurrentPosition() {
		if(this.getDirection()==Direction.UP)
			this.currentPosition = this.currentPosition +1 ;
		else if(this.getDirection()==Direction.DOWN)
			this.currentPosition = this.currentPosition -1 ;
		
	}

	public void setNextDestination(int nextDestination) {
		this.nextDestination = nextDestination;
	}


	public int getCurrentPosition() {
		return currentPosition;
	}


	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}


	public Direction getDirection() {
		return direction;
	}


	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Request> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<Request> requestList) {
		this.requestList = requestList;
	}

	public void addToRequestList(Request request) {
		this.requestList.add(request);
	}
}