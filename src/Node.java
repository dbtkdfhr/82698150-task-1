package src;

// osm 파일에 Node 파싱을 위한 클래스
public class Node {
    public long id;
    public double lat, lon;
    public Node(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
}
