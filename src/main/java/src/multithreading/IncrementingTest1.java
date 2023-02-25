package multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class IncrementingTest1 {

    private static final AtomicInteger globalVariable = new AtomicInteger(0);

    private static final int RECORDS_PROCESS_PAGE_SIZE = 12;

    private static final int FIXED_THREAD_POOL_SIZE = 13;

    private static final boolean SHOULD_SLEEP = false;

    private static final int THREAD_POOL_SLOT = (RECORDS_PROCESS_PAGE_SIZE * FIXED_THREAD_POOL_SIZE);

    public static void main(String[] args) throws InterruptedException {
        int totalRecords = 12346789;
        System.out.println(sequentialProcessRecords(totalRecords));
        globalVariable.set(0);
        System.out.println(parallelProcessRecords(totalRecords));
    }

    public static long checkPerformanceResult(Supplier<Integer> sum, int numberOfTimes) {

        long start = System.currentTimeMillis();

        try {
            System.out.println(sum.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        return end - start;
    }

    private static int sequentialProcessRecords(int totalRecords) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("################ STARTING SEQUENTIAL PROCESS ################ ");
        if (totalRecords < 0) {
            System.out.printf("Invalid totalRecords: %s %n", totalRecords);
            return 0;
        }
        int i = 0;
        int sum = 0;
        int deltaRecords = totalRecords % RECORDS_PROCESS_PAGE_SIZE;
        int iteration = totalRecords / RECORDS_PROCESS_PAGE_SIZE + (deltaRecords == 0 ? 0 : 1);
//        int totalSleepTime = 0;
        System.out.printf("iteration: %s deltaRecords: %s %n", iteration, deltaRecords);
        while (i < iteration) {
            int nextBatch = (i < iteration - 1) ? RECORDS_PROCESS_PAGE_SIZE : deltaRecords;
            IncrementingTest1.processNextXItems(String.valueOf(i),nextBatch, i == iteration - 1,false);
            sum += globalVariable.get();
//            totalSleepTime = getTotalSleepTime(i, totalSleepTime);
            i++;
        }
//        System.out.println("Total thread sleep time " + sum);
        long end = System.currentTimeMillis();

        System.out.printf("totalRecords: %s, final processed records: %s, All processed: %s %n", totalRecords, globalVariable.get(), (totalRecords == globalVariable.get()));
        System.out.printf("Total Execution time: %s.%s seconds %n", (end - start) / 1000, (end - start) % 1000);
        System.out.println("################ ENDING SEQUENTIAL PROCESS ################ ");
        return globalVariable.get();
    }

    private static int parallelProcessRecords(int totalRecords) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("################ STARTING PARALLEL PROCESS ################ ");

        if (totalRecords < 0) {
            System.out.printf("Invalid totalRecords: %s %n", totalRecords);
            return 0;
        }

        int deltaRecordsForSlot = totalRecords % THREAD_POOL_SLOT;
        int deltaRecordsForThread = deltaRecordsForSlot % RECORDS_PROCESS_PAGE_SIZE;
        int deltaPoolSize = (deltaRecordsForSlot / RECORDS_PROCESS_PAGE_SIZE) + (deltaRecordsForThread == 0 ? 0 : 1);
        int iteration = totalRecords / THREAD_POOL_SLOT + (deltaRecordsForSlot == 0 ? 0 : 1);
        System.out.printf("iteration: %s deltaRecordsForSlot: %s deltaPoolSize: %s %n", iteration, deltaRecordsForSlot, deltaPoolSize);
        int i = 0;
//        int totalSleepTime = 0;
        while (i < iteration) {
            int currentPoolSize = (i < iteration - 1) ? FIXED_THREAD_POOL_SIZE : deltaPoolSize;
            if(i == iteration - 1) {
                System.out.printf("iteration: %s currentPoolSize: %s %n", i + 1, currentPoolSize);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(currentPoolSize);
            List<Callable<Void>> subTasks = new ArrayList<>();
            for (int j = 0; j < currentPoolSize; j++) {
                int currentBatchSize = (i < iteration - 1) || (j < currentPoolSize - 1) ? RECORDS_PROCESS_PAGE_SIZE : deltaRecordsForThread;
                int finalI = i;
                int finalJ = j;
                subTasks.add(() -> processNextXItems(finalI+"_"+finalJ, currentBatchSize, (finalI == iteration - 1) && (finalJ == currentPoolSize - 1),true));
            }
            executorService.invokeAll(subTasks);
            executorService.shutdown();
            i++;
//            totalSleepTime = getTotalSleepTime(i, totalSleepTime);
        }
        long end = System.currentTimeMillis();
//        System.out.println("totalSleepTime: " + totalSleepTime);
        System.out.printf("totalRecords: %s, final processed records: %s, All processed: %s %n", totalRecords, globalVariable.get(), (totalRecords == globalVariable.get()));
        System.out.printf("Total Execution time: %s.%s seconds %n", (end - start) / 1000, (end - start) % 1000);
        System.out.println("################ ENDING PARALLEL PROCESS ################ ");
        return globalVariable.get();
    }

//    private static int getTotalSleepTime(int i, int totalSleepTime) {
//        if (SHOULD_SLEEP) {
//            int sleepTime = globalVariable.get() / 1000;
//            System.out.println("Loop:" + i + "- Going to sleep for " + sleepTime + " milliseconds");
//            try {
//                Thread.sleep(sleepTime);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            totalSleepTime += sleepTime;
//        }
//        return totalSleepTime;
//    }

    private static Void processNextXItems(String iterationId, int lotSize, boolean showLog, boolean isParallel) {
        if (lotSize < 0) {
            return null;
        }
        globalVariable.addAndGet(lotSize);
        if (showLog) {
            String mode=isParallel?"PARALLEL":"SEQUENTIAL";
            System.out.printf("%s Iteration: '%s', Processed items: %s %n",mode ,iterationId, lotSize);
        }
//        if (SHOULD_SLEEP) {
//            System.out.println("Going to sleep for " + globalVariable.get() / 10 + " milliseconds");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }

        return null;
    }

}