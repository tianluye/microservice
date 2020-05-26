package com.tly.guice;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompletableFutureTest {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    class OddNumberPlus implements Supplier<Integer> {
        @Override
        public Integer get() {
            try {
                Thread.sleep(3000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("OddNumberPlus返回结果...");
            return 1 + 3 + 5 + 7 + 9;
        }
    }

    class EvenNumberPlus implements Supplier<Integer> {
        @Override
        public Integer get() {
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("EvenNumberPlus返回结果...");
            return 2 + 4 + 6 + 8;
        }
    }

    @Test
    public void testOne() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CompletableFuture<Integer> oddFuture = CompletableFuture.supplyAsync(new OddNumberPlus());
        CompletableFuture<Integer> evenFuture = CompletableFuture.supplyAsync(new EvenNumberPlus());
        CompletableFuture<Integer> future = evenFuture.thenCombineAsync(oddFuture, (odd, even) -> {
            logger.info("全部结束，计算结果...");
            return odd + even;
        });
        int result = future.get();
        stopWatch.stop();
        logger.info(stopWatch.getTotalTimeMillis() + "..." + result);
    }

}
