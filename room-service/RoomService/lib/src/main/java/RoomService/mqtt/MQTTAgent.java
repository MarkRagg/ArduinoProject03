package RoomService.mqtt;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

import com.google.gson.Gson;

import RoomService.RoomState;
import io.vertx.core.AbstractVerticle;
import io.vertx.mqtt.MqttClient;

/*
 * MQTT Agent
 */
public class MQTTAgent extends AbstractVerticle {
	
	public MQTTAgent() {
	}

	@Override
	public void start() {		
		MqttClient client = MqttClient.create(vertx);
		Gson msgToEsp = new Gson();

		client.connect(1883, "broker.mqtt-dashboard.com", c -> {

			log("connected");
			
			log("subscribing...");
			
			client.publishHandler(s -> {
				  System.out.println("There are new message in topic: " + s.topicName());
				  
				  if(s.topicName().equals(Topics.LIGHT.getName())) {
					  MQTTMsg light = msgToEsp.fromJson(s.payload().toString(), MQTTMsg.class);
					  light.setMsgDate(LocalDateTime.now().getHour());
					  RoomState.getInstance().getLightStateHistory().add(light);
				  } else {
					  MQTTMovement movement = msgToEsp.fromJson(s.payload().toString(), MQTTMovement.class);
					  RoomState.getInstance().getMovementStateHistory().add(movement);
				  }
				  
				  System.out.println("Content(as string) of the message: " + s.payload().toString());
				  System.out.println("QoS: " + s.qosLevel());
				  
				})
				.subscribe(Map.of(Topics.MOVEMENT.getName(), 2, Topics.LIGHT.getName(), 2));
		});
	}

	private void log(String msg) {
		System.out.println("[MQTT AGENT] "+msg);
	}

	private enum Topics {
		MOVEMENT("movement"),
		LIGHT("light");
		
		private String name;
		
		private Topics(final String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
}