package com.example.calculator;

public class ChildDetailsRegDB {

    private String name;
    private String age;
    private String uname;
    private String pass;
    private String selectedRadioValue;
    private String email;
    private double longitude;
    private double latitude;

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    private String devID;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDevID() {
        return devID;
    }

    public ChildDetailsRegDB(String name, String age, String uname, String pass, String selectedRadioValue, String email, double longitude, double latitude, String devID) {
        this.name = name;
        this.age = age;
        this.uname = uname;
        this.pass = pass;
        this.selectedRadioValue = selectedRadioValue;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.devID = devID;
    }

    public ChildDetailsRegDB(String name, String age, String uname, String pass, String selectedRadioValue, String email) {
        this.name = name;
        this.age = age;
        this.uname = uname;
        this.pass = pass;
        this.selectedRadioValue = selectedRadioValue;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ChildDetailsRegDB()
    {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUname() {
        return uname;
    }


    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSelectedRadioValue() {
        return selectedRadioValue;
    }

    public void setSelectedRadioValue(String selectedRadioValue) {
        this.selectedRadioValue = selectedRadioValue;
    }

}
