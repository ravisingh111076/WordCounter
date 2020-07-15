package com.wordutils.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class WordBox {

    private Map<String, List<String>> words = new ConcurrentHashMap<>();
    final ReentrantLock lock = new ReentrantLock();

    public void put(String key, String value) {
        Objects.requireNonNull(value);
        lookUpAndUpdate(key, value);
    }

    public int getCount(String word) {
        return words.containsKey(word) ? words.get(word).size() : 0;
    }

    private void lookUpAndUpdate(String key, String value) {
        lock.lock();
        try {
            List<String> wordList = words.getOrDefault(key, new ArrayList<>());
            wordList.add(value);
            words.put(key, wordList);
        } finally {
            lock.unlock();
        }
    }
}
