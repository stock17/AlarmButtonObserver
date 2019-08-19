package com.yurima.alarmbuttonobserver.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Model {

    private ClientDAO dao;

    public Model () {
        dao = new ClientDAO();
    }

    public void addClient(Client client) throws SQLException {
        dao.addClient(client);
    }

    public ObservableList<Client> getClients() {
        ObservableList<Client> observableList = null;
        try {
            observableList = FXCollections.observableList(dao.getClients());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return observableList;
    }
}
