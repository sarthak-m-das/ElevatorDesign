package com.elevator.design;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.elevator.design.ElevatorScheduler.ElevatorScheduler;
import com.elevator.design.ElevatorSimulator.Elevator;
import com.elevator.design.ElevatorSimulator.ElevatorSimulator;

public class Main {
	public static void main(String[] args) throws SecurityException, IOException {
		
		ElevatorSimulator elevators = new ElevatorSimulator();
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
