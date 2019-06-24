package reentrant.condition;

/**
 * Created by Administrator
 * 2019-06-21
 */

public class MyServiceThread2 implements Runnable{

    private MyService service;

    public MyServiceThread2(MyService service) {
        this.service = service;
    }

    public void run() {
        service.awaitB();
    }
}