package flightplanner.entities;

public class Airport {
    private final int ID;
    private final String name;
    private final String fullName;
    private final String cityName;
    public Airport(int ID, String name, String fullName, String cityName){
        this.ID = ID;
        this.name = name;
        this.fullName = fullName;
        this.cityName = cityName;
    }

    public int getID() {
        return ID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getName() {
        return name;
    }

    public String getCityName(){
        return cityName;
    }
    public String toString() {
        return cityName;
    }
}
