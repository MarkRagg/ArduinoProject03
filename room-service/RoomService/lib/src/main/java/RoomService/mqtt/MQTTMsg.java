package RoomService.mqtt;

import java.time.LocalDateTime;

public class MQTTMsg {

	private int hoursDate;
	private boolean day;
	
	public MQTTMsg(final boolean day) {
		super();
		this.hoursDate = LocalDateTime.now().getHour();
		this.day = day;
	}

	public int getMsgDate() {
		return hoursDate;
	}

	public boolean getDay() {
		return day;
	}

	public void setMsgDate(int hoursDate) {
		this.hoursDate = hoursDate;
	}

	public void setDay(boolean day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "MQTTMsg [msgDate=" + this.hoursDate + ", day=" + this.day + "]";
	}
}
