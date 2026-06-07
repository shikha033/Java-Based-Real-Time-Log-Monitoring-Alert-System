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

        // Generate critical alert
        if (errorCount >= 5) {

            Alert criticalAlert = new Alert(
                    "CRITICAL_ERROR_THRESHOLD",
                    "More than 5 ERROR logs detected within 1 minute",
                    LocalDateTime.now(),
                    "CRITICAL"
            );

            triggerAlert(criticalAlert);

            // Reset counter
            errorCount = 0;
        }
    }
}
    private void triggerAlert(Alert alert) {

        System.out.println("🚨 " + alert);

       
        dbService.insertAlert(alert);
    }
}