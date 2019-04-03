package com.exchange;

import java.nio.charset.Charset;

public class StringEncodeHelper {

    public static final int INT_SIZE  = Integer.SIZE / 8;
    public static final int LONG_SIZE = Long.SIZE / 8;
    public static final int CHAR_SIZE = 1;

    public static String trimRightZero(CharSequence seq) {
        int pos = seq.toString().indexOf(0);
        if (pos == -1)
            return seq.toString();

        return seq.subSequence(0, pos).toString();
    }

    public static byte[] appendZero(String seq, int length, Charset charset) {

        if (charset == null)
            charset = Charset.forName("utf8");

        byte[] bytes = new byte[length];

        byte[] seqByte = seq == null ? new byte[0] : seq.getBytes(charset);

        for (int i = 0; i < length; i++) {
            bytes[i] = (i < seqByte.length) ? seqByte[i] : 0;
        }

        return bytes;
    }

    /**
     * int 的大小端转换
     * 
     * @param c
     * @return
     */
    public static int ctoji(int c) {
        return (c >> 24 & 0xff) | ((c >> 16 & 0xff) << 8) | ((c >> 8 & 0xff) << 16) | ((c & 0xff) << 24);
    }

    /**
     * long 的大小端转换
     * 
     * @param c
     * @return
     */
    public static long ctojl(long c) {
        //return (long) ((ctoji((int) ((c << 32) >> 32))) << 32) | ctoji((int) (c >> 32));
        return (long) ((ctoji((int) ((c << 32) >> 32)))) << 32 | (long) (ctoji((int) (c >> 32)));
    }
}
