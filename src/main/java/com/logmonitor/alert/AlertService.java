package com.logmonitor.alert;

import com.logmonitor.model.LogEntry;

import java.time.LocalDateTime;

public class AlertService {

    public void processLog(LogEntry logEntry) {

        if ("ERROR".equalsIgnoreCase(logEntry.getLevel())) {

            Alert alert = new Alert(
                    "APPLICATION_ERROR",
                    logEntry.getMessage(),
                    LocalDateTime.now(),
                    "HIGH"
            );

            triggerAlert(alert);
        }
    }

    private void triggerAlert(Alert alert) {
        System.out.println("🚨 " + alert);
    }
}