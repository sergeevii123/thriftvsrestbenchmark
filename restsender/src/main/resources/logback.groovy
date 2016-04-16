import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender

import static ch.qos.logback.classic.Level.INFO

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}

appender("FAILED_FILE_APPENDER", FileAppender) {
    file = "/logs/failed.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}

logger("org.springframework", INFO, ["STDOUT"], false)
logger("benchmark", INFO, ["STDOUT"], false)
logger("org.apache", INFO, ["STDOUT"], false)
logger("com.netflix", INFO, ["STDOUT"], false)

logger("FAILED_LOGGER", INFO, ["FAILED_FILE_APPENDER"], false)

root(INFO, ["FAILED_FILE_APPENDER", "STDOUT"])