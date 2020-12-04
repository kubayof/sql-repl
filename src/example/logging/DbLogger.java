package example.logging;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Logger class, uses writer provided by DbLoggerFactory
 */
public class DbLogger {
    private final String logFormatMessage;
    private PrintWriter writer;

    DbLogger(String className, PrintWriter writer) {
        logFormatMessage = className + ":{0}: {1}";
        this.writer = writer;
    }

    void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    /**
     * Prints log to 'writer' and stdout
     */
    public void msg(String message) {
        String formatted = formatMessage(message);
        System.out.println(formatted);
        if (writer != null) {
            writer.println(formatted);
            writer.flush();
        }
    }

    /**
     * Prints specified string to 'writer' with preceding time and class name
     */
    public void info(String message) {
        if (writer != null) {
            writer.println(formatMessage(message));
            writer.flush();
        }
    }

    public String formatMessage(String message) {
        return MessageFormat.format(logFormatMessage,
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now()),
                message);
    }
}
