package RoomService.mqtt;

import java.time.LocalDateTime;

public class MQTTMsg {

	private String dateTime;
	private boolean day;

	public MQTTMsg(final boolean day) {
		super();
		this.dateTime = LocalDateTime.now().toString();
		this.day = day;
	}

	public String getMsgDate() {
		return this.dateTime;
	}

	public boolean getDay() {
		return day;
	}

	public void setMsgDate(final String dateTime) {
		this.dateTime = dateTime;
	}

	public void setDay(boolean day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "MQTTMsg [msgDate=" + this.dateTime + ", day=" + this.day + "]";
	}
}
