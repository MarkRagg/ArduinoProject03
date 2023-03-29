package RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public synchronized Optional<MQTTMsg> getLastLightState() {
	    if(lightStateHistory.size() == 0) {
	        return Optional.empty();
	    }
	    return Optional.of(lightStateHistory.get(lightStateHistory.size() - 1));
	}

	public synchronized List<MQTTMovement> getMovementStateHistory() {
		return movementStateHistory;
	}	
	
	public synchronized Optional<MQTTMovement> getLastMovementState() {
       if(movementStateHistory.size() == 0) {
           return Optional.empty();
       }
		return Optional.of(movementStateHistory.get(movementStateHistory.size() - 1));
	}
}
