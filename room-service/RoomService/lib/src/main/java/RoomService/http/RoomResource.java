package RoomService.http;

import RoomService.RoomState;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;

public class RoomResource extends AbstractVerticle {

	private int port;

	public RoomResource(final int port) {
		this.port = port;
	}

	@Override
	public void start() {
		Router router = Router.router(vertx);
		router.route().handler(CorsHandler.create("http://localhost"));
		router.get("/api/room").handler(this::handleGetResource);
		router.post("/api/room").handler(this::handlePostResource);
		vertx
			.createHttpServer()
			.requestHandler(router)
			.listen(port);

		log("Service ready on port: " + port);
	}

	private void handleGetResource(final RoutingContext routingContext) {
		JsonObject res = new JsonObject();
		res.put("light", RoomState.getInstance().getLightStateHistory());
		res.put("movement", RoomState.getInstance().getMovementStateHistory());

		routingContext.response()
			.putHeader("content-type", "application/json")
			.end(res.encodePrettily());
	}

	private void handlePostResource(final RoutingContext routingContext) {
	    routingContext.request().bodyHandler(bodyHandler -> {
	        var body = bodyHandler.toJsonObject();
	        RoomState.getInstance().addDashboardMessage(new DashboardMessage(Boolean.getBoolean(body.getString("light")),
	                    Integer.parseInt(body.getString("movement"))));
	    });
	}

	private void log(final String msg) {
		System.out.println("[ROOM RESOURCE] "+msg);
	}

}
