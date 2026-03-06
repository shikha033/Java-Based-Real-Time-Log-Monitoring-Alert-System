package com.logmonitor.alert;

import com.logmonitor.model.LogEntry;

import java.time.LocalDateTime;

public class AlertService {

    public void processLog(LogEntry logEntry) {

        if ("ERROR".equalsIgnoreCase(logEntry.getLevel())) {
