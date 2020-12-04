package example.command;

import example.logging.DbLogger;
import example.logging.DbLoggerFactory;

import java.sql.*;

/**
 * Executes SQL query from repl
 */
public class SqlCommand extends JdbcCommand {
    private final DbLogger logger = DbLoggerFactory.getInstance().getLogger(SqlCommand.class);
    private final String queryString;

    public SqlCommand(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public void execute() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            if (queryString.toUpperCase().startsWith("SELECT")) {
                ResultSet rs = statement.executeQuery(queryString);

                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        System.out.print(rsmd.getColumnLabel(i) + ": '" + rs.getString(i) + "' ");
                    }
                    System.out.println();
                }
            } else {
                statement.execute(queryString);
            }
        } catch (SQLException e) {
            logger.msg("SQException: " + e.getMessage());
            e.printStackTrace(logger.getWriter());
        }
    }
}
