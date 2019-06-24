package reentrant.condition.produceConsume;

/**
 * Created by Administrator
 * 2019-06-21
 */

public class ApplicationProduceConsume {
    public static void main(String[] args) {
        ProduceConsumeService service = new ProduceConsumeService();
        Runnable produce = new MyThreadProduce(service);
        Runnable consume = new MyThreadConsume(service);
        new Thread(produce, "生产者  ").start();
        new Thread(consume, "消费者  ").start();
    }
}
