package commons.io.filemapper;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

import static com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm.INT_SIZE;

public class LargeMappedFiles {

    private static int LENGTH = 0x0000FFF;
    public static final int INT_SIZE = Integer.BYTES;

    public static void main(String[] args) throws IOException {
        MappedByteBuffer out = new RandomAccessFile("test.dat", "rw")
                .getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);



        KafkaIndex kafkaIndex = new KafkaIndex();
        HashMap<String , Long> comsumerLastOffset = new HashMap<>();
        comsumerLastOffset.put("OMS", 156l);
        kafkaIndex.setComsumerLastOffset(comsumerLastOffset);
        byte[] bytes = JSON.toJSONString(kafkaIndex).getBytes();
        out.clear();
        out.putInt(bytes.length);
        out.put(bytes);



        System.out.printf("out");

        out.clear();

        byte[] dst = new byte[LENGTH];
        out.get(dst);

        out.clear();

        int msgSize = out.getInt();

        String tmp = new String(dst, INT_SIZE, msgSize);
        KafkaIndex kafkaIndex1 = JSON.parseObject(tmp, KafkaIndex.class);

    }
}
