package actors;

import actor.messages.Messages;
import actor.messages.Messages.PrintResults;
import actor.messages.Messages.ProcessLine;
import akka.actor.UntypedActor;

/**
 * This actor counts number words in a single line
 * 
 * @author akashnagesh
 *
 */
public class WordsInLineActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof ProcessLine)
		{
			final String line = ((ProcessLine) msg).getLine();
			if(line.length() > 0)
			{
				int count = 0;
				if(line.contains(" "))
				{
					String[] a = line.split(" ");
					count = a.length;	
				}
				getContext().parent().tell(new Messages.ResultantLineWordCount(count), getSelf());
				//getSelf().tell(new Messages.PrintResults(), getSelf());
			}
		}
		else
		{
			unhandled(msg);
		}
//		else if(msg instanceof PrintResults)
//		{
//			getContext().stop(getSelf());
//		}
	}
}
