package actors;

import java.io.File;

import actor.messages.Messages;
import actor.messages.Messages.PrintResults;
import actor.messages.Messages.ResultantFileWordCount;
import actor.messages.Messages.StartWordCount;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UnhandledMessage;
import akka.actor.UntypedActor;

/**
 * This is the main actor and the only actor that is created directly under the
 * {@code ActorSystem} This actor creates more child actors
 * {@code WordCountInAFileActor} depending upon the number of files in the given
 * directory structure
 * 
 * @author akashnagesh
 *
 */
public class WordCountActor extends UntypedActor {

	File[] files = null;
	int[] wordCountArray = null;
	int childNum = 0;
	public WordCountActor() {

	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		
		
		if(msg instanceof StartWordCount)
		{
			//System.out.println("WCA: Request received from: " + this.getSender().path());
			String path = ((StartWordCount) msg).getPath();
			//System.out.println("WordCount: path received: "+path);

			//Get file names
			final File folder = new File(path);
			files = folder.listFiles();
			wordCountArray = new int[files.length];
			for(File f : files)
			{
				//System.out.println("Processing: "+f.getName() + " --by " + getSelf().path());
				ActorRef af = getContext().actorOf(Props.create(WordCountInAFileActor.class));
				af.tell(new Messages.ProcessFile(path, f.getName()), getSelf());
				childNum++;
			}
			

		}
		else if(msg instanceof ResultantFileWordCount)
		{
			
			String fileName = ((ResultantFileWordCount) msg).getFileName();
			int count = ((ResultantFileWordCount) msg).getCount();
			//System.out.println("Reached resultantfilewordcount: "+getSelf().path() +" with: "+fileName+" count: "+count);
			for(int i = 0; i < files.length; i++)
			{
				if(files[i].getName().equals(fileName))
				{
					wordCountArray[i] = count;
					childNum--;
				}
					
			}
			if(childNum == 0)
			{
				getSelf().tell(new Messages.PrintResults(), getSelf());
			}
		}
		else if(msg instanceof PrintResults)
		{
			Long total = 0l;
			for(int i = 0; i < files.length; i++)
			{
				System.out.println("file: "+files[i].getName()+" word count: " + wordCountArray[i]);
				total += wordCountArray[i];
			}
			
			System.out.println("Final word count for all files: "+total);
		}
		else
		{
			unhandled(msg);
		}
		
	}

}
