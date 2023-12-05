package com.hotel.challenge.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection recoverConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/hotelchallenge?userTimeZone=true&serverTimeZone=UTC",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
    }

}
