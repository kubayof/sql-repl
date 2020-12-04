package example.command;

import example.logging.DbLogger;
import example.logging.DbLoggerFactory;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Scanner;

/**
 * Uses resources ddl.sql and dml.sql to create tables and populate them with date respectively
 */
public class InitDbCommand extends JdbcCommand {
    private final DbLogger logger = DbLoggerFactory.getInstance().getLogger(InitDbCommand.class);

    @Override
    public void execute() {
        executeCommands("ddl.sql");
        executeCommands("dml.sql");
    }

    private void executeCommands(String resourceFileName) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             Scanner scanner = new Scanner(Objects.requireNonNull(
                     getClass().getClassLoader().getResourceAsStream(resourceFileName)))) {
            StringBuilder queryBuilder;

            while (scanner.hasNextLine()) {
                queryBuilder = new StringBuilder();
                String line;
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.endsWith(";")) {
                        queryBuilder.append(line, 0, line.length() - 1);
                        break;
                    }
                    queryBuilder.append(line).append(" ");
                }

                String query = queryBuilder.toString();

                try {
                    statement.execute(query);
                } catch (SQLException e) {
                    logger.msg(MessageFormat.format("Error while executing query '{0}': {1}",
                            query,
                            e.getMessage()));
                    e.printStackTrace(logger.getWriter());
                }
            }
        } catch (SQLException e) {
            logger.msg("Exception while closing connection: " + e.getMessage());
            e.printStackTrace(logger.getWriter());
        }
    }
}
