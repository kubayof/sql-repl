package example;

import example.command.GenerateLogCommand;
import example.command.InitDbCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.List;

public class GenerateLogTests {
    @BeforeAll
    public static void init() {
        new InitDbCommand().execute();
    }

    @Test
    public void generateLogTest() throws IOException {
        String logFileName = "customers_log.txt";
        // Pass name of log file to stdin
        ByteArrayInputStream bin = new ByteArrayInputStream(logFileName.getBytes());
        InputStream defaultStdin = System.in;
        System.setIn(bin);
        PrintStream defaultStdout = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {
                //Ignore stdout during test
            }
        }));

        new GenerateLogCommand(new String[]{"customers"}).execute();

        System.setIn(defaultStdin);
        System.setOut(defaultStdout);

        //Compare to expected log from resources
        List<String> expectedLogBytes = Files.readAllLines(Paths.get(Objects.requireNonNull(
                GenerateLogCommand.class.getClassLoader()
                .getResource("expected_db_log.txt"))
                .getPath()));

        List<String> actualBytes = Files.readAllLines(Paths.get(logFileName));

        Assertions.assertEquals(expectedLogBytes, actualBytes);
    }

    @AfterAll
    public static void deleteTestDatabase() {
        TestUtils.deleteTestDatabase();
    }
}
