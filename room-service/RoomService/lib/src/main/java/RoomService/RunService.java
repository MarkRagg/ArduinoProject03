package RoomService;

import com.google.gson.Gson;
import RoomService.http.RoomResource;
import RoomService.mqtt.MQTTAgent;
import RoomService.mqtt.MQTTMsg;
import RoomService.serial.CommChannel;
import RoomService.serial.SerialCommChannel;
import RoomService.serial.SerialCommunication;
import io.vertx.core.Vertx;

public class RunService {

	public static void main(String[] args) {
		Vertx vertxHttp = Vertx.vertx();
		RoomResource service = new RoomResource(3030);
		vertxHttp.deployVerticle(service);
		
		Vertx vertxMqtt = Vertx.vertx();
		MQTTAgent agent = new MQTTAgent();
		vertxMqtt.deployVerticle(agent);
		
		final String portName = "COM4";
		Gson msgToArduino = new Gson();
		System.out.println("Start monitoring serial port "+portName+" at 9600 boud rate");
		try {
			final CommChannel monitor = new SerialCommChannel(portName, 9600);
			final Thread sender = new Thread() {
				@Override
				public void run() {
					while(true) {
						System.out.println(RoomState.getInstance().getLightStateHistory().toString());
						MQTTMsg light =  RoomState.getInstance().getLastLightState();
						SerialCommunication packet = new SerialCommunication(light.getDay(), true, false, light.getMsgDate(), 0, true);
						try {
							monitor.sendMsg(msgToArduino.toJson(packet));
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			final Thread receiver = new Thread() {
				@Override 
				public void run() {
					while(true) {
						try {
							if(monitor.isMsgAvailable()) {
								String msg;
								msg = monitor.receiveMsg();
								System.out.println(msg);
							}
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}
			};
			receiver.start();
			sender.start();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
}
