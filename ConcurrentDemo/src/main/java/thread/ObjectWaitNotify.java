package thread;

/**
 * Created by Administrator
 * 2019-06-21
 */

class MyTask implements Runnable{
    private boolean flag;
    private Object object;
    //定义一个构造方法
    public MyTask(boolean flag, Object object) {
        this.flag=flag;
        this.object=object;
    }
    //定义一个普通方法,其中调用了wait()方法
    public void waitThread() {
        synchronized (this.object) {
            try {
                System.out.println("call wait() before------"+Thread.currentThread().getName());
                //调用wait()方法
                this.object.wait();
                System.out.println("call wait() after------"+Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //定义一个普通方法,其中调用了notify()方法
    public void notifyThread() {
        synchronized (this.object) {
            try {
                System.out.println("call notify() before------"+Thread.currentThread().getName());
                //调用notify()方法
                this.object.notifyAll();
                Thread.sleep(2000);
                System.out.println("call notify() after------"+Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        if(this.flag) {
            this.waitThread();
        }else {
            this.notifyThread();
        }
    }
}

public class ObjectWaitNotify {
    public static void main(String[] args) {
        Object object=new Object();
        //实例化调用wait()的线程
        MyTask wait=new MyTask(true,object);
        Thread waitThread1=new Thread(wait,"wait线程1");
        Thread waitThread2=new Thread(wait,"wait线程2");
        Thread waitThread3=new Thread(wait,"wait线程3");
        //实例化调用notify()的线程
        MyTask notify=new MyTask(false,object);
        Thread notifyThread=new Thread(notify,"notify线程");
        //启动3个等待线程
        waitThread1.start();
        waitThread2.start();
        waitThread3.start();
        //调用一下sleep()方法，使得查看效果更明显
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyThread.start();

        System.out.println("主线程结束");
    }
}
