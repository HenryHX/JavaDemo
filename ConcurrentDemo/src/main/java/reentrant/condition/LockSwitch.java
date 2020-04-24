package reentrant.condition;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator
 * 2020-03-02
 */

public class LockSwitch {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        double d1 = 0.1;
        double d2 = 0.2;
        double d3 = d1 * d2;

        System.out.println(d3);

        AtomicInteger localID = new AtomicInteger();
        int START_LOCAL_ID = 1000000000;
        localID.set(START_LOCAL_ID);
        int i = localID.addAndGet(5);
        System.out.println(i);
        System.out.println(localID.get());


        ConcurrentHashMap<Integer, String> sessionHashMap = new ConcurrentHashMap<>();
        sessionHashMap.put(1, "1");
        sessionHashMap.put(2, "2");
        sessionHashMap.put(3, "3");
        sessionHashMap.put(4, "4");

        Collection<String> values = Collections.unmodifiableCollection(sessionHashMap.values());

        for (String value : values) {
            sessionHashMap.remove(3);
            System.out.println(value);
        }


        ConcurrentHashMap<Integer, HashSet<Integer>> map2 = new ConcurrentHashMap<>();
        map2.put(1, new HashSet<>());
        map2.get(1).add(1);
        map2.get(1).add(2);
        map2.get(1).add(3);
        map2.get(1).add(4);
        map2.get(1).add(5);

        HashSet<Integer> tmp = new HashSet<>(map2.get(1));

        for (Integer integer : tmp) {
            map2.get(1).remove(4);
            System.out.println(integer);
        }


        do {
            try {
                lock.lock();
                break;
            } finally {
                int j = 9 + 0;
                lock.unlock();
            }
        } while (false);

        System.out.println("hhhahha");
    }
}
