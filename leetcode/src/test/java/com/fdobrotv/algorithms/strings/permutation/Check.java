package com.fdobrotv.algorithms.strings.permutation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;


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
    public void checkForInclusion() throws IOException {
        String[] args = Arrays.asList("ab", "eidbaooo").toArray(new String[0]);

        //Write to the standard out
        String string = String.join("\n", args);
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        System.setIn(stringStream);
        stringStream.close();

        Solution.main(args);

        String expected = normalize("True");

        String actual = normalize(outputStreamCaptor.toString());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkForNonInclusion() throws IOException {
        String[] args = Arrays.asList("ab", "eidboaoo").toArray(new String[0]);

        //Write to the standard out
        String string = String.join("\n", args);
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        System.setIn(stringStream);
        stringStream.close();

        Solution.main(args);

        String expected = normalize("False");

        String actual = normalize(outputStreamCaptor.toString());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkForInclusionLong() throws IOException {
        String[] args = Arrays.asList("prosperity", "properties").toArray(new String[0]);

        //Write to the standard out
        String string = String.join("\n", args);
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        System.setIn(stringStream);
        stringStream.close();

        Solution.main(args);

        String expected = normalize("False");

        String actual = normalize(outputStreamCaptor.toString());

        Assertions.assertEquals(expected, actual);
    }

    private String normalize(String s) {
        return s.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim().toLowerCase();
    }
}
