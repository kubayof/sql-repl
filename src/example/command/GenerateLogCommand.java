package example.command;

import example.logging.DbLogger;
import example.logging.DbLoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Scanner;

/**
 * Select all data from table and prints it to report file
 */
public class GenerateLogCommand extends JdbcCommand {
    private final DbLogger mLogger = DbLoggerFactory.getInstance().getLogger(GenerateLogCommand.class);
    private final String[] mTableNames;

    public GenerateLogCommand(String[] tableNames) {
        this.mTableNames = tableNames;
    }

    @Override
    public void execute() {
        System.out.print("Name of report file: ");
        String reportFileName = new Scanner(System.in).nextLine();

        try (PrintStream out = new PrintStream(new FileOutputStream(reportFileName));
             Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {

            for (String tableName : mTableNames) {
                try {
                    ResultSet rs = stmt.executeQuery(MessageFormat.format("SELECT * FROM {0}", tableName));
                    ResultSetMetaData rsmd = rs.getMetaData();
                    out.println("Log for table '" + tableName + "':");
                    while (rs.next()) {
                        out.print("{");
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                            out.print(rsmd.getColumnLabel(i));
                            out.print(" = ");
                            out.print(rs.getString(i));
                            out.print("; ");
                        }
                        out.println("}");
                    }
                } catch (SQLException e) {
                    //Fail only for current table, continue execution for others
                    mLogger.msg(e.getMessage());
                    e.printStackTrace(mLogger.getmWriter());
                }
            }
        } catch (FileNotFoundException | SQLException e) {
            mLogger.msg(e.getMessage());
            e.printStackTrace(mLogger.getmWriter());
        }

    }
}
