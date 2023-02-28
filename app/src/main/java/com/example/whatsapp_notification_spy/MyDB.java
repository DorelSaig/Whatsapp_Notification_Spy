package com.example.whatsapp_notification_spy;

import java.util.ArrayList;

public class MyDB {

    private ArrayList<MyMessage> allDeleted;

    public MyDB() { };

    public ArrayList<MyMessage> getMessages() {
        if(allDeleted == null){
            allDeleted = new ArrayList<MyMessage>();
        }
        return allDeleted;
    }

    public MyDB setMessages(ArrayList<MyMessage> allDeleted) {
        this.allDeleted = allDeleted;
        return this;
    }


}
