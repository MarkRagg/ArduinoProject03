package RoomService.mqtt;

import java.util.Date;

public class MQTTMsg {

	private Date msgDate;
	private boolean day;
	
	public MQTTMsg(final boolean day) {
		super();
		this.msgDate = new Date();
		this.day = day;
	}

	public Date getMsgDate() {
		return msgDate;
	}

	public boolean getDay() {
		return day;
	}

	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}

	public void setDay(boolean day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "MQTTMsg [msgDate=" + this.msgDate + ", day=" + this.day + "]";
	}
}
