package locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by Administrator
 * 2019-06-24
 */

class MyThread1 extends Thread {
    private Object object;

    public MyThread1(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println("before unpark");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取blocker
        System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));
        // 释放许可
        LockSupport.unpark((Thread) object);
        // 休眠500ms，保证先执行park中的setBlocker(t, null);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 再次获取blocker
        System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));

        System.out.println("after unpark");
    }
}

/**
 * LockSupport就是通过控制变量_counter来对线程阻塞唤醒进行控制的。原理有点类似于信号量机制。
 *
 *     当调用park()方法时，会将_counter置为0，同时判断前值，等于1说明前面被unpark过,则直接退出，否则将使该线程阻塞。
 *     当调用unpark()方法时，会将_counter置为1，同时判断前值，小于1会进行线程唤醒，否则直接退出。
 *     形象的理解，线程阻塞需要消耗凭证(permit)，这个凭证最多只有1个。当调用park方法时，如果有凭证，则会直接消耗掉这个凭证然后正常退出；但是如果没有凭证，就必须阻塞等待凭证可用；而unpark则相反，它会增加一个凭证，但凭证最多只能有1个。
 *     为什么可以先唤醒线程后阻塞线程？
 *     因为unpark获得了一个凭证,之后调用park因为有凭证消费，故不会阻塞。
 *     为什么唤醒两次后阻塞两次会阻塞线程。
 *     因为凭证的数量最多为1，连续调用两次unpark和调用一次unpark效果一样，只会增加一个凭证；而调用两次park却需要消费两个凭证。
 */
public class ParkUnparkDemo {
    public static void main(String[] args) {
        //阻塞主线程，传入子线程以便unpark和获取blocker
        MyThread1 myThread = new MyThread1(Thread.currentThread());
        myThread.start();
        System.out.println("before park");
        // 获取许可
        LockSupport.park("ParkAndUnparkDemo");
        System.out.println("after park");
    }
}