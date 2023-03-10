package RoomService;

import com.google.gson.Gson;

import RoomService.http.RoomResource;
import RoomService.mqtt.MQTTAgent;
import RoomService.serial.ArduinoMsg;
import RoomService.serial.CommChannel;
import RoomService.serial.SerialCommChannel;
import io.vertx.core.Vertx;

public class RunService {

	public static void main(String[] args) {
		Vertx vertxHttp = Vertx.vertx();
		RoomResource service = new RoomResource(3030);
		vertxHttp.deployVerticle(service);
		
		Vertx vertxMqtt = Vertx.vertx();
		MQTTAgent agent = new MQTTAgent();
		vertxMqtt.deployVerticle(agent);
		
		final String portName = "COM3";
		Gson msgToArduino = new Gson();
		ArduinoMsg msgJson = new ArduinoMsg(2, 3);
		System.out.println("Start monitoring serial port "+portName+" at 9600 boud rate");
		try {
			CommChannel monitor = new SerialCommChannel(portName, 9600);
			while(true) {
				//monitor.sendMsg(msgToArduino.toJson(msgJson));
				if(monitor.isMsgAvailable()) {
					String msg = monitor.receiveMsg();
					System.out.println(msg);
					ArduinoMsg receivedJson = msgToArduino.fromJson(msg, ArduinoMsg.class);
					System.out.println(receivedJson.toString());
				} else {
					System.out.println("No msg available");
				}

				Thread.sleep(2000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
