import lejos.hardware.lcd.LCD;

public class Display {
	
	private String heading;
	private String line;
	private String p1;
	private String p2;
	private String p3;
	private String web;
	private Settings settings;
	
	
	public Display(Settings settings) {
		this.heading = "startup";
		this.line = "------------------";
		this.p1 = "modul 121";
		this.p2 = "distance finder";
		this.p3 = "by quest1onmark";
		this.web = "quest1onmark.dev";
		this.settings = settings;
	}
	
	public void ShowStartup() {
		LCD.drawString(heading, 0, 0);
		LCD.drawString(line, 0, 1);
		LCD.drawString(p1, 0, 3);
		LCD.drawString(p2, 0, 4);
		LCD.drawString(p3, 0, 5);
		LCD.drawString(web, 0, 7);
	}
	
	public void ShowEnding() {
		this.heading = "exiting";
		this.p1 = "goodbye";
		LCD.drawString(heading, 0, 0);
		LCD.drawString(line, 0, 1);
		LCD.drawString(p1, 0, 3);
	}
	
	public void ShowSettings (int state) {
		this.heading = "settings";
		switch (state) {
			case 0: {
				this.p1 = "<mode>: " + this.settings.getMode();
				this.p2 = "distance: " + this.settings.getWantedDistance();
				this.p3 = "deviation: " + this.settings.getWantedDeviation();
				break;
			}
			case 1: {
				this.p1 = "mode: <" + this.settings.getMode() + ">";
				this.p2 = "distance: " + this.settings.getWantedDistance();
				this.p3 = "deviation: " + this.settings.getWantedDeviation();
				break;
			}
			case 2: {
				this.p1 = "mode: " + this.settings.getMode();
				this.p2 = "<distance>: " + this.settings.getWantedDistance();
				this.p3 = "deviation: " + this.settings.getWantedDeviation();
				break;
			}
			case 3: {
				this.p1 = "mode: " + this.settings.getMode();
				this.p2 = "distance: <" + this.settings.getWantedDistance()+ ">";
				this.p3 = "deviation: " + this.settings.getWantedDeviation();
				break;
			}
			case 4: {
				this.p1 = "mode: " + this.settings.getMode();
				this.p2 = "distance: " + this.settings.getWantedDistance();
				this.p3 = "<deviation>: " + this.settings.getWantedDeviation();
				break;
			}
			case 5: {
				this.p1 = "mode: " + this.settings.getMode();
				this.p2 = "distance: " + this.settings.getWantedDistance();
				this.p3 = "deviation: <" + this.settings.getWantedDeviation() + ">";
				break;
			}
		}
		
		LCD.drawString(heading, 0, 0);
		LCD.drawString(line, 0, 1);
		LCD.drawString(p1, 0, 3);
		LCD.drawString(p2, 0, 4);
		LCD.drawString(p3, 0, 5);
	}
	
	public void ShowStart(int state) {
		this.heading = "startscreen";
		if (state == 0) {
			this.p1 = "<start>";
			this.p2 = "settings";
		} else {
			this.p1 = "start";
			this.p2 = "<settings>";
		}
		
		LCD.drawString(heading, 0, 0);
		LCD.drawString(line, 0, 1);
		LCD.drawString(p1, 0, 3);
		LCD.drawString(p2, 0, 4);
	}
	
	public void ClearScreen() {
		LCD.clearDisplay();
	}
	
	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}
	
	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}
	
	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}
	
	
}
