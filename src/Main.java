public class Main {

	public static void main(String[] args) throws InterruptedException {
		StartApplication();
	}
	
	private static void StartApplication() throws InterruptedException {
		Task currentTask = new Task();
		currentTask.Start();
	}
}
