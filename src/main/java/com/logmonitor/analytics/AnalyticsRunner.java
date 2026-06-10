package com.logmonitor.analytics;


public class AnalyticsRunner {

    public static void main(String[] args) {

        AnalyticsService analytics =
                new AnalyticsService();

        analytics.showStatistics();
    }
}
