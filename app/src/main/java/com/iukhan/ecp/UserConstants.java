package com.iukhan.ecp;

import android.app.Application;

import com.google.firebase.database.DataSnapshot;

public class UserConstants extends Application {
    private DataSnapshot snapshot;

    public DataSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(DataSnapshot snapshot) {
        this.snapshot = snapshot;
    }


//    private String name;
//    private String cnic;
//    private String latitude;
//    private String longitude;
//    private String email;
//
//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCnic() {
//        return cnic;
//    }
//
//    public void setCnic(String cnic) {
//        this.cnic = cnic;
//    }
//
//    public String getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(String latitude) {
//        this.latitude = latitude;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }
}
