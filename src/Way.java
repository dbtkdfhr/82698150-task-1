package src;

import java.util.List;

// osm 파일에 Way 파싱을 위한 클래스
public class Way {
    public long id;
    public List<Long> nodeIds;
    public Way(long id, List<Long> nodeIds) {
        this.id = id;
        this.nodeIds = nodeIds;
    }
}
