package example.command;

import example.DbUtils;

import java.sql.Connection;

/**
 * Abstract base class for commands that require connection
 */
public abstract class JdbcCommand implements Command {

    protected Connection getConnection() {
        return DbUtils.getConnection();
    }
}
