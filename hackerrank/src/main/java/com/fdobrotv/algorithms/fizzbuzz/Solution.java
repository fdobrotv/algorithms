package com.fdobrotv.algorithms.fizzbuzz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

class Result {
    /*
     * Complete the 'fizzBuzz' function below.
     *
     * The function accepts INTEGER n as parameter.
     */
    private final static String FIZZ = "Fizz";
    private final static String BUZZ = "Buzz";
    private final static String FIZZBUZZ = "Fizz" + "Buzz";

    public static void fizzBuzz(int n) {
        IntStream.rangeClosed(1, n).forEach((int val) -> {
            String result;
            final boolean isFIZZ = val % 3 == 0;
            final boolean isBUZZ = val % 5 == 0;
            if (isFIZZ && isBUZZ) {
                result = FIZZBUZZ;
            } else if (isFIZZ) {
                result = FIZZ;
            } else if (isBUZZ) {
                result = BUZZ;
            } else {
                result = String.valueOf(val);
            }
            out.println(result);
        });
    }

    private void printResults(List<Integer> result) {
        String preparedResult = result
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
        out.println(preparedResult);
    }

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;
        for (int x = 2; x * x <= num; x++) {
            if (num % x == 0) {
                return false;
            }
        }
        return true;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        Result.fizzBuzz(n);

        bufferedReader.close();
    }
}