package RoomService.http;

public class DashboardMessage {

    private final boolean light;
    private final int angle;

    public DashboardMessage(final boolean light, final int angle) {
        this.light = light;
        this.angle = angle;
    }

    public boolean isLight() {
        return light;
    }

    public int getAngle() {
        return angle;
    }

}
