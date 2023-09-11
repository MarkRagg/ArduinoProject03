package RoomService.serial;

public class SerialCommunication {

	private boolean day;
	private boolean movement;
	private boolean lightOn;
	private int rollerBlindsAngle;
	private boolean automatic;

	public SerialCommunication(boolean day, boolean movement, boolean lightOn,
	        int rollerBlindsAngle, boolean automatic) {
		super();
		this.day = day;
		this.movement = movement;
		this.lightOn = lightOn;
		this.rollerBlindsAngle = rollerBlindsAngle;
		this.automatic = automatic;
	}

	public boolean isDay() {
		return day;
	}

	public void setDay(boolean day) {
		this.day = day;
	}

	public boolean isMovement() {
		return movement;
	}

	public void setMovement(boolean movement) {
		this.movement = movement;
	}

	public boolean isLightOn() {
		return lightOn;
	}

	public void setLightOn(boolean lightOn) {
		this.lightOn = lightOn;
	}

	public int getRollerBlindsAngle() {
		return rollerBlindsAngle;
	}

	public void setRollerBlindsAngle(int rollerBlindsAngle) {
		this.rollerBlindsAngle = rollerBlindsAngle;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	@Override
	public String toString() {
		return "SerialCommunication [day=" + day + ", movement=" + movement + ", lightOn=" + lightOn + ", rollerBlindsAngle=" + rollerBlindsAngle + ", automatic=" + automatic + "]";
	}
}
