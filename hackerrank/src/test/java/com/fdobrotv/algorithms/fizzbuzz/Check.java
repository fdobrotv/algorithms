package com.fdobrotv.algorithms.fizzbuzz;

import com.fdobrotv.algorithms.primenumbers.Solution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;


class Check {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final InputStream standardIn = System.in;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    @Test
    public void checkForPrimeNumbers() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("fizzbuzz/inputArgs.txt");

        assert inputStream != null;
        Scanner scanner = new Scanner(inputStream).useDelimiter("\n");

        ArrayList<String> fromScanner = new ArrayList<>();
        while (scanner.hasNext()) {
            fromScanner.add(scanner.next().trim());
        }

        scanner.close();
        inputStream.close();

        String[] args = fromScanner.toArray(new String[0]);

        //Write to the standard out
        String string = String.join("\n", args);
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        System.setIn(stringStream);
        stringStream.close();

        com.fdobrotv.algorithms.primenumbers.Solution.main(args);

        String expected = normalize("""
                2 
                2 
                2 3 
                2 3 5""");

        String actual = normalize(outputStreamCaptor.toString());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkForPrimeNumbersFromGeneration() throws IOException {
        String[] args = IntStream.rangeClosed(19, 30)
                .boxed().map(Object::toString).toArray(String[]::new);

        //Write to the standard out
        String string = String.join("\n", args);
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        System.setIn(stringStream);
        stringStream.close();

        Solution.main(args);

        String expected = normalize("""
        19 
        19 
        19 
        19 23""");

        String actual = normalize(outputStreamCaptor.toString());

        Assertions.assertEquals(expected, actual);
    }

    private String normalize(String s) {
        return s.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim();
    }
}
