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

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, log.getTimestamp());
            stmt.setString(2, log.getLevel());
            stmt.setString(3, log.getMessage());
            stmt.setString(4, log.getSource());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAlert(Alert alert) {

        String sql = "INSERT INTO alerts (alert_type, description, triggered_at, severity) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
