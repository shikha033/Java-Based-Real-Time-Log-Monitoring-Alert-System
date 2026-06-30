package com.logmonitor.search;

import com.logmonitor.db.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LogSearchService {


    private DatabaseService dbService =
            new DatabaseService();


    public void searchByLevel(String level) {


        String sql =
        "SELECT * FROM logs WHERE level=?";


        try(Connection conn = dbService.connect();
            PreparedStatement stmt =
            conn.prepareStatement(sql)) {
