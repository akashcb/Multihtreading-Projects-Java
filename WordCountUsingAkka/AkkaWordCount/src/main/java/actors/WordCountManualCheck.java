package actors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import actor.messages.Messages;
import akka.actor.ActorRef;
import akka.actor.Props;

public class WordCountManualCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("leng:"+("aaaaaaaaa".split(" ")).length);
		int total = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(new File("test_data/purity.txt")))) {
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.
				String[] words = line.split(" ");
				total += words.length;
				if(total > 12830)
					System.out.println(line);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("total:  "+total);
	}

}
