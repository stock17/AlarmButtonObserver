package com.yurima.alarmbuttonobserver.log;

import com.yurima.alarmbuttonobserver.db.Client;
import com.yurima.alarmbuttonobserver.msg.AlarmMessage;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventLogger {

    public static final int ERROR_MESSAGE = 1;
    public static final int SERVER_ON_MESSAGE = 2;
    public static final int SERVER_OFF_MESSAGE = 3;

    private ListView listView;

    public EventLogger(ListView listView) {
        this.listView = listView;
    }

    public void log (AlarmMessage msg, Client client){
        StringBuilder line = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss ");
        line.append(dateFormat.format(msg.getDate()))
        .append(client.getName()).append(" ")
        .append(client.getAddress()).append(" ");
        publish(new Record(line.toString(), Color.RED));
    }

    public void log (int i) {
        StringBuilder line = new StringBuilder();
        line.append(new SimpleDateFormat("dd.MM.yyyy hh:mm:ss ")
                .format(Calendar.getInstance().getTime()));

        switch (i) {
            case ERROR_MESSAGE:
                line.append("Неизвестное сообщение");
                publish(new Record(line.toString(), Color.GREEN));
                break;
            case SERVER_ON_MESSAGE:
                line.append("Сервер подключен");
                publish(new Record(line.toString(), Color.GREEN));
                break;
            case SERVER_OFF_MESSAGE:
                line.append("Сервер отключен");
                publish(new Record(line.toString(), Color.GREEN));
                break;
            default:
        }
    }

    private void publish(Record line) {
        Platform.runLater( () -> {
            listView.getItems().add(line);

        });
    }

    public static class Record{
        private String text;
        private Color color;

        public Record(String text, Color color) {
            this.text = text;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}
