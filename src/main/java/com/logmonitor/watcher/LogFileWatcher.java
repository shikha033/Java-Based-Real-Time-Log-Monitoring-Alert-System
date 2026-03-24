// package com.logmonitor.watcher;

// import com.logmonitor.model.LogEntry;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

// public class LogFileWatcher {
//     private static final String LOG_FILE = "logs/sample-app.log";

//     public void readLogs() {
//         try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
//             String line;
//             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//             while ((line = br.readLine()) != null) {
//                 String[] parts = line.split(" ", 3); // timestamp, level, message
//                 LocalDateTime timestamp = LocalDateTime.parse(parts[0] + " " + parts[1], formatter);
//                 String level = parts[2].split(" ")[0];
//                 String message = parts[2].substring(level.length()).trim();

//                 LogEntry log = new LogEntry(timestamp, level, message, "Application");
//                 System.out.println(log);
//             }

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public static void main(String[] args) {
//         LogFileWatcher watcher = new LogFileWatcher();
//         watcher.readLogs();
//     }
// }


package com.logmonitor.watcher;

import com.logmonitor.model.LogEntry;
import com.logmonitor.alert.AlertService;

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
           
            while (running) {

                long fileLength = file.length();

                if (fileLength > filePointer) {
                    file.seek(filePointer);
                    String line;
                     AlertService alertService = new AlertService();
                    while ((line = file.readLine()) != null) {

                        String[] parts = line.split(" ", 3);

                        LocalDateTime timestamp = LocalDateTime.parse(
                                parts[0] + " " + parts[1], formatter);

                        String level = parts[2].split(" ")[0];
                        String message = parts[2].substring(level.length()).trim();

                        LogEntry log = new LogEntry(timestamp, level, message, "Application");

                        System.out.println("NEW LOG: " + log);
                        alertService.processLog(log);
                    }

                    filePointer = file.getFilePointer();
                }

                Thread.sleep(2000); // Check every 2 seconds
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    public static void main(String[] args) {
        LogFileWatcher watcher = new LogFileWatcher();
        Thread thread = new Thread(watcher);

        thread.start();
    }
}