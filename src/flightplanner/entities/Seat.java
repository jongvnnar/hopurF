package flightplanner.entities;

public class Seat {
    private String seatNumber;
    private boolean isBooked;
    public Seat(String seatNumber, boolean isBooked){
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String toString() {
        return "Seat{" +
                "seatNumber='" + seatNumber + '\'' +
                ", isBooked=" + isBooked +
                '}';
    }
}
