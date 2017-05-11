package actors;

import actor.messages.Messages.StartWordCount;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Main class for your wordcount actor system.
 * 
 * @author akashnagesh
 *
 */
public class WordCount {

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("wordcounter");
		
		/*
		 * Create the WordCountActor and send it the StartProcessingFolder
		 * message. Once you get back the response, use it to print the result.
		 * Remember, there is only one actor directly under the ActorSystem.
		 * Also, do not forget to shutdown the actorsystem
		 */
		
		//Creating props for actors
		Props wordCountActorProps = Props.create(WordCountActor.class);
		ActorRef af = system.actorOf(wordCountActorProps, "wordCounterActor");
		af.tell(new StartWordCount("input_data"), ActorRef.noSender());
		
		//system.terminate();
		
		
		
	}

}
