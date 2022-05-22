package com.fdobrotv.algorithms.repeated_string;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'repeatedString' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. LONG_INTEGER n
     */

    public static long repeatedString(String s, long n) {
        long countOfAInS = getCountOfChar(s, 'a');
        long finalPartOfStringSize = n % s.length();
        long countOfFullStrings = n / s.length();

        long remainderCountOfAInS = 0;
        if (finalPartOfStringSize != 0) {
            String remainderOfS = s.substring(0, (int)finalPartOfStringSize);
            remainderCountOfAInS = getCountOfChar(remainderOfS, 'a');
        }

        return countOfAInS * countOfFullStrings + remainderCountOfAInS;
    }

    private static long getCountOfChar(String s, char c) {
        long result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)==c)
                result++;
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        long n = Long.parseLong(bufferedReader.readLine().trim());

        long result = Result.repeatedString(s, n);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
