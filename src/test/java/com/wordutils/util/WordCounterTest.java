package com.wordutils.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class WordCounterTest {

    private static final Map<String, String> dummyDictionary;

    static {
        dummyDictionary = new HashMap<>();
        dummyDictionary.put("flower","flower");
        dummyDictionary.put("flor","flower");
        dummyDictionary.put("blume","flower");
        dummyDictionary.put("testInGerman","test");
        dummyDictionary.put("testInFrance","test");
        dummyDictionary.put("testInHindi","test");
        dummyDictionary.put("rav","rav");
        dummyDictionary.put("aada","aada");
        dummyDictionary.put("test","test");
        dummyDictionary.put("sasAA","sasAA");
    }

    WordCounter wordCounter;

    @Before
    public void init() {
       wordCounter = new WordCounter( (word) -> dummyDictionary.get(word));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdd_GivenAlphaNumericWord_ExpectingException() {
        wordCounter.add("rav1123");
        wordCounter.add("1123");
        wordCounter.add("1123AAA");
    }

    @Test
    public void testAdd_GivenAlphabets_ExpectingNoException() {
        wordCounter.add("rav");
        wordCounter.add("aada");
        wordCounter.add("sasAA");
    }

    @Test
    public void testCount_GivenWordInMultipleLanguageWereAdded_ThenExpectingValidCount() {
        wordCounter.add("flower");
        wordCounter.add("flor");
        wordCounter.add("blume");
        Assert.assertEquals(3, wordCounter.count("flor"));
        Assert.assertEquals(3, wordCounter.count("blume"));
        Assert.assertEquals(3, wordCounter.count("flor"));

        wordCounter.add("testInGerman");
        wordCounter.add("testInFrance");
        Assert.assertEquals(2, wordCounter.count("testInGerman"));
        Assert.assertEquals(2, wordCounter.count("testInFrance"));
    }

    @Test
    public void testCount_GivenNonEnglishWordWereAdded_And_EnglishWordWasUsedForCount_ThenExpectACount() {
        //just added value in other language
        wordCounter.add("testInGerman");
        Assert.assertEquals(1, wordCounter.count("test"));
    }

    @Test
    public void testCount_WhenGivenAWordAddedMultipleTimes_ThenExpectingValidCount() {
        IntStream.range(0, 10).forEach(i -> wordCounter.add("flower"));
        Assert.assertEquals(10, wordCounter.count("flower"));
        Assert.assertEquals(10, wordCounter.count("flor"));
    }
}
