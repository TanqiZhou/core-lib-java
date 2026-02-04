package io.apimatic.core.logger;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.event.Level;
//import org.slf4j.spi.LoggingEventBuilder;

public class Slf4jLogger implements io.apimatic.coreinterfaces.logger.Logger {

    /**
     * The SLF4J logger instance wrapped by this class.
     */
    private final Logger logger;

    /**
     * Constructs a new Slf4jLogger instance wrapping the provided SLF4J Logger.
     * @param logger The SLF4J logger instance to wrap.
     */
    public Slf4jLogger(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Return instance of {@link Logger}
     * @return SLF4J Logger instance.
     */
    public Logger getLogger() {
        return logger;
    }

    /***
     * Log provided message according to logging level.
     * @param level     To provide the Level conversion.
     * @param format    The format string
     * @param arguments List of arguments
     */
    @Override
    public void log(Level level, String format, Map<String, Object> arguments) {
        try {
            for (Map.Entry<String, Object> entry : arguments.entrySet()) {
                MDC.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            Object[] args = arguments.values().toArray();
            switch (level) {
                case DEBUG:
                    logger.debug(format, args);
                    break;
                case INFO:
                    logger.info(format, args);
                    break;
                case WARN:
                    logger.warn(format, args);
                    break;
                case ERROR:
                    logger.error(format, args);
                    break;
            }
        } finally {
            MDC.clear();
        }
    }
}
