package example.command;

import example.logging.DbLogger;
import example.logging.DbLoggerFactory;

import java.sql.*;

/**
 * Executes SQL query from repl
 */
public class SqlCommand extends JdbcCommand {
    private final DbLogger mLogger = DbLoggerFactory.getInstance().getLogger(SqlCommand.class);
    private final String mLueryString;

    public SqlCommand(String queryString) {
        this.mLueryString = queryString;
    }

    @Override
    public void execute() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            if (mLueryString.toUpperCase().startsWith("SELECT")) {
                ResultSet rs = statement.executeQuery(mLueryString);

                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        System.out.print(rsmd.getColumnLabel(i) + ": '" + rs.getString(i) + "' ");
                    }
                    System.out.println();
                }
            } else {
                statement.execute(mLueryString);
            }
        } catch (SQLException e) {
            mLogger.msg("SQException: " + e.getMessage());
            e.printStackTrace(mLogger.getmWriter());
        }
    }
}
