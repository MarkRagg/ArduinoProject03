package RoomService.mqtt;

import java.util.Date;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

import RoomService.RoomState;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
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

		client.connect(1883, "broker.mqtt-dashboard.com", c -> {

			log("connected");
			
			log("subscribing...");
			
			client.publishHandler(s -> {
				  System.out.println("There are new message in topic: " + s.topicName());
				  
				  if(s.topicName().equals(Topics.LIGHT.getName())) {
					  RoomState.getInstance().getLightStateHistory().add(new Pair<Date, String>(new Date(), s.payload().toString()));
				  } else {
					  RoomState.getInstance().getMovementStateHistory().add(new Pair<Date, String>(new Date(), s.payload().toString()));
				  }
				  
				  System.out.println("Content(as string) of the message: " + s.payload().toString());
				  System.out.println("QoS: " + s.qosLevel());
				})
				.subscribe(Map.of(Topics.MOVEMENT.getName(), 2, Topics.LIGHT.getName(), 2));
			
			for(int i = 0; i < 100; i++) {
	                        client.publish("movement", 
	                                Buffer.buffer(Double.toString(Math.random() * 100)),
	                                MqttQoS.EXACTLY_ONCE,
	                                false,
	                                false);

	                        client.publish("light", 
	                                Buffer.buffer(Double.toString(Math.random() * 100)),
	                                MqttQoS.EXACTLY_ONCE,
	                                false,
	                                false);
			}
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