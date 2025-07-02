package src;

import java.util.*;

public class Main {
    static final List<Long> ROUTE_WAY_IDS = Arrays.asList(
        521766182L, 990628459L, 472042763L, 218864485L, 520307304L
    );

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser("data/roads.osm");
        parser.parse();

        String[] gpsFiles = {
            "gps_left_turn.csv", "gps_left02_turn.csv", "gps_reverse_direction.csv",
            "gps_right_turn_01.csv", "gps_right02_turn.csv", "gps_straight01.csv",
            "gps_straight02.csv", "gps_straight03.csv", "gps_straight04.csv", "gps_multipath.csv"
        };

        for (String file : gpsFiles) {
            System.out.println("\n==== " + file + " ====");
            GPSLog gpsLog = new GPSLog("data/" + file);
            gpsLog.parse();

            MapMatcher matcher = new MapMatcher(parser.nodes, parser.ways, gpsLog.points, ROUTE_WAY_IDS);
            matcher.match();
        }
    }
}
