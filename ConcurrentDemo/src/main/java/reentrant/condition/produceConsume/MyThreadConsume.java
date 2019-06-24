package reentrant.condition.produceConsume;

/**
 * Created by Administrator
 * 2019-06-21
 */

public class MyThreadConsume implements Runnable{

    private ProduceConsumeService service;

    public MyThreadConsume(ProduceConsumeService service) {
        this.service = service;
    }

    public void run() {
        for (;;) {
            service.consume();
        }
    }
}