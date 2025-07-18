package src;

import java.util.*;
import java.io.*;

// 맵 매칭을 위한 클래스
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

    //맵 매칭
    public void match() {
        matchedWayIds.clear();
        isDeviationList.clear();

        for (GPSPoint p : points) {
            double minScore = Double.MAX_VALUE;
            long bestWay = -1;

            for (Way way : ways) {
                for (int i = 1; i < way.nodeIds.size(); i++) {
                    Node n1 = nodes.get(way.nodeIds.get(i - 1));
                    Node n2 = nodes.get(way.nodeIds.get(i));
                    if (n1 == null || n2 == null) continue;

                    double dist = pointToSegmentDist(p.lat, p.lon, n1.lat, n1.lon, n2.lat, n2.lon);
                    double segAngle = Math.atan2(n2.lon - n1.lon, n2.lat - n1.lat) * 180.0 / Math.PI;
                    double angleDiff = Math.abs(normalizeAngle(p.angle - segAngle));

                    //적합한 각도가 아닐 시 1000이라고 하는 큰 값을 주어 score 점수를 올림
                    //이 땜준에 bestWay에 담길 수 없게됨
                    double score = dist + (angleDiff > MAX_ANGLE_DIFF ? 1000.0 : 0.0);

                    if (score < minScore) {
                        minScore = score;
                        bestWay = way.id;
                    }
                }
            }

            matchedWayIds.add(bestWay);

            //가장 적합한 길이 루트에 있을 시 정상으로, 아닐 시 이탈로 간주하여 리스트에 입력
            boolean deviation = !routeWayIds.contains(bestWay);

            isDeviationList.add(deviation);
        }
    }

    //이탈 정상 결과 값을 csv 파일로 출력하는 함수
    public void outputResult(String outFile) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

        bw.write("idx,Latitude,Longitude,Angle,Speed,HDOP,MatchedWayID,Deviation\n");

        for (int i = 0; i < points.size(); i++) {
            GPSPoint p = points.get(i);
            long wid = matchedWayIds.get(i);
            String deviation = isDeviationList.get(i) ? "이탈" : "정상";

            bw.write(i + "," + p.lat + "," + p.lon + "," + p.angle + "," + p.speed + "," + p.hdop + "," + wid + "," + deviation + "\n");
        }

        bw.close();
    }

    // minContinuos 값만큼의 연속 이탈이 발생 시 경로이탈로 간주하는 함수
    public boolean hasContinuousDeviation(int minContinuous) {
        int cur = 0;

        for (boolean b : isDeviationList) {
            if (b) cur++;
            else cur = 0;
            
            if (cur >= minContinuous) return true;
        }

        return false;
    }

    /*
     * 진행 도로를 선분화 하여 gps 값과의 오차를 계산하여 이탈인지 정상인지 판별하는 함수
     * 선분의 발을 내려서 점과 직선 사이의 거리 공식을 이용하여 계산
     */
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

    //각도를 -180~180 값으로 유지
    private double normalizeAngle(double deg) {
        deg = deg % 360.0;

        if (deg > 180.0) deg -= 360.0;

        if (deg < -180.0) deg += 360.0;

        return deg;
    }
}
