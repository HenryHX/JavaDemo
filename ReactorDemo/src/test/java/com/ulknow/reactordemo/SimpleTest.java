package com.ulknow.reactordemo;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleTest {

    private static final String[] WORDS = new String[]{"the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog"};

    @Test
    public void streamTest() {
        AtomicInteger index = new AtomicInteger(1);
        Arrays.stream(WORDS)
                .map(word -> StrUtil.format("{}. {}", index.getAndIncrement(), word))
                .forEach(System.out::println);
    }

    @Test
    public void reactorTest() {
        Flux.fromArray(WORDS)                                                   // ①
                .zipWith(Flux.range(1, Integer.MAX_VALUE),                      // ②
                        (word, index) -> StrUtil.format("{}. {}", index, word)) // ③
                .subscribe(System.out::println);                                // ④
    }
}
