package example;

import example.command.JdbcCommand;
import example.logging.DbLogger;
import example.logging.DbLoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Utility class for accessing database properties
 */
public class DbUtils {
    private static final DbLogger logger = DbLoggerFactory.getInstance().getLogger(JdbcCommand.class);
    private static Properties props;

    /**
     * Returns connection properties loaded from resources/db.properties
     * or test/resources/db.properties
     */
    public static Properties getDbProps() {
        if (props == null) {
            props = new Properties();
            try {
                props.load(DbUtils.class.getClassLoader().getResourceAsStream("db.properties"));
            } catch (IOException e) {
                // Do nothing, if properties was not loaded from file user will be prompted to enter it from keyboard
            }
            typeUnspecifiedDbProps(props);
        }

        return props;
    }


    /**
     * Checks database properties, if url, username or password is not specified
     * asks to enter it from console
     */
    public static void typeUnspecifiedDbProps(Properties props) {
        String[] requiredProps = {"url", "username", "password", "driver"};

        Scanner stdinReader = new Scanner(System.in);
        for (String prop : requiredProps) {
            if (!props.containsKey(prop)) {
                System.out.println("Property '" + prop + "' is missing in db.properties file.\nPlease, enter it: ");
                String value;
                do {
                    value = stdinReader.nextLine();
                } while (value.trim().isEmpty());
                props.setProperty(prop, value);
            }
        }
    }


    /**
     * Returns connection for properties from db.properties
     */
    public static Connection getConnection() {
        Properties dbProps = getDbProps();
        String url = dbProps.getProperty("url");
        String username = dbProps.getProperty("username");
        String password = dbProps.getProperty("password");
        String driver = dbProps.getProperty("driver");
        Connection connection = null;
        do {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                dbProps.remove("url");
                dbProps.remove("username");
                dbProps.remove("password");
                processException(e);
            } catch (ClassNotFoundException e) {
                dbProps.remove("driver");
                processException(e);
            }
        } while (connection == null);

        return connection;
    }

    /**
     * Prints exception, writes prompt to screen,
     * calls 'typeUnspecifiedDbProps' to reenter properties
     * if user requested
     */
    private static void processException(Exception e) {
        logger.msg("Exception: " + e.getMessage());
        System.out.print("Type 'n' to exit or something else to reenter properties: ");
        String line = new Scanner(System.in).nextLine().trim();
        if (line.equals("n")) {
            System.exit(1);
        }
        typeUnspecifiedDbProps(props);
    }

    // Deny inheritance for utility class
    private DbUtils() {}
}
