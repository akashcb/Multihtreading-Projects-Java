package com.neu;

import java.util.List;

/**
 * Customers are simulation actors that have two fields: a name, and a list
 * of Food items that constitute the Customer's order.  When running, an
 * customer attempts to enter the coffee shop (only successful if the
 * coffee shop has a free table), place its order, and then leave the 
 * coffee shop when the order is complete.
 */
public class Customer implements Runnable {
	//JUST ONE SET OF IDEAS ON HOW TO SET THINGS UP...
	private final String name;
	private final List<Food> order;
	private final int orderNum;    
	private Object lock1 = new Object();
	private static int runningCounter = 0;
	private CoffeeShopManager cm = null;

	/**
	 * You can feel free modify this constructor.  It must take at
	 * least the name and order but may take other parameters if you
	 * would find adding them useful.
	 */
	public Customer(String name, List<Food> order) {
		this.name = name;
		this.order = order;
		this.orderNum = ++runningCounter;
		Simulation.logEvent(SimulationEvent.customerStarting(this));
		cm = Simulation.getCm();
	}

	public String toString() {
		return name;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public List<Food> getOrder() {
		return order;
	}

	/** 
	 * This method defines what an Customer does: The customer attempts to
	 * enter the coffee shop (only successful when the coffee shop has a
	 * free table), place its order, and then leave the coffee shop
	 * when the order is complete.
	 */
	public void run() {
		//YOUR CODE GOES HERE...
		synchronized(cm)
		{
			//CoffeeShopManager cm = Simulation.getCm();
			//System.out.println(name+ " currentlyServing size:"+cm.getCurrentlyServing().size());
			while(cm.getCurrentlyServing().size() >= cm.getNumTables())
			{
				
				try {
					//System.out.println("Waiting customer: "+name+" currentlyServing size:"+cm.getCurrentlyServing().size()+" by: "+Thread.currentThread().getName());
					cm.wait(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if(cm.getCurrentlyServing().size() < cm.getNumTables())
			{
				Simulation.logEvent(SimulationEvent.customerEnteredCoffeeShop(this));
				cm.getCurrentlyServing().add(this);
				System.out.println(name+" added, size: "+cm.getCurrentlyServing().size());
				cm.getOrderStatus().put(this, false);
//				while(!cm.getOrderStatus().get(this))
//				{
//					System.out.println("."+Thread.currentThread().getName());
//				}
				cm.notifyAll();
			}
		}
	}
}