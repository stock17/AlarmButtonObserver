package com.yurima.alarmbuttonobserver.db;

import java.sql.SQLException;

public class Model {

    private ClientDAO dao;

    public Model () {
        dao = new ClientDAO();
    }

    public void addClient(Client client) throws SQLException {
        dao.addClient(client);
    }
}
