import lejos.hardware.lcd.LCD;

public class Display {
	
	private String heading = "modul 121";
	private String line = "------------------";
	private String p1 = "distance finder";
	private String p2 = "by quest1onmark";
	private String p3 = "quest1onmark.dev";
	
	
	public Display() {
		
	}
	
	public void ShowStartScreen() {
		LCD.drawString(heading, 0, 1);
		LCD.drawString(line, 0, 2);
		LCD.drawString(p1, 0, 3);
		LCD.drawString(p2, 0, 4);
		LCD.drawString(p3, 0, 5);
	}
	
	public void ClearStartScreen() {
		LCD.clearDisplay();
	}
}
