package locksupport;

/**
 * Created by Administrator
 * 2019-06-24
 */

import java.util.concurrent.locks.LockSupport;

class MyThread4 extends Thread {
    private Object object;

    public MyThread4(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println("before interrupt");
        try {
            // 休眠3s
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread = (Thread) object;
        // 中断线程
        thread.interrupt();
        System.out.println("after interrupt");
    }
}

/**
 * 在主线程调用park阻塞后，在myThread线程中发出了中断信号，此时主线程会继续运行，也就是说明此时interrupt起到的作用与unpark一样。
 */
public class InterruptDemo {
    public static void main(String[] args) {
        MyThread4 myThread = new MyThread4(Thread.currentThread());
        myThread.start();
        System.out.println("before park");
        // 获取许可
        LockSupport.park("ParkAndUnparkDemo");
        System.out.println("after park");
    }
}