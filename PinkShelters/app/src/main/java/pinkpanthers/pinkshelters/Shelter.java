package pinkpanthers.pinkshelters;

public class Shelter {
    private int id;
    private String shelterName;
    private String capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String specialNotes;
    private String restrictions;
    private String address;
    private int occupancy;
    private int update_capacity;
    private int vacancy;

    Shelter(int id,
            String shelterName,
            String capacity,
            String specialNotes,
            double latitude,
            double longitude,
            String phoneNumber,
            String restrictions,
            String address) {
        this.id = id;
        this.shelterName = shelterName;
        this.specialNotes = specialNotes;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", shelterName='" + shelterName + '\'' +
                ", capacity='" + capacity + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", specialNotes='" + specialNotes + '\'' +
                ", restrictions='" + restrictions + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public int getUpdate_capacity() {
        return update_capacity;
    }

    public void setUpdate_capacity(int update_capacity) {
        this.update_capacity = update_capacity;
    }

    public int getOccupancy() {
        return occupancy;
    }
    public void setOccupancy(int occupancy) {
        this.occupancy=occupancy;
    }

    public int getVacancy() {
        return update_capacity - occupancy;
    }
    public void setVacancy(int vacancy) {this.vacancy = vacancy;}

}