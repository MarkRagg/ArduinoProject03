package RoomService;

import RoomService.http.RoomResource;
import RoomService.mqtt.MQTTAgent;
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
		System.out.println("Start monitoring serial port "+portName+" at 9600 boud rate");
		try {
			CommChannel monitor = new SerialCommChannel(portName, 9600);
			while(true) {
				if(monitor.isMsgAvailable()) {
					String msg = monitor.receiveMsg();
					System.out.print(msg);
					String msgSend = "Ciao Ciccio";
					monitor.sendMsg(msgSend);
					System.out.print("Sending: " + msgSend);
				} else {
					System.out.print("No msg available");
				}

				Thread.sleep(1000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
