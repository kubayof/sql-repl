package example.command;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Select all data from table and prints it to logger,
 * prints 10 entries from selected data to console
 */
public class GenerateLogCommand implements Command {
    private final String[] tableNames;

    GenerateLogCommand(String[] tableNames) {
        this.tableNames = tableNames;
    }

    @Override
    public void execute() {
        throw new NotImplementedException();
    }
}
