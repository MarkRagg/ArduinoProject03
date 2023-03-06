package RoomService.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RoomResource extends AbstractVerticle {
	
	private int port;
	
	public RoomResource(final int port) {
		this.port = port;
	} 

	@Override
	public void start() {		
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.get("/api/room").handler(this::handleGetResource);		
		vertx
			.createHttpServer()
			.requestHandler(router)
			.listen(port);

		log("Service ready on port: " + port);
	}
	
	private void handleGetResource(RoutingContext routingContext) {
		JsonObject res = new JsonObject();
		res.put("message", "ROOM RESOURCE RES");
		
		routingContext.response()
			.putHeader("content-type", "application/json")
			.end(res.encodePrettily());
	}
	
	private void sendError(int statusCode, HttpServerResponse response) {
		response.setStatusCode(statusCode).end();
	}

	private void log(String msg) {
		System.out.println("[ROOM RESOURCE] "+msg);
	}

}
