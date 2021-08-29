package com.elevator.design;
import java.util.*;

import com.elevator.design.ElevatorScheduler.Request;
import com.elevator.design.Elevators.Elevator.Direction;

public class Elevators implements Runnable {
	
	private List<Elevator> elevators=new ArrayList<Elevator>();
	
	//Running the Elevators
	public void run() {
		this.runElevators();
	}
	
	private void runElevators() {
		Scanner scanner = new Scanner(System.in);
		
		for(Elevator elevator: elevators) {
			elevator.changeCurrentPosition();
			
			for(Request req : elevator.getToPickUp()) {
				//Picking Up the people at this floor
				System.out.println("The Elevator "+elevator.getName()+" has reached floor "+elevator.getCurrentPosition());
				if(req.getSource()==elevator.getCurrentPosition()) {
					System.out.println("Enter your Destination");
					req.setDestination(scanner.nextInt());
					
					elevator.getToPickUp().remove(req);
					elevator.addToDeliever(req);
				}
			}
			
			for(Request req : elevator.getToDeliever()) {
				//Dropping off the people at this floor
				if(req.getSource()==elevator.getCurrentPosition()) {
					elevator.getToDeliever().remove(req);
				}
			}
			
			//Selecting next Destination and Direction
			if(elevator.getCurrentPosition() == elevator.getNextDestination()) {
				int newNextDestination=elevator.selectNextDestination();
				elevator.setNextDestination(newNextDestination);
				if(elevator.selectNextDestination()<elevator.getCurrentPosition())
					elevator.setDirection(Direction.DOWN);
				else if(elevator.selectNextDestination()>elevator.getCurrentPosition())
					elevator.setDirection(Direction.UP);
				else
					elevator.setDirection(Direction.STEADY);
			}
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
		
		public int getNextDestination() {
			return nextDestination;
		}


		public int selectNextDestination() {
			if(this.toDeliever.isEmpty() && this.toPickUp.isEmpty())
				return this.currentPosition;
			
			if(this.direction == Direction.UP) {
				int newNextDest = this.nextDestination;
				for(Request req : this.getToDeliever()) {
					if(req.getDestination()>newNextDest)
						newNextDest=req.getDestination();
				}
				for(Request req : this.getToPickUp()) {
					if(req.getDestination()>newNextDest)
						newNextDest=req.getDestination();
				}
				if(newNextDest!=this.nextDestination)
					return newNextDest;
				
				//Looking for new destination on changing direction.
				for(Request req : this.getToDeliever()) {
					if(req.getDestination()<newNextDest)
						newNextDest=req.getDestination();
				}
				for(Request req : this.getToPickUp()) {
					if(req.getDestination()<newNextDest)
						newNextDest=req.getDestination();
				}
				return newNextDest;
			}
			else {
				int newNextDest = this.nextDestination;
				for(Request req : this.getToDeliever()) {
					if(req.getDestination()<newNextDest)
						newNextDest=req.getDestination();
				}
				for(Request req : this.getToPickUp()) {
					if(req.getDestination()<newNextDest)
						newNextDest=req.getDestination();
				}
				if(newNextDest!=this.nextDestination)
					return newNextDest;
				
				//Looking for new destination on changing direction.
				for(Request req : this.getToDeliever()) {
					if(req.getDestination()>newNextDest)
						newNextDest=req.getDestination();
				}
				for(Request req : this.getToPickUp()) {
					if(req.getDestination()>newNextDest)
						newNextDest=req.getDestination();
				}
				return newNextDest;
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
