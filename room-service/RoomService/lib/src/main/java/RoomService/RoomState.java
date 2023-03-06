package RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.util.Pair;

public class RoomState {

	private static RoomState instance = null;
	
	private List<Pair<Date, String>> lightStateHistory;
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

	public List<Pair<Date, String>> getLightStateHistory() {
		return lightStateHistory;
	}

	public List<Pair<Date, String>> getMovementStateHistory() {
		return movementStateHistory;
	}	
}
