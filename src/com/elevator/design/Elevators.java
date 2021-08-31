package com.elevator.design;
import java.util.*;
import java.util.logging.Logger;

import com.elevator.design.ElevatorScheduler.Request;
import com.elevator.design.Elevators.Elevator.Direction;

public class Elevators implements Runnable {
	
	private List<Elevator> elevators=new ArrayList<Elevator>();
	
	private static final Logger Log = Logger.getLogger("Elevator Simulation");
	
	//Running the Elevators
	public void run() {
		while(true) {
			System.out.println("\n*******************");
			this.runElevators();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void runElevators() {
		Scanner scanner = new Scanner(System.in);
		
		for(Elevator elevator: elevators) {
			elevator.changeCurrentPosition();
			System.out.println("The Elevator "+elevator.getName()+" has reached floor "+elevator.getCurrentPosition());
			
			for(Request req : elevator.getToPickUp()) {
				//Picking Up the people at this floor
				if(req.getSource()==elevator.getCurrentPosition()) {
					System.out.println("Enter your Destination : ");
					req.setDestination(scanner.nextInt());
					
					elevator.getToPickUp().remove(req);
					elevator.addToDeliever(req);
					elevator.setNewState();
				}
			}
			
			for(Request req : elevator.getToDeliever()) {
				//Dropping off the people at this floor
				if(req.getSource()==elevator.getCurrentPosition()) {
					elevator.getToDeliever().remove(req);
				}
			}
			
			//Selecting next Destination and Direction
			if(elevator.getCurrentPosition() == elevator.getNextDestination()) 
				elevator.setNewState();
		}
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}
	
	public void addElevator(Elevator elevator) {
		this.elevators.add(elevator);
	}
	
	public static class Elevator{
		String name;
		int nextDestination=0;
		int currentPosition=0;
		Direction direction=Direction.STEADY;
		List<Request> toDeliever=new LinkedList<Request>();
		List<Request> toPickUp=new LinkedList<Request>();
		
		
		public enum Direction{
			STEADY, UP, DOWN;
		}
		
		@Override
		public String toString() {
			return "Elevator [name=" + name + ", nextDestination=" + nextDestination + ", currentPosition="
					+ currentPosition + ", direction=" + direction + ", toDeliever=" + toDeliever + ", toPickUp="
					+ toPickUp + "]";
		}
		
		
		public int getNextDestination() {
			return nextDestination;
		}
		
		public void setNewState() {
			int newNextDestination=this.findNextDestination();
			this.setNextDestination(newNextDestination);
			
			if(newNextDestination<this.getCurrentPosition())
				this.setDirection(Direction.DOWN);
			else if(newNextDestination>this.getCurrentPosition())
				this.setDirection(Direction.UP);
			else
				this.setDirection(Direction.STEADY);
			
			System.out.println("New State of Elevator "+this.name);
			System.out.println(this.toString());
		}


		public int findNextDestination() {
			if(this.toDeliever.isEmpty() && this.toPickUp.isEmpty())
				return this.currentPosition;
			
			int justUp = Integer.MAX_VALUE;
			for(Request req : this.getToDeliever()) {
				if(req.getDestination()>this.currentPosition && req.getDestination()<justUp)
					justUp=req.getDestination();
			}
			for(Request req : this.getToPickUp()) {
				if(req.getSource()>this.currentPosition && req.getSource()<justUp)
					justUp=req.getSource();
			}
			
			int justDown = Integer.MIN_VALUE;
			for(Request req : this.getToDeliever()) {
				if(req.getDestination()<this.currentPosition && req.getDestination()>justDown)
					justDown=req.getDestination();
			}
			for(Request req : this.getToPickUp()) {
				if(req.getSource()<this.currentPosition && req.getSource()>justDown)
					justDown=req.getSource();
			}
			
			if(this.direction == Direction.UP) {
				if(justUp!=Integer.MAX_VALUE)
					return justUp;
				else return justDown;
			}
			else if(this.direction == Direction.DOWN) {
				if(justDown!=Integer.MIN_VALUE)
					return justDown;
				else return justUp;
			}
			else {
				if(justUp==Integer.MAX_VALUE && justDown!=Integer.MIN_VALUE)
					return this.currentPosition;
				else if(justUp==Integer.MAX_VALUE)
					return justDown;
				else if(justDown==Integer.MIN_VALUE)
					return justUp;
				else {
					if(Math.abs(justDown-this.currentPosition)>Math.abs(justUp-this.currentPosition))
						return justUp;
					else return justDown;
				}
			}
		}


		public void addToDeliever(Request req) {
			this.toDeliever.add(req);
			
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


		public List<Request> getToDeliever() {
			return toDeliever;
		}


		public void setToDeliever(List<Request> toDeliever) {
			this.toDeliever = toDeliever;
		}


		public List<Request> getToPickUp() {
			return toPickUp;
		}


		public void setToPickUp(List<Request> toPickUp) {
			this.toPickUp = toPickUp;
		}
		
		public void addToPickUp(Request request) {
			this.toPickUp.add(request);
		}
		

		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}

	}
}
