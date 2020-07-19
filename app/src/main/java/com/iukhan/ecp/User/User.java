package com.iukhan.ecp.User;

public class User {
    public String name, email, cnic, phone, gender, latitude, longitude;

    public User() {
    }

    public User(String name, String email, String cnic, String phone, String gender, String latitude, String longitude) {
        this.name = name;
        this.email = email;
        this.cnic = cnic;
        this.phone = phone;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
