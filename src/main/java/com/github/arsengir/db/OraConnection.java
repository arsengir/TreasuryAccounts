package com.github.arsengir.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class OraConnection {
    private static Connection conn;

    public static void setCon(String server) {
        String url = null;
        String user = null;
        String password = null;
        String rootPath;
        try {
            rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        } catch (Exception e) {
            rootPath = "";
        }
        String dbConfigPath = rootPath + "db.properties";

        Properties dbProps = new Properties();
        try {
            dbProps.load(new FileInputStream(dbConfigPath));
            if ("test".equals(server)) {
                url = dbProps.getProperty("urltest");
            } else {
                url = dbProps.getProperty("url");
            }
            user = dbProps.getProperty("user");
            password = dbProps.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(url);
        System.out.println(user);
        System.out.println(password);

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void closeConn() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void setUserIsn(double pUserIsn) {
        try (PreparedStatement stat = conn.prepareStatement("{call init.setuser(?)}")){
            stat.setDouble(1, pUserIsn);
            stat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
