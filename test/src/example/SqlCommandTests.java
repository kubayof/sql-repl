package example;

import example.command.InitDbCommand;
import example.command.SqlCommand;
import example.logging.DbLoggerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class SqlCommandTests {
    @BeforeAll
    public static void init() {
        new InitDbCommand().execute();
    }

    @Test
    public void executeSelectCommandTest() {
        String log = executeAndReturnLog("SELECT * FROM customers");
        //If log contains SQLException then command failed
        Assertions.assertFalse(log.contains("SQLException"));
    }

    @Test
    public void executeUpdateCommandTest() {
        String log = executeAndReturnLog("INSERT INTO customers (first_name, last_name) VALUES ('Test', 'User')");
        //If log contains SQLException then command failed
        Assertions.assertFalse(log.contains("SQLException"));
    }

    @Test
    public void executeWrongCommandTest() {
        String log = executeAndReturnLog("Some non-sql text");
        //If log contains SQLException then command failed
        Assertions.assertTrue(log.contains("SQLException"));
    }

    private String executeAndReturnLog(String command) {
        try {
            Writer defaultWriter = DbLoggerFactory.getInstance().getWriter();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DbLoggerFactory.getInstance().setWriter(new OutputStreamWriter(bout));

            new SqlCommand(command).execute();

            DbLoggerFactory.getInstance().getWriter().flush();
            DbLoggerFactory.getInstance().setWriter(defaultWriter);
            return bout.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
            return "";
        }
    }

    @AfterAll
    public static void deleteTestDatabase() {
        TestUtils.deleteTestDatabase();
    }
}
