package com.fdobrotv.algorithms.roads_and_libraries;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

@ExtendWith(SystemStubsExtension.class)
class Check {

    private final static Logger LOGGER = Logger.getLogger(Check.class.getName());

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final InputStream standardIn = System.in;
    private final static String resourceDirName = "roads_and_libraries";
    private final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private final String OUTPUT_PATH = System.getenv("OUTPUT_PATH");

    @SystemStub
    private EnvironmentVariables environmentVariables;

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
    public void iterateOverResourcesInputOutput() throws IOException {
        environmentVariables.set("OUTPUT_PATH", "test.txt");

        Assertions.assertEquals(System.getenv("OUTPUT_PATH"), "test.txt");

        File root = new File(resourceDirName);

        final List<String> resourceFiles = getResourceFiles(resourceDirName);

        List<Pair> pairs = resourceFiles.stream().filter(it -> it.contains("input")).map(it -> {
            String numOfTestInput = it.replaceFirst("inputArgs", "").replaceFirst(".txt", "");
            final Optional<String> outputFile = resourceFiles.stream()
                    .filter(t -> t.contains("output"))
                    .filter(file -> {
                        final String expectedFileName = "output" + numOfTestInput + ".txt";
                        return file.contains(expectedFileName);
                    }).findFirst();

            return outputFile.map(file -> new Pair(it, file)).orElse(null);
        }).collect(Collectors.toList());

        for (Pair pair : pairs) {
            String input = pair.input;
            String output = pair.expectedOutput;
            try (InputStream inputStream = classLoader.getResourceAsStream(resourceDirName + "/" + input);
                 Scanner scanner = new Scanner(Objects.requireNonNull(inputStream)).useDelimiter("\n")) {

                ArrayList<String> fromScanner = new ArrayList<>();
                while (scanner.hasNext()) {
                    fromScanner.add(scanner.next().trim());
                }

                String[] args = fromScanner.toArray(new String[0]);

                //Write to the standard out
                String string = String.join("\n", args);
                InputStream stringStream = new ByteArrayInputStream(string.getBytes());
                System.setIn(stringStream);
                stringStream.close();

                Solution.main(args);
            }

            try (InputStream inputStream = classLoader.getResourceAsStream(resourceDirName + "/" + output);
                 Scanner scanner = new Scanner(Objects.requireNonNull(inputStream)).useDelimiter("\n")) {

                String expected = "0";

                List<String> expectedList = new ArrayList<>();
                while (scanner.hasNext()) {
                    expectedList.add(scanner.next().trim());
                }

                final List<String> actualResults = getActualResults();

                LOGGER.info("Going to assert: " + input + " vs " + output);
                assertLinesMatch(expectedList, actualResults);

            }
        }
    }

    private List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    private InputStream getResourceAsStream(String resource) {
        final InputStream in
                = classLoader.getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private static String normalize(String s) {
        return s.replaceAll("\\n|\\r\\n", System.getProperty("line.separator")).trim();
    }

    private class Pair {
        String input;
        String expectedOutput;

        public Pair(String input, String expectedOutput) {
            this.input = input;
            this.expectedOutput = expectedOutput;
        }
    }

    private List<String> getActualResults() {
        List<String> actualList = new ArrayList<>();


        final String outputPath = System.getenv("OUTPUT_PATH");
        try (final FileReader fileReader = new FileReader(outputPath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            actualList = bufferedReader.lines().peek(String::trim).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actualList;
    }
}
