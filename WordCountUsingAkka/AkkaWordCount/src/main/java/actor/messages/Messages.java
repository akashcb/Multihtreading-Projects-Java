package actor.messages;

/**
 * Messages that are passed around the actors are usually immutable classes.
 * Think how you go about creating immutable classes:) Make them all static
 * classes inside the Messages class.
 * 
 * This class should have all the immutable messages that you need to pass
 * around actors. You are free to add more classes(Messages) that you think is
 * necessary
 * 
 * @author akashnagesh
 *
 */
public class Messages {

	
	//Messages for WordCountActor Class
	public static class StartWordCount{
		String path;
		
		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public StartWordCount(String path)
		{
			this.path = path;
		}
	};
	
	public static class PrintResults{};

	public static class ResultantFileWordCount{
		String fileName;
		int count;
		public ResultantFileWordCount(String fileName, int count)
		{
			this.fileName =  fileName;
			this.count = count;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
	};
	
	public static class ResultantLineWordCount{
		int count;
		public ResultantLineWordCount(int count)
		{
			this.count = count;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
	};
	
	
	//Messages for WordCountInAFileActor Class
	public static class ProcessFile{
		String path;
		String fileName;
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public ProcessFile(String path, String fileName)
		{
			this.path = path;
			this.fileName = fileName;
		}
	}
	
	public static class ProcessLine{
		String line;
		public ProcessLine(String line)
		{
			this.line = line;
		}
		public String getLine() {
			return line;
		}
		public void setLine(String line) {
			this.line = line;
		}
	}
	
	public static class EOFFile{};
	
	
}