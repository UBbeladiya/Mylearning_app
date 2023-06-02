package com.example.learning_app.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class material_Model{
    String code;
    String PDF;
    String Topic;
    String Unit_ID;
    public material_Model(String code, String PDF, String Topic, String Unit_ID) {
        this.code = code;
        this.PDF= PDF;
        this.Topic = Topic;
        this.Unit_ID = Unit_ID;

    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPDF() {
        return PDF;
    }

    public void setPDF(String PDF) {
        this.PDF = PDF;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getUnit_ID() {
        return Unit_ID;
    }

    public void setUnit_ID(String unit_ID) {
        Unit_ID = unit_ID;
    }


}
