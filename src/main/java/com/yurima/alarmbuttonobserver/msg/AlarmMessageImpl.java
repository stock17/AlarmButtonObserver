package com.yurima.alarmbuttonobserver.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmMessageImpl implements AlarmMessage {
    //TODO
    private int id;
    private Date date;
    private AlarmMessage.Location location;
    private String phone;

    private final String ID_NAME = "idkey";
    private final String DATE_NAME = "date";
    private final String LONGITUDE_NAME = "long";
    private final String LATITUDE_NAME = "lat";
    private final String PHONE_NAME = "phone";

    //Create new alarm message in a client application
    public AlarmMessageImpl(int id) {
        this.date = Calendar.getInstance().getTime();
        this.id = id;
    }

    //Recover alarm message from JSON object
    public AlarmMessageImpl(JSONObject json) throws JSONException {
        this.id = json.getInt(ID_NAME);
        this.date = new Date(json.getLong(DATE_NAME));
        if (json.has(LONGITUDE_NAME) && json.has(LATITUDE_NAME)){
            double latitude = json.getDouble(LATITUDE_NAME);
            double longitude = json.getDouble(LONGITUDE_NAME);
            this.location = new Location(latitude, longitude);
        }
        if (json.has(PHONE_NAME)){
            this.phone = json.getString(PHONE_NAME);
        }
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPhone(String phone){
        //TODO check data
        this.phone = phone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("test \n");
        builder.append("id: ").append(id);
        if (location != null){
            builder.append("\nLongitude: ").append(location.getLongitude())
                    .append("\nLatitude: ").append(location.getLatitude());
        }
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss", Locale.US);
        builder.append(("\n" + format.format(date)));
        builder.append("\nSender:" + phone);
        return builder.toString();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(ID_NAME, id);
            if (location != null) {
                json.put(LONGITUDE_NAME,location.getLongitude());
                json.put(LATITUDE_NAME,location.getLatitude());
            }
            json.put(DATE_NAME, date.getTime());
            if (phone != null){
                json.put(PHONE_NAME, phone);
            }
        }catch (JSONException e){
            return null;
        }
        return json;
    }
}

