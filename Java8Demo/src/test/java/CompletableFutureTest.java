import org.junit.Test;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureTest {
    @Test
    public void thenApply() throws InterruptedException, ExecutionException {
        CompletableFuture<String> stage = CompletableFuture.supplyAsync(() -> "hello");

        while (!stage.isDone()) {
            TimeUnit.SECONDS.sleep(1);
        }

        String result = stage.thenApply(s -> s + " world").join();
        System.out.println(stage.get());
        System.out.println(result);
    }

    @Test
    public void thenCombine() {
        LocalTime now = LocalTime.now();
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("1/current thread : " + Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("2/current thread : " + Thread.currentThread().getName());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        LocalTime now2 = LocalTime.now();
        System.out.println(result);
        System.out.println("time = " + (now2.getSecond() - now.getSecond()));
    }

    @Test
    public void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("1/current thread : " + Thread.currentThread().getName());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Tom";
        }).applyToEitherAsync(CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("2/current thread : " + Thread.currentThread().getName());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "John";
        }), s -> {
            System.out.println("3/current thread : " + Thread.currentThread().getName());
            return "hello " + s;
        }).join();
        System.out.println(result);
    }

    @Test
    public void thenAccept() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s + " world"));
        while (!voidCompletableFuture.isDone()) {
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(voidCompletableFuture.get());
    }

    @Test
    public void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));

        while (true) {
        } //等待打印出结果
    }

    @Test
    public void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello john";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello tom";
        }), System.out::println);

        while (true) {
        } //等待打印出结果
    }

    @Test
    public void thenRun() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));
        while (true) {
        }
    }

    @Test
    public void runAfterBoth() {
        //不关心这两个CompletionStage的结果，只关心这两个CompletionStage正常执行完毕，之后在进行操作（Runnable）。
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        while (true) {
        }
    }

    @Test
    public void runAfterEither() {
        //两个CompletionStage，任何一个正常完成了都会执行下一步的操作（Runnable）。
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        while (true) {
        }
    }

    @Test
    public void thenCompose() {
        LocalTime now = LocalTime.now();

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("1/current thread : " + Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCompose(s -> {
            CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println("2/current thread : " + Thread.currentThread().getName());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return s + " world";
            });
            System.out.println("stringCompletableFuture1 = " + stringCompletableFuture1);
            return stringCompletableFuture1;
        });

        System.out.println("stringCompletableFuture = " + stringCompletableFuture);
        String result = stringCompletableFuture.join();
        System.out.println(result);

        LocalTime now2 = LocalTime.now();
        System.out.println(result);
        System.out.println("time = " + (now2.getSecond() - now.getSecond()));
    }

    @Test
    public void thenApplyEqualCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<CompletableFuture<String>> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenApply(s -> {
            CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return s + " world";
            });
            System.out.println("stringCompletableFuture = " + stringCompletableFuture);
            return stringCompletableFuture;
        });

        System.out.println("stringCompletableFuture1 = " + stringCompletableFuture1);
        System.out.println("stringCompletableFuture1.content = " + stringCompletableFuture1.get());

        CompletableFuture<String> result1 = stringCompletableFuture1.join();

        String result = stringCompletableFuture1.join().join();
        System.out.println(result1);
    }

    @Test
    public void thenCombine2() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (1 == 1) {
                //throw new RuntimeException("测试一下异常情况");
            }

            return "hello ";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("return world...");  //会执行
            return "world";
        }), (s1, s2) -> {
            String s = s1 + " " + s2;   //并不会执行
            System.out.println("combine result :" + s); //并不会执行
            return s;
        }).whenComplete((s, t) -> {
            System.out.println("current result is :" + s);
            if (t != null) {
                System.out.println("阶段执行过程中存在异常：");
                //t.printStackTrace();
            }
        }).join();

        System.out.println("final result:" + result); //并不会执行
    }

    @Test
    public void handle() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "Tom";
        }).handle((s, t) -> {
            if (t != null) { //出现异常了
                return "John";
            }
            return s; //这里也可以对正常结果进行转换
        }).join();
        System.out.println(result);
    }

    @Test
    public void exceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            e.printStackTrace(); //e肯定不会null
            return "hello world"; //补偿返回
        }).join();
        System.out.println(result); //打印hello world
    }

    private static Random rnd = new Random();

    static int delayRandom(int min, int max) {
        int milli = max > min ? rnd.nextInt(max - min) : 0;
        try {
            Thread.sleep(min + milli);
        } catch (InterruptedException e) {
        }
        return milli;
    }

    private static ExecutorService executor =
            Executors.newFixedThreadPool(10);

    @Test
    public void testAllof() {
        CompletableFuture<String> taskA = CompletableFuture.supplyAsync(() -> {
            delayRandom(100, 1000);
            return "helloA";
        }, executor);

        CompletableFuture<Void> taskB = CompletableFuture.runAsync(() -> {
            delayRandom(2000, 3000);
        }, executor);

        CompletableFuture<Void> taskC = CompletableFuture.runAsync(() -> {
            delayRandom(30, 100);
            throw new RuntimeException("task C exception");
        }, executor);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(taskA, taskB, taskC).whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println(ex.getMessage());
            }
            if (!taskA.isCompletedExceptionally()) {
                System.out.println("task A " + taskA.join());
            }
        });

        while (!voidCompletableFuture.isDone()) {
            delayRandom(100, 1000);
        }
    }
}