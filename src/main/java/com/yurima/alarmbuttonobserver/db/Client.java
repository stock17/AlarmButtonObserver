package com.yurima.alarmbuttonobserver.db;

import javafx.beans.property.*;

public class Client {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty clientId = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    private DoubleProperty latitude = new SimpleDoubleProperty();
    private DoubleProperty longitude = new SimpleDoubleProperty();

    public void setId(int id) {
        this.id.set(id);
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    public int getId() {
        return id.get();
    }

    public int getClientId() {
        return clientId.get();
    }

    public String getName() {
        return name.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public double getLatitude() {
        return latitude.get();
    }

    public double getLongitude() {
        return longitude.get();
    }

}
