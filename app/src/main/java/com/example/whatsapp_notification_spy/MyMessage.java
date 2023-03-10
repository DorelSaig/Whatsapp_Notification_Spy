package com.example.whatsapp_notification_spy;

public class MyMessage {

    private String contact_name;
    private String message;
    private String time;

    public MyMessage(String contact_name, String message, String time) {
        this.contact_name = contact_name;
        this.message = message;
        this.time = time;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
