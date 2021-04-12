package flightplanner.entities;

public class Airport {
    private final int ID;
    private final String name;
    private final String fullName;
    public Airport(int ID, String name, String fullName){
        this.ID = ID;
        this.name = name;
        this.fullName = fullName;
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

    public String toString() {
        return fullName;
    }
}
