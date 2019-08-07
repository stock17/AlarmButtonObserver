package com.yurima.alarmbuttonobserver.msg;

import org.json.JSONObject;

import java.util.Date;

public interface AlarmMessage {

    public static final String ID_NAME = "id";
    public static final String DATE_NAME = "date";
    public static String LONGITUDE_NAME = "long";
    public static String LATITUDE_NAME = "lat";
    public static String PHONE_NAME = "phone";

    JSONObject toJson();

    void setLocation(Location location);
    void setPhone(String phone);

    int getId();
    Date getDate();
    Location getLocation();
    String getPhone();


    class Location{
        double latitude;
        double longitude;

        public Location (double lat, double lng) {
            latitude = lat;
            longitude = lng;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}


