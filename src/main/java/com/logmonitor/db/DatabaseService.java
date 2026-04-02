package com.logmonitor.db;

import com.logmonitor.model.LogEntry;
import com.logmonitor.alert.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseService {

    private static final String URL = "jdbc:mysql://localhost:3306/logmonitor";
    private static final String USER = "root";
    private static final String PASSWORD = "yourpassword";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void insertLog(LogEntry log) {

        String sql = "INSERT INTO logs (timestamp, level, message, source) VALUES (?, ?, ?, ?)";
