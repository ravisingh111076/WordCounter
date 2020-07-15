package com.wordutils.util;

public class WordCounter {

    private Translator translator;
    private WordBox wordBox;

    public WordCounter(Translator translator) {
        this.translator = translator;
        this.wordBox = new WordBox();
    }

    public void add(String word) {
       //will throw exception for invalid word
       Validator.checkWord.apply(word);
       wordBox.put(translator.translate(word).toLowerCase(), word);
    }

    public int count(String word) {
        return wordBox.getCount(translator.translate(word).toLowerCase());
    }
}