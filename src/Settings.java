
public class Settings {
	private float MAX;
	private float MIN;
	private float WantedDistance;
	private int Mode; // 0 = Stop direclty, 1 = wait x seconds
	private int Volume;
	private float MaxDeviation;
	private int HoldPositionTime;
	
	public Settings() {
		this.MAX = 50;
		this.MIN = 1;
		this.WantedDistance = 10;
		this.setMode(0);
		this.Volume = 100;
		this.setMaxDeviation(5.00f);
		this.setHoldPositionTime(3);
	}

	public float getWantedDistance() {
		return this.WantedDistance;
	}

	public void IncrementWantedDistance(float wantedDistance) {
		if (this.WantedDistance < this.MAX) {
			this.WantedDistance++;
		}
	}
	
	public void DecrementWantedDistance(float wantedDistance) {
		if (this.WantedDistance > this.MIN) {
			this.WantedDistance--;
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

	public float getMaxDeviation() {
		return this.MaxDeviation;
	}

	public void setMaxDeviation(float maxDeviation) {
		this.MaxDeviation = maxDeviation;
	}

	public int getHoldPositionTime() {
		return this.HoldPositionTime;
	}

	public void setHoldPositionTime(int holdPositionTime) {
		this.HoldPositionTime = holdPositionTime;
	}
	
	
}
