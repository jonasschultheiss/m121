
public class Settings {
	private float MAX_DISTANCE;
	private float MIN_DISTANCE;
	private float WantedDistance;
	private int Mode; // 0 = Stop direclty, 1 = wait x seconds
	private int Volume;
	private float MAX_DEVIATION;
	private float MIN_DEVIATION;
	private float WantedDeviation;
	private int HoldPositionTime;
	
	public Settings() {
		this.MAX_DISTANCE = 50;
		this.MIN_DISTANCE = 1;
		this.MAX_DEVIATION = 5;
		this.MIN_DEVIATION = 1;
		this.WantedDistance = 10;
		this.setMode(0);
		this.Volume = 5;
		this.WantedDeviation = 2;
		this.setHoldPositionTime(3);
	}

	public float getWantedDistance() {
		return this.WantedDistance;
	}
	
	public float getWantedDeviation() {
		return this.WantedDeviation;
	}

	public void IncrementWantedDistance() {
		if (this.WantedDistance < this.MAX_DISTANCE) {
			this.WantedDistance++;
		}
	}
	
	public void DecrementWantedDistance() {
		if (this.WantedDistance > this.MIN_DISTANCE) {
			this.WantedDistance--;
		}	
	}
	
	public void IncrementWantedDeviation() {
		if (this.WantedDeviation < this.MAX_DEVIATION) {
			this.WantedDeviation++;
		}
	}
	
	public void DecrementWantedDeviation() {
		if (this.WantedDeviation > this.MIN_DEVIATION) {
			this.WantedDeviation--;
		}	
	}
	
	public void changeMode() {
		if (this.Mode == 0) {
			this.Mode = 1;
		} else {
			this.Mode = 0;
		}
	}

	public int getVolume() {
		return this.Volume;
	}

	public void setVolume(int volume) {
		this.Volume = volume;
	}

	public int getMode() {
		return this.Mode;
	}

	public void setMode(int mode) {
		this.Mode = mode;
	}

	public int getHoldPositionTime() {
		return this.HoldPositionTime;
	}

	public void setHoldPositionTime(int holdPositionTime) {
		this.HoldPositionTime = holdPositionTime;
	}
	
	
}
