
package com.logmonitor.model;

import java.time.LocalDateTime;

public class LogEntry {
    private LocalDateTime timestamp;
    private String level;    // INFO, WARN, ERROR
    private String message;
    private String source;

    // Constructor
    public LogEntry(LocalDateTime timestamp, String level, String message, String source) {
        this.timestamp = timestamp;
        this.level = level;
        this.message = message;
        this.source = source;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getLevel() { return level; }
    public String getMessage() { return message; }
    public String getSource() { return source; }

    // Setters
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setLevel(String level) { this.level = level; }
    public void setMessage(String message) { this.message = message; }
    public void setSource(String source) { this.source = source; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + level + " : " + message + " (" + source + ")";
    }
}