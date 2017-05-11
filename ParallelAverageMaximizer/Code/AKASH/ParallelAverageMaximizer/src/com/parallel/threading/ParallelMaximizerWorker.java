package com.parallel.threading;

import java.util.LinkedList;

public class ParallelMaximizerWorker  extends Thread {

	protected LinkedList<Integer> list;
	protected int partialMax = Integer.MIN_VALUE; // initialize to lowest value

	protected int partialAvg = 0;
	public ParallelMaximizerWorker(LinkedList<Integer> list) {
		this.list = list;
	}
	// Display a message, preceded by
	// the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

/**
	 * Update <code>partialMax</code> until the list is exhausted.
	 */
	public void run() {
		while (true) {
			
			int number;
			// check if list is not empty and removes the head
			// synchronization needed to avoid atomicity violation
			synchronized(list) {
				if (list.isEmpty())
					return; // list is empty
				number = list.remove();
				threadMessage("Number retreived = "+number);
			}
			//threadMessage("current number is: " + number);
			// update partialMax according to new value
			// TODO: IMPLEMENT CODE HERE
			if(number >= partialMax)
			{
				partialMax = number;
				threadMessage("Partial Max set for this thread is : "+partialMax);
			}
				
		}
	}



	public int getPartialMax() {
		return partialMax;
	}
}
