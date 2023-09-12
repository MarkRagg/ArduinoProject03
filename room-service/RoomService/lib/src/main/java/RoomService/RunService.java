package RoomService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

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

	private static boolean automatic = true;
	private static boolean isBtActive = false;
	private static SerialCommunication lastAutomaticMessage;

    public static void main(String[] args) {
        Vertx vertxHttp = Vertx.vertx();
        RoomResource service = new RoomResource(3030);
        vertxHttp.deployVerticle(service);

        Vertx vertxMqtt = Vertx.vertx();
        MQTTAgent agent = new MQTTAgent();
        vertxMqtt.deployVerticle(agent);

        final String portName = "COM3";
        System.out.println("Start monitoring serial port " + portName + " at 9600 boud rate");

        Timer timer = new Timer();

        try {
            final CommChannel arduinoChannel = new SerialCommChannel(portName, 9600);
            final Thread sender = new Thread(() -> {
                while (true) {

                    Optional<MQTTMsg> lastDay = RoomState.getInstance().getLastDay();
                    Optional<MQTTMovement> movement = RoomState.getInstance().getLastMovementState();
                    Optional<DashboardMessage> dashboardMsg = RoomState.getInstance().getLastDashboardMessage();

                    if (dashboardMsg.isPresent()) {
                    	setAutomatic(false);
                    	lastAutomaticMessage = new SerialCommunication(false, false,
                    			dashboardMsg.get().isLight(), dashboardMsg.get().getAngle(), false, false);

                    	startTimer(timer);
                    	sendMessage(lastAutomaticMessage, arduinoChannel);
                    } else if(!getAutomatic()) {
                    	sendMessage(lastAutomaticMessage, arduinoChannel);
                    } else if (lastDay.isPresent() && movement.isPresent()) {
                    	sendMessage(new SerialCommunication(lastDay.get().getDay(), movement.get().getMovementState(),
                    			false, 0, true, false), arduinoChannel);
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
                            var gson = new Gson().fromJson(msg, SerialCommunication.class);

                            System.out.println("New Arduino Msg available: " + msg);
                            var lightOn = new MQTTMsg(gson.isLightOn());
                            if(!msg.contains("null")) setAutomatic(gson.isAutomatic());
                            if(gson.isBtCommand()) {
                                setAutomatic(false);
                                lastAutomaticMessage = gson;
                            }
                            if(!isBtActive && gson.isBtCommand()) {
                            	System.out.println(gson.isLightOn());
                            	startTimer(timer);
                            	isBtActive = true;
                            }
                            lightOn.setMsgDate(LocalDateTime.now().toString());
                            RoomState.getInstance().getLightStateHistory().add(lightOn);
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

    private static void startTimer(Timer timer) {
    	timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
			    setAutomatic(true);
		    }
		}, 5000);
	}

	private static void sendMessage(final SerialCommunication packet, final CommChannel channel) {
        try {
            channel.sendMsg(new Gson().toJson(packet));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean getAutomatic() {
        return automatic;
    }

    public static synchronized void setAutomatic(boolean value) {
    	automatic = value;
    }
}
