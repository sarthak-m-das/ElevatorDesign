package com.elevator.design;

import java.util.ArrayList;
import java.util.List;

import com.elevator.design.Elevators.Elevator;


public class Main {
	public static void main(String[] args) {
		
		Elevators elevators = new Elevators();
		Elevator elevatorA = new Elevator();
		elevatorA.setName("A");
		Elevator elevatorB = new Elevator();
		elevatorB.setName("B");
		List<Elevator> elevatorList = new ArrayList<Elevator>();
		elevatorList.add(elevatorA);
		elevatorList.add(elevatorB);
		elevators.setElevators(elevatorList);
		
		//running the elevator on a different thread
		Thread elevatorRunningThread = new Thread(elevators);
		elevatorRunningThread.start();
		
		//Running Scheduler on the main thread
		ElevatorScheduler elevatorScheduler = new ElevatorScheduler();
		elevatorScheduler.requestlistener(elevators);
	}
}
