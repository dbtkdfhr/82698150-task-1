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

    private double normalizeAngle(double deg) {
        deg = deg % 360.0;

        if (deg > 180.0) deg -= 360.0;

        if (deg < -180.0) deg += 360.0;

        return deg;
    }
}
