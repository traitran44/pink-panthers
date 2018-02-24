package pinkpanthers.pinkshelters;

public class Shelter {
    private String shelterName;
    private String gender;
    private String address;
    private int capacity;
    private double latitude;
    private double longitude;
    private int phoneNumber;

    Shelter(String shelterName,
            String gender,
            String address,
            int capacity,
            int latitude,
            int longitude,
            int phoneNumber) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.address = address;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
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

    public void setaddress(String address) {
        this.address = address;
    }

    public String getaddress() {

        return this.address;
    }

    public void setcapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getcapacity() {
        return this.capacity;
    }

    ;

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

}





