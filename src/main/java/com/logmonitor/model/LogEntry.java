
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
