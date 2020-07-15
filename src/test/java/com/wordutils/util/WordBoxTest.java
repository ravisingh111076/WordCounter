package com.wordutils.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

public class WordBoxTest {
    WordBox box;

    @Before
    public void setUp() {
        box = new WordBox();
    }

    @Test
    public void testGetCountGivenWhenValidWordsAreAdded_ExpectingValidCount() {
        box.put("test", "test");
        box.put("test", "testInGerman");
        //add more
        box.put("test1", "test");
        box.put("test1", "test");
        box.put("test1", "testInFrance");

        Assert.assertEquals(2, box.getCount("test"));
        Assert.assertEquals(3, box.getCount("test1"));
    }

    @Test
    public void testGetCountGivenWhenNoWordsAreAdded_ExpectingZeroCount() {
        Assert.assertEquals(0, box.getCount("test"));
        Assert.assertEquals(0, box.getCount("test1"));
    }

    @Test(expected = NullPointerException.class)
    public void testPut_GivenWhenKeyIsNullThenThrowNullPointerException() {
        box.put(null, "test");
    }

    @Test(expected = NullPointerException.class)
    public void testPut_GivenWhenValueIsNullThenThrowNullPointerException() {
        box.put("test", null);
    }

    @Test
    public void testPutAndCountWhenMultipleWorkerThread() throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(2);

        Collection<Future<Integer>> futures =
                new ArrayList<>(10);

        for (int t = 0; t < 10; t++) {
            futures.add(
                    service.submit(
                            () -> {
                                box.put("test", "test");
                                return box.getCount("test");
                            }
                    )
            );
        }
        Set<Integer> wordCounts = new HashSet<>();
        for (Future<Integer> f : futures) {
            wordCounts.add(f.get());
        }
        Assert.assertEquals(10, wordCounts.size());
        service.shutdown();
    }
}
