package actors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

import actor.messages.Messages;
import actor.messages.Messages.ProcessFile;
import actor.messages.Messages.ResultantLineWordCount;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * this actor reads the file line by line and sends them to
 * {@code WordsInLineActor} to count the words in line. Upon geting the results,
 * It sends the result to it's parent actor {@code WordCount}
 * 
 * @author akashnagesh
 *
 */
public class WordCountInAFileActor extends UntypedActor {

	int wordCount = 0;
	int lineNum = -1;
	String fileName = "";
	public WordCountInAFileActor() {
		// TODO Auto-generated constructor stub
		lineNum = 0;
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		
		if(msg instanceof ProcessFile)
		{
			//System.out.println("WCIFA: Request received from: " + this.getSender().path());
			ProcessFile message = (ProcessFile)msg;
			final String filePath = message.getPath()+"/"+message.getFileName();
			this.fileName += message.getFileName();
			//Read file
			if(message.getFileName().contains(".txt"))
			{
				System.out.println("-----Processing File: " + message.getFileName()+ " ----------");
				try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
					String line;
					while ((line = br.readLine()) != null) {
						// process the line.
						if(line.contains(" "))
						{
							ActorRef af = getContext().actorOf(Props.create(WordsInLineActor.class));
							af.tell(new Messages.ProcessLine(line), getSelf());
							lineNum ++;	
						}
//						else
//							System.out.println("prob: "+line);
						
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}

		}
		else if(msg instanceof ResultantLineWordCount)
		{
			//System.out.println("WordCount: " + wordCount+" for: " + this.fileName);
			wordCount += ((ResultantLineWordCount) msg).getCount();
			lineNum --;
			if(lineNum == 0)
			{
				getContext().parent().tell(new Messages.ResultantFileWordCount(fileName, wordCount), getSelf());
			}
			
		}
		else
		{
			unhandled(msg);
		}
	}

}
