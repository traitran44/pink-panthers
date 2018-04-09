package pinkpanthers.pinkshelters.Model;

/**
 * shelter class that hold information about each shelter
 */
@SuppressWarnings("ALL")
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

    /**
     * constructor
     *
     * @param id           shelter id created by database
     * @param shelterName  name of shelter
     * @param capacity     number of beds of shelter
     * @param specialNotes notes about shelter
     * @param latitude     location of shelter
     * @param longitude    location of shelter
     * @param phoneNumber  phone number of shelter
     * @param restrictions gender/age restriction
     * @param address      address of shelter
     */
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

    /**
     * get unique id of shelter
     *
     * @return shelter id
     */
    public int getId() {
        return id;
    }

    /**
     * set shelter id (created by database)
     *
     * @param id shelter
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get current shelter name
     *
     * @return current shelter name
     */
    public String getShelterName() {
        return shelterName;
    }

    /**
     * set new shelter name
     *
     * @param shelterName new shelter name
     */
    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    /**
     * get current capacity of shelter
     *
     * @return current capacity of shelter
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * set new capacity of shelter
     *
     * @param capacity new capacity of shelter
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * get current latitude of shelter
     *
     * @return current latitude of shelter
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * set new latitude of shelter
     *
     * @param latitude new latitude of shelter
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * get new longitude of shelter
     *
     * @return new longitude of shelter
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * set new longitude of shelter
     *
     * @param longitude new longitude of shelter
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * get current phone number of shelter
     *
     * @return current phone number of shelter
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * set new phone number of shelter
     *
     * @param phoneNumber new phone number of shelter
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * get special note of shelter
     *
     * @return current special note of shelter
     */
    public String getSpecialNotes() {
        return specialNotes;
    }

    /**
     * set new special note of shelter
     *
     * @param specialNotes new special note of shelter
     */
    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    /**
     * get current restriction of shelter
     *
     * @return current restriction
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * set new restriction of shelter
     *
     * @param restrictions new restriction of shelter
     */
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * get current address of shelter
     *
     * @return current address of shelter
     */
    public String getAddress() {
        return address;
    }

    /**
     * set new address of shelter
     *
     * @param address new address of shelter
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get updated capacity of shelter
     *
     * @return current updated capacity of shelter
     */
    public int getUpdate_capacity() {
        return update_capacity;
    }

    /**
     * set new update capacity of shelter
     *
     * @param update_capacity current updated capacity of shelter
     */
    public void setUpdate_capacity(int update_capacity) {
        this.update_capacity = update_capacity;
    }

    /**
     * get current occupancy of shelter
     *
     * @return current occupancy of shelter
     */
    public int getOccupancy() {
        return occupancy;
    }

    /**
     * set new occupancy of shelter
     *
     * @param occupancy new occupancy of shelter
     */
    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    /**
     * get current vacancy of shelter
     *
     * @return current vacancy of shelter
     */
    public int getVacancy() {
        return update_capacity - occupancy;
    }

}