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

	private List<MQTTMsg> dayHistory;
	private List<MQTTMsg> lightStateHistory;
	private List<MQTTMovement> movementStateHistory;
	private Queue<DashboardMessage> dashboardMessages;

	private RoomState() {
		this.dayHistory = new ArrayList<>();
		this.movementStateHistory = new ArrayList<>();
		this.dashboardMessages = new ArrayDeque<>();
		this.lightStateHistory = new ArrayList<>();
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
		return this.lightStateHistory;
	}

	public synchronized Optional<MQTTMsg> getLastLightState() {
	    if(this.lightStateHistory.size() == 0) {
	        return Optional.empty();
	    }
	    return Optional.of(this.lightStateHistory.get(this.lightStateHistory.size() - 1));
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

	public synchronized Optional<MQTTMsg> getLastDay() {
            if(dayHistory.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(dayHistory.get(dayHistory.size() - 1));
        }

        public synchronized List<MQTTMsg> getDayHistory() {
                return this.dayHistory;
        }
}
