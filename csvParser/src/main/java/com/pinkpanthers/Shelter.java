package com.pinkpanthers;

public class Shelter {
    private String shelterName;
    private String capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String specialNotes;
    private String restrictions;
    private String gender;

    Shelter(String shelterName,
            String capacity,
            String specialNotes,
            double latitude,
            double longitude,
            String phoneNumber,
            String restrictions) {
        this.shelterName = shelterName;
        this.specialNotes = specialNotes;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "shelterName='" + shelterName + '\'' +
                ", capacity='" + capacity + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", specialNotes='" + specialNotes + '\'' +
                ", restrictions='" + restrictions + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
