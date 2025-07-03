package src;

import java.util.*;

public class Main {
    static final List<Long> ROUTE_WAY_IDS = Arrays.asList(
        521766182L, 990628459L, 472042763L, 218864485L, 520307304L
    );

    public static void main(String[] args) throws Exception {
        //roads.osm 파일을 파싱하여 Node, Way 등 값 분리하여 저장
        OSMParser parser = new OSMParser("data/roads.osm");
        parser.parse();

        //csv 파일 목록 이름들
        String[] gpsFiles = {
            "gps_left_turn.csv", "gps_left02_turn.csv", "gps_reverse_direction.csv",
            "gps_right_turn_01.csv", "gps_right02_turn.csv", "gps_straight01.csv",
            "gps_straight02.csv", "gps_straight03.csv", "gps_straight04.csv", "gps_multipath.csv"
        };

        for (String file : gpsFiles) {
            System.out.println("\n==== " + file + " ====");

            //GPS 파일을 읽고 파싱하여 GPSPoint의 리스트로 저장
            GPSLog gpsLog = new GPSLog("data/" + file);
            gpsLog.parse();

            //맵 매칭 시작
            MapMatcher matcher = new MapMatcher(parser.nodes, parser.ways, gpsLog.points, ROUTE_WAY_IDS);
            matcher.match();

            //결과를 output 폴더에 각각의 csv 파일로 저장
            String resultFile = "output/" + file.replace(".csv", "_result.csv");
            matcher.outputResult(resultFile);

            //최소 5번 이상의 이탈이 연속으로 있을 시 경로이탈로 간주하여 출력
            if (matcher.hasContinuousDeviation(5)) { 
                System.out.println("[경로이탈 있음]");
            } else {
                System.out.println("[경로이탈 없음]");
            }
        }
    }
}
