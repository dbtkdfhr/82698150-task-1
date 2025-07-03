package src;

//GPS 파일을 읽기 위한 클래스 분리
public class GPSPoint {
    public double lat, lon, angle, speed, hdop;
    public GPSPoint(double lat, double lon, double angle, double speed, double hdop) {
        this.lat = lat; this.lon = lon; this.angle = angle; this.speed = speed; this.hdop = hdop;
    }
}
