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
		
		while(true) {
			System.out.println(RoomState.getInstance().getLightStateHistory().toString());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*
		final String portName = "COM3";
		Gson msgToArduino = new Gson();
		ArduinoMsg msgJson = new ArduinoMsg(2, 3);
		System.out.println("Start monitoring serial port "+portName+" at 9600 boud rate");
		try {
			final CommChannel monitor = new SerialCommChannel(portName, 9600);
			final Thread sender = new Thread() {
				@Override
				public void run() {
					while(true) {
						try {
							monitor.sendMsg(msgToArduino.toJson(msgJson));
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
								ArduinoMsg receivedJson = msgToArduino.fromJson(msg, ArduinoMsg.class);
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
		}*/
	}
}
