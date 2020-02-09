package commons.io.filemapper;

import java.io.Serializable;
import java.util.HashMap;

public class KafkaIndex implements Serializable {
    private HashMap<String, Long> comsumerLastOffset;

    public HashMap<String, Long> getComsumerLastOffset() {
        return comsumerLastOffset;
    }

    public void setComsumerLastOffset(HashMap<String, Long> comsumerLastOffset) {
        this.comsumerLastOffset = comsumerLastOffset;
    }
}
