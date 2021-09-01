package com.elevator.design.ElevatorScheduler;

public class Request {
	private int source;
	private int destination = -1;
	private Button directionToGo;
	private boolean pickedUp = false;
	
	public enum Button{
		UP, DOWN
	}
	
	public Request() {
		super();
	}

	public Request(Request req) {
		super();
		this.source = new Integer(req.source);
		this.destination = new Integer(req.destination);
		if(req.directionToGo==Button.UP)
			this.directionToGo = Button.UP;
		else
			this.directionToGo = Button.DOWN;
		this.pickedUp = new Boolean(req.pickedUp);
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
