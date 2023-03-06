package RoomService;

import RoomService.http.RoomResource;
import RoomService.mqtt.MQTTAgent;
import io.vertx.core.Vertx;

public class RunService {

	public static void main(String[] args) {
		Vertx vertxHttp = Vertx.vertx();
		RoomResource service = new RoomResource(8080);
		vertxHttp.deployVerticle(service);
		
		Vertx vertxMqtt = Vertx.vertx();
		MQTTAgent agent = new MQTTAgent();
		vertxMqtt.deployVerticle(agent);
	}
	
}
