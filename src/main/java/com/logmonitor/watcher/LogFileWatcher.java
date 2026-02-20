package com.logmonitor.watcher;

import com.logmonitor.model.LogEntry;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileWatcher {
    private static final String LOG_FILE = "logs/sample-app.log";

    public void readLogs() {
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ", 3); // timestamp, level, message
                LocalDateTime timestamp = LocalDateTime.parse(parts[0] + " " + parts[1], formatter);
                String level = parts[2].split(" ")[0];
                String message = parts[2].substring(level.length()).trim();

                LogEntry log = new LogEntry(timestamp, level, message, "Application");
                System.out.println(log);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFileWatcher watcher = new LogFileWatcher();
        watcher.readLogs();
    }
}