package reentrant.condition;

/**
 * Created by Administrator
 * 2019-06-21
 */

public class MyServiceThread1 implements Runnable{

    private MyService service;

    public MyServiceThread1(MyService service) {
        this.service = service;
    }

    public void run() {
        service.awaitA();
    }
}
