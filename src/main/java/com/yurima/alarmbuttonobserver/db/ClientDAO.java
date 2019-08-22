package com.yurima.alarmbuttonobserver.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDAO {

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS clients (" +
                    "id SERIAL," +
                    "clientId INT UNIQUE, " +
                    "name varchar(255) not null, " +
                    "address varchar(255) not null, " +
                    "phone varchar(20) not null, " +
                    "latitude DECIMAL(255), " +
                    "longitude DECIMAL(255), " +
                    "primary key(id)" +
            ");";

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
            rs = statement.getGeneratedKeys();
            connection.commit();
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

    public int editClient(Client client) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "UPDATE clients SET clientId = ?, name = ?, address = ?, phone = ?, latitude = ?, longitude = ? WHERE clientId = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, client.getClientId());
            statement.setString(2, client.getName());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getPhone());
            statement.setDouble(5, client.getLatitude());
            statement.setDouble(6, client.getLongitude());
            statement.setInt(7, client.getClientId());
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            connection.commit();
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

    public int deleteClient(Client client) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "DELETE FROM clients WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, client.getId());
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            connection.commit();
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

    public List<Client> getClients() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Client> list = new ArrayList<>();
        try {
            connection = Database.getDBConnection();
            connection.setAutoCommit(false);
            String query = "SELECT * FROM clients";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            connection.commit();

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt(1));
                client.setClientId(rs.getInt(2));
                client.setName(rs.getString(3));
                client.setAddress(rs.getString(4));
                client.setPhone(rs.getString(5));
                client.setLatitude(rs.getDouble(6));
                client.setLongitude(rs.getDouble(7));
                list.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null)    connection.rollback();

        } finally {
            if (rs != null)            rs.close();
            if (statement != null)     statement.close();
            if (connection != null)    connection.close();
        }

        return list;

    }
}
