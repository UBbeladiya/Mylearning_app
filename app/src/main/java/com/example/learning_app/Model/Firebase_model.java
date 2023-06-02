package com.example.learning_app.Model;

import java.util.ArrayList;

public class Firebase_model {
    String id;
    String name;
    ArrayList<String> topic ;



    public Firebase_model(String id, String name, ArrayList topic ) {
        this.id = id;
        this.name = name;
        this.topic = topic;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTopic() {
        return topic;
    }

    public void setTopic(ArrayList<String> topic) {
        this.topic = topic;
    }
}
