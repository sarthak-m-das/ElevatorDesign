package com.elevator.design.ElevatorSchedulor;

public class Request {
	private int source;
	private int destination;
	private Button directionToGo;
	private boolean pickedUp = false;
	
	public enum Button{
		UP, DOWN
	}
	
	@Override
	public String toString() {
		return "Request [source=" + source + ", destination=" + destination + ", directionToGo=" + directionToGo
				+ ", pickedUp=" + pickedUp + "]";
	}
	
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
	public Button getDirectionToGo() {
		return directionToGo;
	}
	public void setDirectionToGo(Button directionToGo) {
		this.directionToGo = directionToGo;
	}
	public boolean isPickedUp() {
		return pickedUp;
	}
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
}
