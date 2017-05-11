package com.parallel.threading;

import java.util.ArrayList;
import java.util.LinkedList;

public class ParallelMaximizer {

	int numThreads;
	ArrayList<ParallelMaximizerWorker> workers; // = new ArrayList<ParallelMaximizerWorker>(numThreads);

	public ParallelMaximizer(int numThreads) {
		System.out.println("Number of threads passed to constructor: "+numThreads);
		this.numThreads = numThreads;
		workers = new ArrayList<ParallelMaximizerWorker>(numThreads);
		System.out.println("Workers size in constructor: "+ workers.size());
	}

	public static void main(String[] args) {
		
		int countThreads = 4; // number of threads for the maximizer
		int numElements = 100; // number of integers in the list

		ParallelMaximizer maximizer = new ParallelMaximizer(countThreads);
		
		System.out.println("Num thread value in main: " + maximizer.numThreads +" Thread name: "+Thread.currentThread().getName());
		System.out.println("Maximizer list size while creating: "+ maximizer.workers.size());
		LinkedList<Integer> list = new LinkedList<Integer>();

		// populate the list
		// TODO: change this implementation to test accordingly
		for (int i=0; i < numElements; i++) 
			list.add(i);
		//System.out.println("Printing LinkedList values");

		// run the maximizer
		try {
			System.out.println(maximizer.max(list));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds the maximum by using <code>numThreads</code> instances of
	 * <code>ParallelMaximizerWorker</code> to find partial maximums and then
	 * combining the results.
	 * @param list <code>LinkedList</code> containing <code>Integers</code>
	 * @return Maximum element in the <code>LinkedList</code>
	 * @throws InterruptedException
	 */
	public int max(LinkedList<Integer> list) throws InterruptedException {
		int max = Integer.MIN_VALUE; // initialize max as lowest value

		//System.out.println("Worker's Size: "+ workers.size());

		//System.out.println("Num threads value is: "+numThreads+"Thread name: "+Thread.currentThread().getName());
		// run numThreads instances of ParallelMaximizerWorker
		for (int i=0; i < numThreads; i++) {
			//System.out.println("Iteration number: "+ i);
			workers.add(i, new ParallelMaximizerWorker(list));
			workers.get(i).start();
			threadMessage("Started execution for: "+workers.get(i).getName());
		}
		
		// wait for threads to finish
		for (int i = 0; i < workers.size(); i++)
			workers.get(i).join();

		// take the highest of the partial maximums
		// TODO: IMPLEMENT CODE HERE
		for(int i=0; i< workers.size(); i++)
		{
			int workerMax = workers.get(i).getPartialMax();
			if(workerMax >= max)
			{
				max = workerMax;
			}
		}
		return max;
	}

	// Display a message, preceded by
	// the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}
}
