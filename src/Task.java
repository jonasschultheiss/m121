import java.util.concurrent.TimeUnit;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;

public class Task {

	private Display display;
	
	private Settings settings;
	
	private MovePilot pilot;
	
	private float currentDistance = 0.00f;
	
	public Task() {
		this.settings = new Settings();
		this.display = new Display(this.settings);
	}
	
	public void Start() {
		StartupSequence();
		DoStuff();
		EndingSequence();
	}
	
	private void DoStuff() {
		if (this.settings.getMode() == 0) {
			ModeZero();
		} else {
			// Don mode 2
		}
	}
	
	private void ModeZero () {
		CreateMovePilot();
		boolean isTravelingForward = false;
		boolean isGoalAnchieved = false;
		EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S4);
		ultra.enable();
		
		do {
			if (Math.signum((double)GetCurrentDistance(ultra))) {
				
			}
			
			
		} while (!isGoalAnchieved);
		
		
		this.pilot.setLinearSpeed(90);
		this.pilot.setLinearAcceleration(25);
		this.pilot.travel(20000);
		Sleep();
		this.pilot.travel(-20000);
		Sleep();
		this.pilot.stop();
	}
	
	private void ModeOne () {
		
	}
	
	private float GetCurrentDistance(EV3UltrasonicSensor ultra) {
		SampleProvider sample = ultra.getDistanceMode();
		float[] a = new float[99]; 
		sample.fetchSample(a, 0); 
		return (a[0] * 100);
	}
	
	private void CreateMovePilot () {
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 42.2).offset(72).gearRatio(2);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 42.2).offset(-72).gearRatio(2);
		Chassis chassis = new WheeledChassis( new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		this.pilot = new MovePilot(chassis);
	}
	
	private void StartupSequence() {
		this.display.ShowStartup();
		PlayStartHymn(settings.getVolume());
		Sleep();
		GetUserInput();
	}
	
	private void GetUserInput() {
		int state = 0;
		boolean isUserDone = false;
		do {
			this.display.ClearScreen();
			this.display.ShowStart(state);
			if (state == 0) {
				switch (Button.waitForAnyPress()) { 
					case Button.ID_ENTER: { 
						isUserDone = true;
						break;
					} 
					case Button.ID_DOWN: {
						state = 1;
						break;
					}
				}
			} else {
				switch (Button.waitForAnyPress()) { 
					case Button.ID_ENTER: {
						StartSettings();
						break;
					} 
					case Button.ID_UP: {
						state = 0;
						break;
					}
				}
			}
		} while (!isUserDone);
	}
	
	private void StartSettings  () {
		Button.LEDPattern(2);
		int state = 0;
		boolean isUserDone = false;
		do {
			this.display.ClearScreen();
			this.display.ShowSettings(state);
			switch (state) {
				case 0: {
					switch (Button.waitForAnyPress()) {
						case Button.ID_ESCAPE: {
							isUserDone = true;
							break;
						}
						case Button.ID_ENTER: {
							state = 1;
							break;
						}
						case Button.ID_DOWN: {
							state = 2;
							break;
						}
					}
					break;
				}
				case 1: {
					switch (Button.waitForAnyPress()) {
						case Button.ID_UP: {
							this.settings.changeMode();
							break;
						}
						case Button.ID_RIGHT: {
							this.settings.changeMode();
							break;
						}
						case Button.ID_DOWN: {
							this.settings.changeMode();
							break;
						}
						case Button.ID_LEFT: {
							this.settings.changeMode();
							break;
						}
						case Button.ID_ESCAPE: {
							state = 0;
							break;
						}
						case Button.ID_ENTER: {
							state = 0;
							break;
						}
					}
					break;
				}
				case 2: {
					switch (Button.waitForAnyPress()) {
						case Button.ID_ESCAPE: {
							isUserDone = true;
							break;
						}
						case Button.ID_ENTER: {
							state = 3;
							break;
						}
						case Button.ID_DOWN: {
							state = 4;
							break;
						}
						case Button.ID_UP: {
							state = 0;
							break;
						}
					}	
					break;	
				}
				case 3: {
					switch (Button.waitForAnyPress()) {
						case Button.ID_UP: {
							this.settings.IncrementWantedDistance();
							break;
						}
						case Button.ID_RIGHT: {
							this.settings.IncrementWantedDistance();
							break;
						}
						case Button.ID_DOWN: {
							this.settings.DecrementWantedDistance();
							break;
						}
						case Button.ID_LEFT: {
							this.settings.DecrementWantedDistance();
							break;
						}
						case Button.ID_ESCAPE: {
							state = 2;
							break;
						}
						case Button.ID_ENTER: {
							state = 2;
							break;
						}
					}
					break;
				}
				case 4: {
					switch (Button.waitForAnyPress()) {
						case Button.ID_ESCAPE: {
							isUserDone = true;
							break;
						}
						case Button.ID_ENTER: {
							state = 5;
							break;
						}
						case Button.ID_UP: {
							state = 2;
							break;
						}
					}
					break;
				}
				case 5: {
					switch (Button.waitForAnyPress()) {
						case Button.ID_UP: {
							this.settings.IncrementWantedDeviation();
							break;
						}
						case Button.ID_RIGHT: {
							this.settings.IncrementWantedDeviation();
							break;
						}
						case Button.ID_DOWN: {
							this.settings.DecrementWantedDeviation();
							break;
						}
						case Button.ID_LEFT: {
							this.settings.DecrementWantedDeviation();
							break;
						}
						case Button.ID_ESCAPE: {
							state = 4;
							break;
						}
						case Button.ID_ENTER: {
							state = 4;
							break;
						}
					}
					break;
				}
			}
		} while (!isUserDone);
		Button.LEDPattern(0);
	}
	
	private void EndingSequence() {
		this.display.ClearScreen();
		this.display.ShowEnding();
		PlayDoneHymn(this.settings.getVolume());
		Sleep();
	}
	
	private void Sleep() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void PlayStartHymn(int volume) {
		Sound.setVolume(volume);
		Sound.beepSequenceUp();
	}
	
	private void PlayDoneHymn(int volume) {
		Sound.setVolume(volume);
		Sound.beepSequence();
	}

