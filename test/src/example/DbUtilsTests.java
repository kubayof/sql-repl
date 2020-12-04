package example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Tests for DbUtils class
 */
public class DbUtilsTests {
    @Test
    public void loadPropertiesTest() {
        Properties actualProperties = DbUtils.getDbProps();
        Properties expectedProperties = new Properties();
        expectedProperties.setProperty("url", "jdbc:h2:./test_db");
        expectedProperties.setProperty("username", "sa");
        expectedProperties.setProperty("password", "");
        expectedProperties.setProperty("driver", "org.h2.Driver");

        Assertions.assertEquals(expectedProperties, actualProperties);
    }

    @Test
    public void typeUnspecifiedPropertiesTest() {
        String propsStr = "url\nusername\npassword\ndriver";
        InputStream testIn = new ByteArrayInputStream(propsStr.getBytes());
        Properties actualProperties = new Properties();
        //Replace System.in for testing time
        InputStream defaultStdin = System.in;
        System.setIn(testIn);
        PrintStream defaultStdout = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {
                //Ignore output From System.out during test
            }
        }));

        DbUtils.typeUnspecifiedDbProps(actualProperties);

        Properties expectedProperties = new Properties();
        expectedProperties.setProperty("url", "url");
        expectedProperties.setProperty("username", "username");
        expectedProperties.setProperty("password", "password");
        expectedProperties.setProperty("driver", "driver");

        //Restore original System.in and System.out
        System.setIn(defaultStdin);
        System.setOut(defaultStdout);

        Assertions.assertEquals(expectedProperties, actualProperties);
    }

    @Test
    public void connectToH2DatabaseTest() {
        try {
            DbUtils.getConnection().close();
            //If an exception was not thrown - connection established and closed
            Assertions.assertTrue(true);
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @AfterAll
    public static void deleteTestDatabase() {
        TestUtils.deleteTestDatabase();
    }
}
