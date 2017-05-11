package com.parallel.threading;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class ParallelAverager {

	int numThreads;
	ArrayList<ParallelAverageWorker> workers;
	public ParallelAverager(int numThreads)
	{
		System.out.println("Number of threads passed to constructor: " + numThreads);
		this.numThreads = numThreads;
		workers = new ArrayList<ParallelAverageWorker>(numThreads);
		System.out.println("Workers size in constructor: "+ workers.size());
	}

	public static void main(String[] args) throws InterruptedException 
	{
		int countThreads = 4; // number of threads for the maximizer
		int numElements = 10; // number of integers in the list

		ParallelAverager averager = new ParallelAverager(countThreads);

		System.out.println("Num thread value in main: " + averager.numThreads +" Thread name: "+Thread.currentThread().getName());
		System.out.println("Maximizer list size while creating: "+ averager.workers.size());
		
		LinkedList<Integer> list = new LinkedList<Integer>();

		// populate the list
		// TODO: change this implementation to test accordingly
		for (int i = 0; i < 100; i++) 
			list.add(i);
		System.out.println("Number of elements in linked list: "+list.size());

		System.out.println("Final Average is: "+ averager.averageOut(list));
	}
	
	public double averageOut(LinkedList<Integer> list) throws InterruptedException
	{
		double average = 0.0;
		int count = 0;
		for(int i = 0; i <= numThreads; i++)
		{
			System.out.println("Iteration number: "+ i);
			workers.add(i, new ParallelAverageWorker(list));
			threadMessage("starting thread");
			workers.get(i).start();
			threadMessage("current average: "+workers.get(i).partialAvg);
		}
		
		for(int i = 0; i < numThreads; i++)
		{
			workers.get(i).join();
		}
		for(int i = 0; i < numThreads; i++)
		{
			threadMessage(workers.get(i).getName()+" partial average is: "+workers.get(i).partialAvg);
			average += workers.get(i).partialAvg;
			count ++;
		}
		return average/count;
	}
	
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

}
