package com.neu;

import java.util.List;

/**
 * Cooks are simulation actors that have at least one field, a name.
 * When running, a cook attempts to retrieve outstanding orders placed
 * by Eaters and process them.
 */
public class Cook implements Runnable {
	private final String name;
	private CoffeeShopManager cm = null;
	/**
	 * You can feel free modify this constructor.  It must
	 * take at least the name, but may take other parameters
	 * if you would find adding them useful. 
	 *
	 * @param: the name of the cook
	 */
	
	public Cook(String name) {
		this.name = name;
		Simulation.logEvent(SimulationEvent.cookStarting(this));
		cm = Simulation.getCm();
		busy = false;
	}

	public String toString() {
		return name;
	}
	public boolean busy = false;
	/**
	 * This method executes as follows.  The cook tries to retrieve
	 * orders placed by Customers.  For each order, a List<Food>, the
	 * cook submits each Food item in the List to an appropriate
	 * Machine, by calling makeFood().  Once all machines have
	 * produced the desired Food, the order is complete, and the Customer
	 * is notified.  The cook can then go to process the next order.
	 * If during its execution the cook is interrupted (i.e., some
	 * other thread calls the interrupt() method on it, which could
	 * raise InterruptedException if the cook is blocking), then it
	 * terminates.
	 */
	public void run() {

		//Simulation.logEvent(SimulationEvent.cookStarting(this));
		try {
			//your code goes here
			//CoffeeShopManager cm = Simulation.getCm();
			synchronized(cm)
			{
				while(true)
				{
					while(cm.getCurrentlyServing().size() == 0)
					{
						cm.wait(10);
					}
					if(cm.getCurrentlyServing().size() > 0)
					{
						busy = true;
						Customer servingCustomer = cm.getCurrentlyServing().get(0);

						List<Food> order = servingCustomer.getOrder();
						int orderNum = servingCustomer.getOrderNum();
						Simulation.logEvent(SimulationEvent.customerPlacedOrder(servingCustomer, order, orderNum));
						Simulation.logEvent(SimulationEvent.cookReceivedOrder(this, order, orderNum));
						for(Food f : order)
						{
							Machine m = cm.getMachineInstance(f);
							if(m != null)
							{
								//						System.out.println("Got machine:"+m.toString()+" for food:"+f.toString()+" for cust:"+
								//								servingCustomer.toString()+" by cook:"+this.toString()+" by:"+Thread.currentThread().getName());
								Simulation.logEvent(SimulationEvent.cookStartedFood(this, f, orderNum));
								boolean itemStatus = m.makeFood(orderNum);

								//Thread.currentThread().join();
								if(!itemStatus)
								{
									System.out.println("Cooking failed order:"+servingCustomer.toString()+" by cook: "+this.toString()+" by: "+Thread.currentThread().getName());
								}
								else
									Simulation.logEvent(SimulationEvent.cookFinishedFood(this, f, orderNum));
							}
							else
								System.out.println("Machine was not found in Cook class for food type:"+f.name);
						}
						Simulation.logEvent(SimulationEvent.cookCompletedOrder(this, orderNum));
						cm.getOrderStatus().put(servingCustomer, true);
						cm.getCurrentlyServing().remove(servingCustomer);
						Simulation.logEvent(SimulationEvent.customerReceivedOrder(servingCustomer, order, orderNum));
						Simulation.logEvent(SimulationEvent.customerLeavingCoffeeShop(servingCustomer));
						//System.out.println(servingCustomer+" removed, size: "+cm.getCurrentlyServing().size());
						cm.notifyAll();
					}
					else
						System.out.println("No orders by :"+this.toString()+" by:"+Thread.currentThread().getName());
					busy = false;
				}
				

			}
		}

		catch(InterruptedException e) {
			// This code assumes the provided code in the Simulation class
			// that interrupts each cook thread when all customers are done.
			// You might need to change this if you change how things are
			// done in the Simulation class.
			Simulation.logEvent(SimulationEvent.cookEnding(this));
		}

	}
}