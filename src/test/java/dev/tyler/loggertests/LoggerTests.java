package dev.tyler.loggertests;

import dev.tyler.utilities.LogLevel;
import dev.tyler.utilities.Logger;
import org.junit.jupiter.api.Test;

public class LoggerTests {

    @Test
    void info_log_test(){
        Logger.log("Hello", LogLevel.INFO);
        Logger.log("Testing", LogLevel.DEBUG);
    }
}
