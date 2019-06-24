package thread;

/**
 * Created by Administrator
 * 2018-12-10
 */

public class ThreadLocalTest {
    ThreadLocal<Long> longLoal = new ThreadLocal<Long>();
    ThreadLocal<String> strLocal = new ThreadLocal<String>();

    public Long getLong() {
        return longLoal.get();
    }

    public String getStr() {
        return strLocal.get();
    }

    private void setLocal() {
        longLoal.set(Thread.currentThread().getId());
        strLocal.set(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalTest test = new ThreadLocalTest();

        test.setLocal();

        System.out.println(test.getLong());
        System.out.println(test.getStr());

        Thread thread = new Thread() {
            public void run() {
                test.setLocal();
                System.out.println(test.getLong());
                System.out.println(test.getStr());
            }
        };

        thread.start();
        thread.join();

        System.out.println(test.getLong());
        System.out.println(test.getStr());
    }
}
