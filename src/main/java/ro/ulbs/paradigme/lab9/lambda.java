package ro.ulbs.paradigme.lab9;

import java.util.ArrayList;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.*;

public class lambda {
    public static void main(String[] args) {
        //a
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(5);
        numbers.add(10);
        numbers.add(15);
        numbers.add(20);
        numbers.add(25);
        Consumer<Integer> sum = a -> {
            int s = 0;
            for (int i = 0; i < numbers.size(); i++) {
                s += numbers.get(i);
            }
            System.out.println("Suma elementelor este: " + s);

        };
        numbers.forEach(sum);
        //b
        int max = numbers.stream().mapToInt(Integer::intValue).max().orElseThrow();
        int min = numbers.stream().mapToInt(Integer::intValue).min().orElseThrow();
        //c
        List<Integer> filteredList = numbers.stream().filter(n -> n >= 10 && n <= 20).collect(Collectors.toList());
        //d
        List<Double> doubleList = numbers.stream().map(n -> n.doubleValue()).collect(Collectors.toList());
        //e
        boolean contains12 = numbers.stream()
                .anyMatch(n -> n == 12);

        List<Integer> distinctSquares = numbers.stream().map(n -> n * n).distinct().collect(Collectors.toList());

        String prop = "Acesta este un program scris cu java 8 si expresii lambda";
        List<String> words = Arrays.asList(prop.split(" "));
        List<String> longWords = words.stream().filter(word -> word.length() >= 5).collect(Collectors.toList());
        System.out.println("Cuvintele cu lungime >= 5: " + longWords);
        List<String> sortedWords = longWords.stream().sorted().collect(Collectors.toList());
        System.out.println("Cuvintele ordonate: " + sortedWords);
        Optional<String> startsWithP = sortedWords.stream().filter(word -> word.startsWith("p")).findFirst();

    }


}
