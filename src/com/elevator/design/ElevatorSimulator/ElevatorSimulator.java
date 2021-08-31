package com.elevator.design.ElevatorSimulator;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.FileHandler;

import com.elevator.design.ElevatorScheduler.Request;
import com.elevator.design.ElevatorSimulator.Elevator.Direction;

public class ElevatorSimulator implements Runnable {
	
	private List<Elevator> elevators=new ArrayList<Elevator>();
	
	private FileHandler handler;
	private Logger Log;
	
	public ElevatorSimulator() throws SecurityException, IOException{
		handler = new FileHandler("ElevatorSimulator.log", true);
		Log = Logger.getLogger("Elevator Simulation");
		Log.addHandler(handler);
	}
	
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
			if(elevator.getDirection() == Direction.STEADY)
					System.out.println("The Elevator "+elevator.getName()+" is on floor "+elevator.getCurrentPosition());
			else	
					System.out.println("The Elevator "+elevator.getName()+" is on floor "+elevator.getCurrentPosition()+" and moving "+elevator.getDirection());
			
			int f=0;
			//Picking Up the people at this floor
			for(Request req : elevator.getRequestList()) {
				if(req.getSource()==elevator.getCurrentPosition() && !req.isPickedUp()) {
					f=1;
					System.out.println("Enter your Destination : ");
					req.setDestination(scanner.nextInt());
					req.setPickedUp(true);
				}
			}
			
			//Dropping off the people at this floor
			List<Integer> toDroplist = new ArrayList<Integer>();
			for(int i=0;i< elevator.getRequestList().size();i++) {
				Request req = elevator.getRequestList().get(i);
				if( req.isPickedUp() && req.getDestination()==elevator.getCurrentPosition())
					toDroplist.add(i);
			}
			for(int toDrop:toDroplist) {
				elevator.getRequestList().remove(toDrop);
				f=1;
			}
			
			//Selecting next Destination and Direction
			if(f==1)
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
}
