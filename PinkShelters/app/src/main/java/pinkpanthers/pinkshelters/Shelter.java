package pinkpanthers.pinkshelters;

public class Shelter {
    private String shelterName;
    private int capacity;
    private double latitude;
    private double longitude;
    private int phoneNumber;
    private String specialNotes;
    private String restrictions;
    private String gender;

    Shelter(String shelterName,
            int capacity,
            String specialNotes,
            int latitude,
            int longitude,
            int phoneNumber,
            String restrictions) {
        this.shelterName = shelterName;
        this.specialNotes = specialNotes;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
    }

    public void setshelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getshelterName() {
        return this.shelterName;
    }

    public void setgender(String gender) {

        this.gender = gender;
    }

    public String getgender() {

        return this.gender;
    }

    public void setcapacity(int capacity) {
        this.capacity = capacity;
    }


    public int getcapacity() {
        return this.capacity;
    }

    public void setlatitude(int latitude) {
        this.latitude = latitude;
    }

    public double getlatitude() {
        return this.latitude;
    }

    public void setlongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
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





