import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor; 
import lejos.robotics.SampleProvider;

public class Task {

	private float DEFAULT_DISTANCE = 10;
	private float MIN_DISTANCE;
	private float MAX_DISTANCE;

	private float wantedDistance;
	private float currentDistance;

	private Display display;
	
	public Task() {
		display = new Display();
		DEFAULT_DISTANCE = 10;
		MIN_DISTANCE = 1;
		MAX_DISTANCE = 50;
	}
	
	private void IncrementDistance () {
		if (this.wantedDistance < MAX_DISTANCE) {
			this.wantedDistance++;
		}
	}
	
	private void DecrementDistance () {
		if (this.wantedDistance > MIN_DISTANCE) {
			this.wantedDistance--;
		}
	}
	
	public void Start() {
		PlayStartHymn(10);
		GetUserInput();
		DriveSaidDistance();
		PlayDoneHymn(10);
	}

	private void PlayStartHymn(int volume) {
		Sound.setVolume(volume);
		Sound.beepSequenceUp();
	}
	
	private void PlayDoneHymn(int volume) {
		Sound.setVolume(volume);
		Sound.beepSequence();
	}

	private int GetUserInput() {
		this.wantedDistance = DEFAULT_DISTANCE;
		DisplayWantedDistance();
		boolean isInputConfirmed = false;

		while (isInputConfirmed == false) {
			switch (Button.waitForAnyPress()) {
				case Button.ID_DOWN: {
					DecrementDistance();
					break;
				}
				case Button.ID_LEFT: {
					DecrementDistance();
					break;
				}
				case Button.ID_UP: {
					IncrementDistance();
					break;
				}
				case Button.ID_RIGHT: {
					IncrementDistance();
					break;
				}
				case Button.ID_ENTER: {
					isInputConfirmed = true;
					break;
				}
				
			}
			
			DisplayWantedDistance();
		}
		
		return 0;
	}
	
	private void DriveSaidDistance () {
		boolean isUserDone = false;
		EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S4);
		ultra.enable();
		Motor.B.setSpeed(100);
		Motor.C.setSpeed(100);
		while (isUserDone == false) {
			SampleProvider sample = ultra.getDistanceMode();
			float[] a = new float[99];
		    sample.fetchSample(a, 0);
		    this.currentDistance = a[0] * 100;
		    if (this.currentDistance > this.wantedDistance) {
		    	Motor.B.forward();
		    	Motor.C.forward();
		    } else if (this.currentDistance < this.wantedDistance) {
		    	Motor.B.backward();
		    	Motor.C.backward();
		    } else if (this.currentDistance == this.wantedDistance) {
		    	Motor.B.stop();
		    	Motor.C.stop();
		    	DisplayCurrentSituation();
		    	isUserDone = true;
		    }
		}
		
		ultra.close();
	}

	private void DisplayWantedDistance() {
		LCD.clearDisplay();
		LCD.drawString("distance = " + this.wantedDistance + "cm", 1, 3);
	}
	
	private void DisplayCurrentSituation() {
		LCD.clearDisplay();
		LCD.drawString("wanted=" + this.wantedDistance + "cm", 1, 3);
		LCD.drawString("current=" + this.currentDistance + "cm", 2, 3);
	}
}