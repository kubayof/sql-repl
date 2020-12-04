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
    private final String mLogFormatMessage;
    private PrintWriter mWriter;

    DbLogger(String className, PrintWriter writer) {
        mLogFormatMessage = className + ":{0}: {1}";
        this.mWriter = writer;
    }

    void setmWriter(PrintWriter mWriter) {
        this.mWriter = mWriter;
    }

    public PrintWriter getmWriter() {
        return mWriter;
    }

    /**
     * Prints log to 'writer' and stdout
     */
    public void msg(String message) {
        String formatted = formatMessage(message);
        System.out.println(formatted);
        if (mWriter != null) {
            mWriter.println(formatted);
            mWriter.flush();
        }
    }

    /**
     * Prints specified string to 'writer' with preceding time and class name
     */
    public void info(String message) {
        if (mWriter != null) {
            mWriter.println(formatMessage(message));
            mWriter.flush();
        }
    }

    public String formatMessage(String message) {
        return MessageFormat.format(mLogFormatMessage,
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now()),
                message);
    }
}
