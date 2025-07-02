package src;

import java.util.List;

public class Way {
    public long id;
    public List<Long> nodeIds;
    public Way(long id, List<Long> nodeIds) {
        this.id = id;
        this.nodeIds = nodeIds;
    }
}
