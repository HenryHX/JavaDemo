package commons.io.filemonitor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator
 * 2020-02-11
 */

public class Test {
    public static void main(String[] args) {
        String tradeTopic = "1";
        String adminTopic = "2";
        List<String> topicList = Arrays.asList(tradeTopic, adminTopic);
        System.out.println(topicList);
    }
}
