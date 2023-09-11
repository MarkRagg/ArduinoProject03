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
        System.out.println("Start monitoring serial port " + portName + " at 9600 boud rate");

        try {
            final CommChannel arduinoChannel = new SerialCommChannel(portName, 9600);
            final Thread sender = new Thread(() -> {
                while (true) {

                    Optional<MQTTMsg> light = RoomState.getInstance().getLastLightState();
                    Optional<MQTTMovement> movement = RoomState.getInstance().getLastMovementState();
                    Optional<DashboardMessage> dashboardMsg = RoomState.getInstance().getLastDashboardMessage();

                    if (light.isPresent() && movement.isPresent()) {
                        sendMessage(new SerialCommunication(light.get().getDay(), movement.get().getMovementState(),
                                false, light.get().getMsgDate(), 0, true), arduinoChannel);
                    } else if (dashboardMsg.isPresent()) {
                        sendMessage(new SerialCommunication(false, false, dashboardMsg.get().isLight(), 0,
                                dashboardMsg.get().getAngle(), false), arduinoChannel);
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            final Thread receiver = new Thread(() -> {
                while (true) {
                    try {
                        if (arduinoChannel.isMsgAvailable()) {
                            String msg;
                            msg = arduinoChannel.receiveMsg();
                            System.out.println("New Arduino Msg available: " + msg);
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            receiver.start();
            sender.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(final SerialCommunication packet, final CommChannel channel) {
        try {
            channel.sendMsg(new Gson().toJson(packet));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
