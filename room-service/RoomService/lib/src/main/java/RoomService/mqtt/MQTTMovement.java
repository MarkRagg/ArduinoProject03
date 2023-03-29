package RoomService.mqtt;

public class MQTTMovement {

	private boolean movementState;

	public MQTTMovement(final boolean movementState) {
		super();
		this.movementState = movementState;
	}

	public boolean getMovementState() {
		return movementState;
	}

	public void setMovementState(final boolean movementState) {
		this.movementState = movementState;
	}
}
