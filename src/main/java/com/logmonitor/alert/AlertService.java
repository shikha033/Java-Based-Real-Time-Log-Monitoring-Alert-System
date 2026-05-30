package com.logmonitor.alert;

import com.logmonitor.model.LogEntry;
import com.logmonitor.db.DatabaseService;

import java.time.LocalDateTime;

public class AlertService {

   // added ---
    private DatabaseService dbService = new DatabaseService();
    private int errorCount = 0;
    private long windowStartTime = System.currentTimeMillis();

    public void processLog(LogEntry logEntry) {

    long currentTime = System.currentTimeMillis();

    // Reset counter after 1 minute
    if ((currentTime - windowStartTime) > 60000) {

        errorCount = 0;
        windowStartTime = currentTime;
    }

    if ("ERROR".equalsIgnoreCase(logEntry.getLevel())) {

        errorCount++;

        Alert alert = new Alert(
                "APPLICATION_ERROR",
                logEntry.getMessage(),
                LocalDateTime.now(),
                "HIGH"
        );

        triggerAlert(alert);
                    LocalDateTime.now(),
                    "HIGH"
            );

            triggerAlert(alert);
        }
    }

    private void triggerAlert(Alert alert) {

        System.out.println("🚨 " + alert);

        // ✅ ADD THIS
        dbService.insertAlert(alert);
    }
}