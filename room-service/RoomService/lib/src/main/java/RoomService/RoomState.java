package RoomService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import RoomService.http.DashboardMessage;
import RoomService.mqtt.MQTTMovement;
import RoomService.mqtt.MQTTMsg;

public class RoomState {

	private static RoomState instance = null;

	private List<MQTTMsg> lightStateHistory;
	private List<MQTTMovement> movementStateHistory;
	private Queue<DashboardMessage> dashboardMessages;

	private RoomState() {
		this.lightStateHistory = new ArrayList<>();
		this.movementStateHistory = new ArrayList<>();
		this.dashboardMessages = new ArrayDeque<>();
	}

	public static RoomState getInstance() {
            if (instance == null) {
                instance = new RoomState();
            }
            return instance;
        }

	public synchronized void addDashboardMessage(final DashboardMessage msg) {
            this.dashboardMessages.add(msg);
        }

        public synchronized Optional<DashboardMessage> getLastDashboardMessage() {
            return Optional.ofNullable(this.dashboardMessages.poll());
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
