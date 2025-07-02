package src;

import java.util.*;

public class MapMatcher {
    Map<Long, Node> nodes;
    List<Way> ways;
    List<GPSPoint> points;
    Set<Long> routeWayIds;

    public MapMatcher(Map<Long, Node> nodes, List<Way> ways, List<GPSPoint> points, List<Long> routeWayIds) {
        this.nodes = nodes;
        this.ways = ways;
        this.points = points;
        this.routeWayIds = new HashSet<>(routeWayIds);
    }
}
