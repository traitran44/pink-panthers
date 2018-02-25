package pinkpanthers.pinkshelters;
public class Shelter {


    private String shelterName;
    private String address;
    private int capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String specialNotes;
    private String restrictions;
    private String gender;

    Shelter(String shelterName,
            String address,
            int capacity,
            String specialNotes,
            double latitude,
            double longitude,
            String phoneNumber,
            String restrictions) {
        this.shelterName = shelterName;
        this.address = address;
        this.specialNotes = specialNotes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;




    }

    public void setShelterName(String shelterName) {
        this.shelterName= shelterName;
    }
    public String getShelterName() {
        return this.shelterName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return this.gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return this.address;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public int getCapacity() {
        return this.capacity;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLongitude() {
        return this.longitude;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
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

}





