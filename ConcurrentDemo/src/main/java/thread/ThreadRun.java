package thread;

/**
 * Created by Administrator
 * 2020-02-24
 */

public class ThreadRun {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.setDaemon(true);
        t.start();
        System.out.println("end   11111111111111111111");
    }
}
