package com.parallel.threading;
import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

public class PublicTest {

	private int	threadCount = 10; // number of threads to run
	private ParallelMaximizer maximizer = new ParallelMaximizer(threadCount);
	private ParallelAverager averager = new ParallelAverager(threadCount);
	@Test
	public void compareMax() {
		int size = 10000; // size of list
		LinkedList<Integer> list = new LinkedList<Integer>();
		Random rand = new Random();
		int serialMax = Integer.MIN_VALUE;
		int parallelMax = 0;
		// populate list with random elements
		for (int i=0; i < size; i++) {
			int next = rand.nextInt(10000);
			list.add(next);
			serialMax = Math.max(serialMax, next); // compute serialMax
		}
		// try to find parallelMax
		try {
			parallelMax = maximizer.max(list);
			System.out.println("Parallel Max is: "+parallelMax);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("The test failed because the max procedure was interrupted unexpectedly.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("The test failed because the max procedure encountered a runtime error: " + e.getMessage());
		}
		
		assertEquals("The serial max doesn't match the parallel max", serialMax, parallelMax);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void compareAvg()
	{
		int size = 10000; // size of list
		LinkedList<Integer> list = new LinkedList<Integer>();
		Random rand = new Random();
		double serialAvg = Double.MIN_VALUE;
		double parallelAvg = 0;
		// populate list with random elements
		
		for (int i=0; i < size; i++) {
			int next = rand.nextInt(10000);
			list.add(next);
			serialAvg = (serialAvg + next)/2.0; // compute serialAvg
		}
		try {
			parallelAvg = averager.averageOut(list);
			System.out.println("Parallel Avg is: " + parallelAvg);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("The test failed because the max procedure was interrupted unexpectedly.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("The test failed because the max procedure encountered a runtime error: " + e.getMessage());
		}
		assertEquals("Serial avg does not match with parallel avg",serialAvg, parallelAvg);
	}
}
