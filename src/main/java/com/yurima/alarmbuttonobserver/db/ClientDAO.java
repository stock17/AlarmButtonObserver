package com.yurima.alarmbuttonobserver.db;

import java.sql.*;


public class ClientDAO {

    private static final String CREATE_TABLE_QUERY = String.format("CREATE TABLE IF NOT EXISTS clients (id SERIAL, clientId INT, name varchar(255) not null unique,address varchar(255) not null,phone varchar(20) not null,latitude DECIMAL(255),longitude DECIMAL(255),primary key(id));");

    public ClientDAO() {
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean createTable() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        boolean result = false;
        try {
            connection = Database.getDBConnection();
            statement = connection.createStatement();
            result = statement.execute(CREATE_TABLE_QUERY);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            return result;
        }
    }



    public int addClient(Client client) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "INSERT INTO clients(clientId, name, address, phone, latitude, longitude) VALUES(?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, client.getClientId());
            statement.setString(2, client.getName());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getPhone());
            statement.setDouble(5, client.getLatitude());
            statement.setDouble(6, client.getLongitude());
            statement.executeUpdate();
            connection.commit();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                    return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null)    connection.rollback();

        } finally {
            if (rs != null)            rs.close();
            if (statement != null)     statement.close();
            if (connection != null)    connection.close();
        }

        return 0;
    }
}
