package com.logmonitor.watcher;

import com.logmonitor.model.LogEntry;
import com.logmonitor.alert.AlertService;
import com.logmonitor.db.DatabaseService;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.logmonitor.config.ConfigReader;

public class LogFileWatcher implements Runnable {

    // private static final String LOG_FILE = "logs/sample-app.log";
    private volatile boolean running = true;

    @Override
    public void run() {
        ConfigReader config = new ConfigReader();

        String LOG_FILE = config.get("log.file");

        try (RandomAccessFile file = new RandomAccessFile(LOG_FILE, "r")) {

            System.out.println("Watching file: " + new java.io.File(LOG_FILE).getAbsolutePath());
            long filePointer = file.length(); // Start at end of file
            file.seek(filePointer);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Services
            AlertService alertService = new AlertService();
            DatabaseService dbService = new DatabaseService();

            while (running) {

                long fileLength = file.length();

                if (fileLength > filePointer) {

                    file.seek(filePointer);

                    String line;

                    while ((line = file.readLine()) != null) {

                        // Skip empty lines
                        if (line.trim().isEmpty()) {
                            continue;
                        }

                        String[] parts = line.split(" ", 3);

                        // Validate log format
                        if (parts.length < 3) {
                            System.out.println("Invalid log format: " + line);
                            continue;
                        }
                        System.out.println("RAW LINE = [" + line + "]");
                        System.out.println("parts[0] = [" + parts[0] + "]");
                        System.out.println("parts[1] = [" + parts[1] + "]");

                        LocalDateTime timestamp = LocalDateTime.parse(
                                parts[0] + " " + parts[1],
                                formatter);

                        String level = parts[2].split(" ")[0];

                        String message = parts[2].substring(level.length()).trim();

                        LogEntry log = new LogEntry(
                                timestamp,
                                level,
                                message,
                                "Application");

                        System.out.println("NEW LOG: " + log);

                        dbService.insertLog(log);

                        alertService.processLog(log);
                    }

                    filePointer = file.getFilePointer();
                }

                // Check every 2 seconds
                Thread.sleep(2000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {

        LogFileWatcher watcher = new LogFileWatcher();

        Thread thread = new Thread(watcher);

        thread.start();

        System.out.println("Real-time log monitoring started...");
    }
}