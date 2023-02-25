package multithreading;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class IncrementingTest {

    private static final AtomicInteger globalVariable = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println(shouldExecuteInParallel());
    }

    private static int shouldExecuteInParallel() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Void>> subTasks = Arrays.asList(
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times,
                IncrementingTest::increment100times
        );
        executorService.invokeAll(subTasks);
        return globalVariable.get();
    }

    private static Void increment100times() {

        globalVariable.addAndGet(100);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        for (int item = 0; item < 100; item++) {
//            globalVariable.incrementAndGet();
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        return null;
    }

}