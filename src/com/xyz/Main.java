package com.xyz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static String options;
    private static String file;

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Check ur input!");
            System.exit(0);
        }
        if (args.length == 1) {
            options = "mv";
            file = args[0];
        } else {
            options = args[0];
            file = args[1];
        }

        if (options.contains("X")) {
            if (options.contains("m") && !options.contains("v")) options = "Xm";
            else if (options.contains("v") && !options.contains("m")) options = "Xv";
            else options = "Xmv";
        } else {
            if (options.contains("v") && !options.contains("m")) options = "v";
            else if (options.contains("m") && !options.contains("v")) options = "m";
            else options = "mv";
        }

        switch (options) {
            case "m": {
                countElements(file);
                break;
            }
            case "v": {
                countWordsOfFile(file);
                break;
            }
            case "mv": {
                countElements(file);
                countWordsOfFile(file);
                break;
            }
            case "Xv": {
                countWordsOfFile(file);
                frequencyElements(file);
                break;
            }
            case "Xm": {
                countElements(file);
                frequencyElements(file);
                break;
            }
            case "Xmv": {
                countElements(file);
                countWordsOfFile(file);
                frequencyElements(file);
                break;
            }
        }
    }

    private static void countWordsOfFile(String fileName) throws IOException {
        long wordsCount = Files.lines(Paths.get(getFileName(fileName)))
                .flatMap(str -> Stream.of(str.split(" ")))
                .filter(s -> s.length() > 0).count();
        System.out.println("Amount of words : " + wordsCount);
    }

    private static void frequencyElements(String fileName) throws IOException {
        Map<Character, Long> chars = Files.lines(Paths.get(getFileName(fileName)))
                .flatMapToInt(String::chars)
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

        chars.entrySet().stream()
                .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(System.out::println);
    }

    private static void countElements(String fileName) throws IOException {
        long countElements = Files.lines(Paths.get(getFileName(fileName)))
                .flatMapToInt(String::chars)
                .count();
        System.out.println("Amount of elements : " + countElements);
    }

    private static String getFileName(String name) {
        String src = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        return src + name;
    }
}
