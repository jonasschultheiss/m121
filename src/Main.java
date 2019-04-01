import lejos.hardware.Button;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// StartApplication();
		Debug();
	}
	
	private static void Debug() {
		Display display = new Display();
		display.ShowStartScreen();

		Button.waitForAnyPress();
		
	}
	
	private static void StartApplication() throws InterruptedException {
		Task currentTask = new Task();
		currentTask.Start();
		
	}
}