/*
 * private int GetUserInput() { this.wantedDistance = DEFAULT_DISTANCE;
 * DisplayWantedDistance(); boolean isInputConfirmed = false;
 * 
 * while (isInputConfirmed == false) { switch (Button.waitForAnyPress()) { case
 * Button.ID_DOWN: { DecrementDistance(); break; } case Button.ID_LEFT: {
 * DecrementDistance(); break; } case Button.ID_UP: { IncrementDistance();
 * break; } case Button.ID_RIGHT: { IncrementDistance(); break; } case
 * Button.ID_ENTER: { isInputConfirmed = true; break; }
 * 
 * }
 * 
 * DisplayWantedDistance(); }
 * 
 * return 0; }
 * 
 * private void DriveSaidDistance () { boolean isUserDone = false;
 * EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S4);
 * ultra.enable(); Motor.B.setSpeed(100); Motor.C.setSpeed(100); while
 * (isUserDone == false) { SampleProvider sample = ultra.getDistanceMode();
 * float[] a = new float[99]; sample.fetchSample(a, 0); this.currentDistance =
 * a[0] * 100; if (this.currentDistance > this.wantedDistance) {
 * Motor.B.forward(); Motor.C.forward(); } else if (this.currentDistance <
 * this.wantedDistance) { Motor.B.backward(); Motor.C.backward(); } else if
 * (this.currentDistance == this.wantedDistance) { Motor.B.stop();
 * Motor.C.stop(); DisplayCurrentSituation(); isUserDone = true; } }
 * 
 * ultra.close(); }
 * 
 * private void DisplayWantedDistance() { LCD.clearDisplay();
 * LCD.drawString("distance = " + this.wantedDistance + "cm", 1, 3); }
 * 
 * private void DisplayCurrentSituation() { LCD.clearDisplay();
 * LCD.drawString("wanted=" + this.wantedDistance + "cm", 1, 3);
 * LCD.drawString("current=" + this.currentDistance + "cm", 2, 3); } }
 */
}	