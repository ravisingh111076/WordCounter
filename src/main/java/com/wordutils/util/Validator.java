package com.wordutils.util;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Validator {

    private static Pattern allowedWordCharacters = Pattern.compile("[a-zA-Z]+$");

    private static Predicate<String> isValid = value -> allowedWordCharacters.matcher(value).find();

    public static Function<String, String> checkWord = (value) -> {
        if(!isValid.test(value)) throw new IllegalArgumentException("only alphabet allowed");
        return value;
    };
}
