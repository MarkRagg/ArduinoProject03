package RoomService;

import java.util.ArrayList;
import java.util.List;

import RoomService.mqtt.MQTTMovement;
import RoomService.mqtt.MQTTMsg;

public class RoomState {

	private static RoomState instance = null;
	
	private List<MQTTMsg> lightStateHistory;
	private List<MQTTMovement> movementStateHistory;
	
	private RoomState() {
		this.lightStateHistory = new ArrayList<>();
		this.movementStateHistory = new ArrayList<>();
	}
	
	public static RoomState getInstance() {
        if (instance == null) {
            instance = new RoomState();
        }
        return instance;
    }

	public synchronized List<MQTTMsg> getLightStateHistory() {
		return lightStateHistory;
	}
	
	public synchronized MQTTMsg getLastLightState() {
		return lightStateHistory.get(lightStateHistory.size());
	}

	public synchronized List<MQTTMovement> getMovementStateHistory() {
		return movementStateHistory;
	}	
	
	public synchronized MQTTMovement getLastMovementState() {
		return movementStateHistory.get(movementStateHistory.size());
	}
}
