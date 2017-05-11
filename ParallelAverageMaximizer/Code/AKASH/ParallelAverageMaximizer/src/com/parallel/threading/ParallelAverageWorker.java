package com.parallel.threading;

import java.util.LinkedList;

public class ParallelAverageWorker extends Thread{

	protected LinkedList<Integer> list;
	protected double partialAvg = 0;
	
	public ParallelAverageWorker(LinkedList<Integer> list)
	{
		this.list = list;
	}
	// Display a message, preceded by
	// the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}
	public void run()
	{
		while(true)
		{
			int number;
			// check if list is not empty and removes the head
			// synchronization needed to avoid atomicity violation
			synchronized(list) {
				if (list.isEmpty())
					return; // list is empty
				number = list.remove();
				threadMessage("Number retreived = "+number);
			}
			partialAvg = (partialAvg + number) / 2.0;
			threadMessage("partial average is = "+partialAvg);
		}
	}
	
}
