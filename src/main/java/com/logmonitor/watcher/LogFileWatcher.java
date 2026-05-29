package com.logmonitor.watcher;

import com.logmonitor.model.LogEntry;
import com.logmonitor.alert.AlertService;
import com.logmonitor.db.DatabaseService;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileWatcher implements Runnable {

    private static final String LOG_FILE = "logs/sample-app.log";
    private volatile boolean running = true;

    @Override
    public void run() {

        try (RandomAccessFile file = new RandomAccessFile(LOG_FILE, "r")) {

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

                        LocalDateTime timestamp = LocalDateTime.parse(
                                parts[0] + " " + parts[1],
                                formatter
                        );

                        String level = parts[2].split(" ")[0];

                        String message =
                                parts[2].substring(level.length()).trim();

                        LogEntry log = new LogEntry(
                                timestamp,
                                level,
                                message,
                                "Application"
                        );

                        // Print log
                        System.out.println("NEW LOG: " + log);

                        // Store log in database
                        dbService.insertLog(log);

                        // Process alert
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