package com.wordutils;
import com.wordutils.util.WordCounter;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<String, String> dictionary = new HashMap<>();

    static {
        dictionary.put("flower","flower");
        dictionary.put("flor","flower");
        dictionary.put("blume","flower");
    }

    public static void main(String [] args) {
        //word counter with dummy translator
        WordCounter wordCounter = new WordCounter((word) -> dictionary.get(word));
        dictionary.keySet().stream().forEach(word -> {
            System.out.println("Adding " + word);
            wordCounter.add(word);
                }
        );
        dictionary.keySet().stream().forEach(word -> {
                    System.out.println("Counting for " + word + " " +wordCounter.count(word));
                }
        );
    }
}
