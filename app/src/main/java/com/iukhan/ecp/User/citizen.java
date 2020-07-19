package com.iukhan.ecp.User;

public class citizen {
    private String cnic, name, lattitude, longitude;

    public citizen(String cnic, String name, String lattitude, String longitude) {
        this.cnic = cnic;
        this.name = name;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
