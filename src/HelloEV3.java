public class HelloEV3 {

	public static void main(String[] args) throws InterruptedException {
		StartApplication();
	}
	
	private static void StartApplication() throws InterruptedException {
		Task301 currentTask = new Task301();
		currentTask.Start();
		
	}
}
