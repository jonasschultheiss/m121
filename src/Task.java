import java.util.concurrent.TimeUnit;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Task {

	private Display display;
	
	private Settings settings;
	
	private CustomPilot pilot;
	
	public static boolean isUserDone;
	
	public Task() {
		this.settings = new Settings();
		this.display = new Display(this.settings);
		isUserDone = false;
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
			ModeOne();
		}
	}
	
	private void ModeZero () {
		Button.ENTER.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key k) {
				isUserDone = true;
			}

			@Override
			public void keyReleased(Key k) {}
		});
		
		CreateMovePilot();
		EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S4);
		ultra.enable();
		
		do {
			float currentDistance = GetCurrentDistance(ultra);
			float difference = this.settings.getWantedDistance() - currentDistance;
			if (Math.signum(difference) == 1.0f) {
				if (AreCriteriasMet(difference)) {
					this.pilot.Stop();
					
				} else {
					this.pilot.Forwards();
				}
			} else {
				if (AreCriteriasMet(difference)) {
					this.pilot.Stop();
				} else {
					this.pilot.Backwards();
				}
			}
			
		} while (!isUserDone);
		
		this.pilot.Stop();
		ultra.close();
	
	}
	
	private void ModeOne () {
		CreateMovePilot();
		boolean isGoalAnchieved = false;
		EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S4);
		ultra.enable();
		
		do {
			float currentDistance = GetCurrentDistance(ultra);
			float difference = this.settings.getWantedDistance() - currentDistance;
			if (Math.signum(difference) == 1.0f) {
				if (AreCriteriasMet(difference)) {
					isGoalAnchieved = true;
				} else {
					this.pilot.Forwards();
				}
			} else {
				if (AreCriteriasMet(difference)) {
					isGoalAnchieved = true;
				} else {
					this.pilot.Backwards();
				}
			}
			
		} while (!isGoalAnchieved);
		
		this.pilot.Stop();
		ultra.close();
	}
	
	private boolean AreCriteriasMet(float difference) {
		if (Math.signum(difference) != 1) {
			difference = Math.abs(difference);
		}
		
		if (difference <= this.settings.getWantedDeviation()) {
			return true;
		} else {
			return false;
		}
	}
	
	private float GetCurrentDistance(EV3UltrasonicSensor ultra) {
		SampleProvider sample = ultra.getDistanceMode();
		float[] a = new float[1]; 
		sample.fetchSample(a, 0); 
		return (a[0] * 100);
	}
	
	private void CreateMovePilot () {
		this.pilot = new CustomPilot();
	}
	
	private void StartupSequence() {
		this.display.ShowStartup();
		Button.LEDPattern(1);
		PlayStartHymn(settings.getVolume());
		// Sleep();
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
		Button.LEDPattern(4);
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
		Button.LEDPattern(1);
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