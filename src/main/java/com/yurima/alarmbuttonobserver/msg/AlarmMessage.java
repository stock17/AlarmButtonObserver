package com.yurima.alarmbuttonobserver.msg;

import org.json.JSONObject;

public interface AlarmMessage {

    JSONObject toJson();
    void setLocation(Location location);
    void setPhone(String phone);

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


