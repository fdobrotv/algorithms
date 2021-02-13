package com.fdobrotv.algorithms.primenumbers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


class Check {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void checkForPrimeNumbers() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("inputArgs.txt");

        assert inputStream != null;
        Scanner scanner = new Scanner(inputStream).useDelimiter("\n");

        ArrayList<String> fromScanner = new ArrayList<>();
        while (scanner.hasNext()) {
            fromScanner.add(scanner.next());
        }
        scanner.close();
        String[] args = fromScanner.toArray(new String[0]);

        Solution.main(args);

        String expected = normalize("""
                2 
                2 
                2 3 
                2 3 5""");

        String actual = normalize(outputStreamCaptor.toString());

        Assertions.assertEquals(expected, actual);
    }

    private String normalize(String s) {
        return s.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim();
    }
}
