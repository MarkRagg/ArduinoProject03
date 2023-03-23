package RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.util.Pair;

import RoomService.mqtt.MQTTMsg;

public class RoomState {

	private static RoomState instance = null;
	
	private List<MQTTMsg> lightStateHistory;
	private List<Pair<Date, String>> movementStateHistory;
	
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
		//return lightStateHistory.get(lightStateHistory.size());
		return new MQTTMsg(false);
	}

	public synchronized List<Pair<Date, String>> getMovementStateHistory() {
		return movementStateHistory;
	}	
}
