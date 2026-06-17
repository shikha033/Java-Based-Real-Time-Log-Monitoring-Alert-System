package com.logmonitor.analytics;

import com.logmonitor.db.DatabaseService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AnalyticsService {

    private DatabaseService dbService = new DatabaseService();

    public void showStatistics() {

        try (Connection conn = dbService.connect();
                Statement stmt = conn.createStatement()) {

            ResultSet rs1 = stmt.executeQuery(
                    "SELECT COUNT(*) FROM logs");

            if (rs1.next()) {
                System.out.println("Total Logs: " + rs1.getInt(1));
            }

            ResultSet rs2 = stmt.executeQuery(
                    "SELECT COUNT(*) FROM alerts");

            if (rs2.next()) {
                System.out.println("Total Alerts: " + rs2.getInt(1));
            }
            ResultSet infoLogs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM logs WHERE level='INFO'");

            if (infoLogs.next()) {
                System.out.println("INFO Logs: " + infoLogs.getInt(1));
            }

            ResultSet warnLogs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM logs WHERE level='WARN'");

            if (warnLogs.next()) {
                System.out.println("WARN Logs: " + warnLogs.getInt(1));
            }

            ResultSet errorLogs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM logs WHERE level='ERROR'");

            if (errorLogs.next()) {
                System.out.println("ERROR Logs: " + errorLogs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}