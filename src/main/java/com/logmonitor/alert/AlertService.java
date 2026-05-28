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