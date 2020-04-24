import java.util.Vector;

/**
 * Created by Administrator
 * 2020-04-22
 */

public class MaxHeap {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version")); //判断JDK版本
        System.out.println(System.getProperty("sun.arch.data.model")); //判断是32位还是64位
        System.out.println("最大内存" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");

        Runtime rt = Runtime.getRuntime();
        long totalMem = rt.totalMemory();
        long maxMem = rt.maxMemory();
        long freeMem = rt.freeMemory();
        double megs = 1048576.0;

        System.out.println ("Total Memory: " + totalMem + " (" + (totalMem/megs) + " MiB)");
        System.out.println ("Max Memory:   " + maxMem + " (" + (maxMem/megs) + " MiB)");
        System.out.println ("Free Memory:  " + freeMem + " (" + (freeMem/megs) + " MiB)");
//        Vector v = new Vector();
//        for (int i = 0; i < 10; i++) {
//            byte[] b = new byte[1024 * 1024]; //分配1M
//            v.add(b); //强引用，GC不能释放空间
//            System.out.println((i + 1) + "M 空间被分配");
//        }
    }
}
