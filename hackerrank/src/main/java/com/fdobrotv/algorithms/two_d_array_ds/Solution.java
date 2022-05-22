package com.fdobrotv.algorithms.two_d_array_ds;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.Stream;

public class Solution {

    // Complete the hourglassSum function below.
    static int hourglassSum(int[][] arr) {
        int result = Integer.MIN_VALUE;
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[i].length; j++) {
                if (i+2 >= arr.length || j+2 >= arr[j].length)
                    continue;
                int[][] hourglass = { { arr[i][j], arr[i][j+1], arr[i+2][j]     },
                        {           arr[i+1][j+1]                 },
                        { arr[i][j+2],arr[i+2][j+1],arr[i+2][j+2] } };
                int sumOfHourGlass = getSumOfHourGlass(hourglass);
                result = Math.max(result, sumOfHourGlass);
            }
        }
        return result;
    }

    private static int getSumOfHourGlass(int[][] arr) {
        int result = 0;
        final Iterator<int[]> lineIteratorY = Arrays.stream(arr).iterator();
        while (lineIteratorY.hasNext()) {
            final int[] line = lineIteratorY.next();
            final PrimitiveIterator.OfInt rowIteratorX = Arrays.stream(line).iterator();
            while (rowIteratorX.hasNext()) {
                final int value = rowIteratorX.nextInt();
                result += value;
            }
        }
        return result;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int[][] arr = new int[6][6];

        for (int i = 0; i < 6; i++) {
            String[] arrRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 6; j++) {
                int arrItem = Integer.parseInt(arrRowItems[j]);
                arr[i][j] = arrItem;
            }
        }

        int result = hourglassSum(arr);

        System.out.println(result);
        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
