package com.yurima.alarmbuttonobserver.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmMessageImpl implements AlarmMessage {
    //TODO
    private int key;
    private Date date;
    private AlarmMessage.Location location;
    private String sender;

    private final String KEY_NAME = "key";
    private final String DATE_NAME = "date";
    private final String LONGITUDE_NAME = "long";
    private final String LATITUDE_NAME = "lat";
    private final String SENDER_NAME = "sender";

    public AlarmMessageImpl(int key) {
        this.date = Calendar.getInstance().getTime();
        this.key = key;
    }

    public AlarmMessageImpl(JSONObject json) throws JSONException {
        this.key = json.getInt(KEY_NAME);
        this.date = new Date(json.getLong(DATE_NAME));
        if (json.has(LONGITUDE_NAME) && json.has(LATITUDE_NAME)){
            double latitude = json.getDouble(LATITUDE_NAME);
            double longitude = json.getDouble(LONGITUDE_NAME);
            this.location = new Location(latitude, longitude);
        }
        if (json.has(SENDER_NAME)){
            this.sender = json.getString(SENDER_NAME);
        }
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSender(String sender){
        //TODO check data
        this.sender = sender;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("test \n");
        builder.append("key: ").append(key);
        if (location != null){
            builder.append("\nLongitude: ").append(location.getLongitude())
                    .append("\nLatitude: ").append(location.getLatitude());
        }
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss", Locale.US);
        builder.append(("\n" + format.format(date)));
        builder.append("\nSender:" + sender);
        return builder.toString();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(KEY_NAME, key);
            if (location != null) {
                json.put(LONGITUDE_NAME,location.getLongitude());
                json.put(LATITUDE_NAME,location.getLatitude());
            }
            json.put(DATE_NAME, date.getTime());
            if (sender != null){
                json.put(SENDER_NAME, sender);
            }
        }catch (JSONException e){
            return null;
        }
        return json;
    }
}

