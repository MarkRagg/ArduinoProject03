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
		res.put("light", RoomState.getInstance().getLightStateHistory().stream()
		        .map(msg -> new ResponseData(msg.getMsgDate(), msg.getDay())));
		res.put("movement", RoomState.getInstance().getMovementStateHistory().stream()
		        .map(msg -> new ResponseData(msg.getDateTime(), msg.getMovementState())));

		routingContext.response()
			.putHeader("content-type", "application/json")
			.end(res.encodePrettily());
	}

	private void handlePostResource(final RoutingContext routingContext) {
	    routingContext.request().bodyHandler(bodyHandler -> {
	        var body = bodyHandler.toJsonObject();
	        RoomState.getInstance().addDashboardMessage(new DashboardMessage(Boolean.parseBoolean(body.getString("light")),
	                    Integer.parseInt(body.getString("movement"))));
	    });
	    routingContext.response()
    	        .putHeader("content-type", "application/json")
                .end(new JsonObject().encodePrettily());
	}

	private void log(final String msg) {
		System.out.println("[ROOM RESOURCE] "+msg);
	}

	private final class ResponseData {
	    @SuppressWarnings("unused")
            private final String date;
    	    @SuppressWarnings("unused")
            private final boolean value;

	    public ResponseData(final String date, final boolean value) {
	        this.date = date;
	        this.value = value;
	    }
	}

}
