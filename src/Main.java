package src;

import java.util.*;

public class Main {
    static final List<Long> ROUTE_WAY_IDS = Arrays.asList(
        521766182L, 990628459L, 472042763L, 218864485L, 520307304L
    );

    public static void main(String[] args) throws Exception {
        OSMParser parser = new OSMParser("data/roads.osm");
        parser.parse();
    }
}
