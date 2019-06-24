package reentrant.condition.produceConsume;

/**
 * Created by Administrator
 * 2019-06-21
 */

public class MyThreadProduce implements Runnable{

    private ProduceConsumeService service;

    public MyThreadProduce(ProduceConsumeService service) {
        this.service = service;
    }

    public void run() {
        for (;;) {
            service.produce();
        }
    }

}