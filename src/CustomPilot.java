import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class CustomPilot {
	
	private MovePilot pilot;
	
	private Boolean isGoingForward;
	
	public CustomPilot() {
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 42.2).offset(72).gearRatio(2);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 42.2).offset(-72).gearRatio(2);
		Chassis chassis = new WheeledChassis( new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		this.pilot = new MovePilot(chassis);
		this.pilot.setLinearSpeed(70);
		this.pilot.setLinearAcceleration(30);
		this.isGoingForward = null;
	}
	
	public void Forwards () {
		if (this.isGoingForward == null || isGoingForward == false) {
			this.pilot.travel(-20000, true);
			this.isGoingForward = true;
		}
	}
	
	public void Backwards () {
		if (this.isGoingForward == null || isGoingForward == true) {
			this.pilot.travel(20000, true);
			this.isGoingForward = false;
		}
	}
	
	public void Stop () {
		this.isGoingForward = null;
		this.pilot.stop();
	}
}
