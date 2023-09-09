package RoomService;

import java.util.Optional;

import com.google.gson.Gson;

import RoomService.http.DashboardMessage;
import RoomService.http.RoomResource;
import RoomService.mqtt.MQTTAgent;
import RoomService.mqtt.MQTTMovement;
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

		final String portName = "COM3";
		Gson msgToArduino = new Gson();
		System.out.println("Start monitoring serial port "+portName+" at 9600 boud rate");
		try {
			final CommChannel monitor = new SerialCommChannel(portName, 9600);
			final Thread sender = new Thread() {
				@Override
				public void run() {
					while(true) {
                        Optional<MQTTMsg> light =  RoomState.getInstance().getLastLightState();
                        Optional<MQTTMovement> movement = RoomState.getInstance().getLastMovementState();
                        Optional<DashboardMessage> dashboardMsg = RoomState.getInstance().getLastDashboardMessage();
					    if(light.isPresent() && movement.isPresent()) {
    						SerialCommunication packet = new SerialCommunication(
    						            light.get().getDay(), movement.get().getMovementState(), false, light.get().getMsgDate(), 0, true
    						        );

    						dashboardMsg.ifPresent(msg -> {
    						    packet.setAutomatic(false);
    						    packet.setLightOn(msg.isLight());
    						    packet.setRollerBlindsAngle(msg.getAngle());
    						});

    						try {
    							//System.out.println(packet);
    							monitor.sendMsg(msgToArduino.toJson(packet));
    							Thread.sleep(1000);
    						} catch (InterruptedException e) {
    							e.printStackTrace();
    						}
					    } else {
					        try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
								System.out.println("New Arduino Msg available: " + msg);
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
