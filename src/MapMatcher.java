package src;

import java.util.*;

public class MapMatcher {
    Map<Long, Node> nodes;
    List<Way> ways;
    List<GPSPoint> points;
    Set<Long> routeWayIds;
    
    List<Long> matchedWayIds = new ArrayList<>();
    List<Boolean> isDeviationList = new ArrayList<>();

    static final double MAX_DIST = 40.0;
    static final double MAX_ANGLE_DIFF = 45.0;

    public MapMatcher(Map<Long, Node> nodes, List<Way> ways, List<GPSPoint> points, List<Long> routeWayIds) {
        this.nodes = nodes;
        this.ways = ways;
        this.points = points;
        this.routeWayIds = new HashSet<>(routeWayIds);
    }

    private double pointToSegmentDist(double plat, double plon, double lat1, double lon1, double lat2, double lon2) {
        double avgLat = (plat + lat1 + lat2) / 3.0;
        double latMeter = 111_000;
        double lonMeter = 111_000 * Math.cos(Math.toRadians(avgLat));

        double x0 = plon * lonMeter, y0 = plat * latMeter;
        double x1 = lon1 * lonMeter, y1 = lat1 * latMeter;
        double x2 = lon2 * lonMeter, y2 = lat2 * latMeter;

        double dx = x2 - x1, dy = y2 - y1;
        double t = ((x0 - x1)*dx + (y0 - y1)*dy) / (dx*dx + dy*dy);

        t = Math.max(0, Math.min(1, t));

        double projX = x1 + t * dx, projY = y1 + t * dy;

        return Math.sqrt((x0 - projX)*(x0 - projX) + (y0 - projY)*(y0 - projY));
    }

    private double normalizeAngle(double deg) {
        deg = deg % 360.0;

        if (deg > 180.0) deg -= 360.0;

        if (deg < -180.0) deg += 360.0;

        return deg;
    }
}
