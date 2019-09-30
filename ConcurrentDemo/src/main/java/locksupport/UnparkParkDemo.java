package locksupport;

/**
 * Created by Administrator
 * 2019-06-24
 */

import java.util.concurrent.locks.LockSupport;

class MyThread3 extends Thread {
    private Object object;

    public MyThread3(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println("before unpark");
        // 释放许可
        LockSupport.unpark((Thread) object);
        System.out.println("after unpark");
    }
}

/**
 * 先调用unpark，再调用park时，仍能够正确实现同步，不会造成由wait/notify调用顺序不当所引起的阻塞。因此park/unpark相比wait/notify更加的灵活。
 *
 * 调用了park函数后，会禁用当前线程，除非许可可用。在以下三种情况之一发生之前，当前线程都将处于休眠状态，即下列情况发生时，当前线程会获取许可，可以继续运行。
 *
 * 　　① 其他某个线程将当前线程作为目标调用 unpark。
 * 　　② 其他某个线程中断当前线程。
 * 　　③ 该调用不合逻辑地（即毫无理由地）返回。
 */
public class UnparkParkDemo {
    public static void main(String[] args) {
        MyThread3 myThread = new MyThread3(Thread.currentThread());
        myThread.start();
        try {
            // 主线程睡眠3s
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("before park");
        // 获取许可
        LockSupport.park("ParkAndUnparkDemo");
        System.out.println("after park");
    }
}