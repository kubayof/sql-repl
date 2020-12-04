package example.logging;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides loggers for program, creates one DbLoggerInstance per class
 */
public class DbLoggerFactory {
    private static final DbLoggerFactory instance = new DbLoggerFactory();

    public static DbLoggerFactory getInstance() {
        return instance;
    }

    private PrintWriter printWriter;
    private final Map<Class<?>, DbLogger> loggers = new HashMap<>();
    private final DbLogger logger = getLogger(getClass());

    private DbLoggerFactory() {
        try {
            printWriter = new PrintWriter(new FileOutputStream("log.txt"));
        } catch (FileNotFoundException e) {
            // Normally, cannot reach here, file will be created if not exists
            System.err.println("Exception in DbLoggerFactory: " + e.getMessage());
        }
    }



    /**
     * If loggers map contains DbLogger for specified class - returns it,
     * otherwise - creates new logger for specified class, puts it to 'loggers' map abd returns it
     * @return DbLogger for specified Class
     */
    public DbLogger getLogger(Class<?> clazz) {
        return loggers.computeIfAbsent(clazz, c -> new DbLogger(clazz.getName(), printWriter));
    }

    /**
     * Updates writer for each DbLogger that was already created.
     * After calling this method each newly created logger will use specified writer
     */
    public void setWriter(Writer writer) {
        if (writer == null) {
            throw new IllegalArgumentException(logger.formatMessage("Writer cannot be null"));
        }
        printWriter = new PrintWriter(writer);
        loggers.values().forEach(logger -> logger.setWriter(printWriter));
    }

    public Writer getWriter() {
        return printWriter;
    }
}
