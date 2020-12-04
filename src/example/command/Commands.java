package example.command;

import example.logging.DbLogger;
import example.logging.DbLoggerFactory;

import java.util.Arrays;

/**
 * Returns command according to specified string
 * Available commands:
 * <ul>
 *     <li><i>init;</i> - initialize database</li>
 *     <li><i>log 'table_name'*;</i> - create log from specified tables (example: log customers sales)</li>
 *     <li>And sql query by default</li>
 * </ul>
 */
public class Commands {
    private static final DbLogger mLogger = DbLoggerFactory.getInstance().getLogger(Commands.class);

    public static Command get(String command) {
        command = command.trim();
        if (command.startsWith("log")) {
            String[] tables = Arrays.stream(command.substring(3).split("[ ]+"))
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
            if (tables.length == 0) {
                String message = "No tables specified to generate log";
                mLogger.msg(message);
                throw new IllegalStateException(message);
            }
            return new GenerateLogCommand(tables);
        } else if (command.equals("init")) {
            return new InitDbCommand();
        } else {
            return new SqlCommand(command);
        }
    }

    private Commands() {}
}
