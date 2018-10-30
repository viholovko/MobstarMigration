package com.viholovko.databasehelper;

import com.viholovko.databaseconnection.SingletonOldDBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OldDatabaseHelper {

    public static ResultSet getResultByRequest(String request) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = SingletonOldDBConnection.getInstance().getConnection();

            stmt = conn.createStatement();
            return stmt.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rs;
    }

    public static int getCountInTable(String tableName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = SingletonOldDBConnection.getInstance().getConnection();
            stmt = conn.createStatement();

            String query = "SELECT COUNT(*) FROM "+ tableName;
            rs =stmt.executeQuery(query);
            //Extact result from ResultSet rs
            while(rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            return count;

        } catch(SQLException s){
            s.printStackTrace();
        }
        // close Connection and Statement

        return count;
    }
}
