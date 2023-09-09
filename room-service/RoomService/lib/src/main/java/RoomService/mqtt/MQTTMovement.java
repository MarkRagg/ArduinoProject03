package RoomService.mqtt;

public class MQTTMovement {

	private boolean movement;

	public MQTTMovement(final boolean movement) {
		super();
		this.movement = movement;
	}

	public boolean getMovementState() {
		return movement;
	}

	public void setMovementState(final boolean movement) {
		this.movement = movement;
	}
}
