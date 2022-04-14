package dev.tyler.utilities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class Logger {


    public static void log(String message, LogLevel level){
        String logMessage = level.name() +" " +  message + " " + new Date() + "\n";

        try {
            Files.write(Paths.get("src/main/java/dev/tyler/logs/applogs.log"),
                    logMessage.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
        }
    }
}

