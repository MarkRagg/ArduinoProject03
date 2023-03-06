package RoomService;

import RoomService.http.RoomResource;
import io.vertx.core.Vertx;

public class RunService {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		RoomResource service = new RoomResource(8080);
		vertx.deployVerticle(service);
	}
	
}
