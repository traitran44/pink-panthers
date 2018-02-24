package pinkpanthers.pinkshelters;
public class Shelter {


    private String shelterName;
    private String restrictions;
    private String address;
    private int capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String specialNote;

    Shelter(String shelterName,
            String restrictions,
            String address,
            int capacity,
            double latitude,
            double longitude,
            String phoneNumber,
            String specialNote)
    {
        this.shelterName = shelterName;
        this.restrictions= restrictions;
        this.address =address;
        this.capacity= capacity;
        this.latitude= latitude;
        this.longitude = longitude;
        this.phoneNumber=phoneNumber;
        this.specialNote = specialNote;
    }

    public void setShelterName(String shelterName) {
        this.shelterName= shelterName;
    }

    public String getShelterName() {
        return this.shelterName;
    }

    public void setRestrictions(String restrictions){
        this.restrictions = restrictions;
    }
    public String getRestrictions() {
        return this.restrictions;
    }
    public void setAddress(String address) {
        this.address=address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public int getCapacity() {return this.capacity;}

    public void setLatitude(double latitude){
        this.latitude=latitude;
    }
    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double longitude){
        this.longitude=longitude;
    }
    public double getLongitude(){
        return this.longitude;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setSpecialNote(String specialNote) {this.specialNote = specialNote;}
    public String getSpecialNote() {return  specialNote;}

}





