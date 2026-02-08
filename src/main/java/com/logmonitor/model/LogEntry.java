package com.logmonitor.model;
import java.time.LocalDateTime;
public class LogEntry {
    private LocalDateTime timestamp;
    private String level;    // INFO, WARN, ERROR
    private String message;
    private String source;
