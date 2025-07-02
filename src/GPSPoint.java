package src;

public class GPSPoint {
    public double lat, lon, angle, speed, hdop;
    public GPSPoint(double lat, double lon, double angle, double speed, double hdop) {
        this.lat = lat; this.lon = lon; this.angle = angle; this.speed = speed; this.hdop = hdop;
    }
}
