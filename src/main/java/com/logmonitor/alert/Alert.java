package com.logmonitor.alert;

import java.time.LocalDateTime;

public class Alert {

    private String type;
    private String message;
    private LocalDateTime triggeredAt;
    private String severity;

    public Alert(String type, String message, LocalDateTime triggeredAt, String severity) {
        this.type = type;
        this.message = message;
        this.triggeredAt = triggeredAt;
        this.severity = severity;
    }

    public String getType() { return type; }
    public String getMessage() { return message; }
    public LocalDateTime getTriggeredAt() { return triggeredAt; }
    public String getSeverity() { return severity; }

    @Override
    public String toString() {
        return "ALERT [" + severity + "] " + type +
               " - " + message +
               " at " + triggeredAt;
    }
}