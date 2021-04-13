package flightplanner.entities;

public class Booking {
    private int ID;
    private Passenger passenger;
    private Person customer;
    private Flight flight;
    private Seat seat;
    private int price;
    private String billingAddress;
    private boolean paymentMade = false;
    public Booking(int id, Passenger passenger, Person customer, Flight flight, Seat seat, int price, String billingAddress, boolean paymentMade){
        this.ID = id;
        this.passenger = passenger;
        this.customer = customer;
        this.flight = flight;
        this.seat = seat;
        this.price = price;
        this.billingAddress = billingAddress;
        this.paymentMade = paymentMade;
    }
    public Booking(int id, Passenger passenger, Person customer, Flight flight, Seat seat, int price, String billingAddress){
        this.ID = id;
        this.passenger = passenger;
        this.customer = customer;
        this.flight = flight;
        this.seat = seat;
        this.price = price;
        this.billingAddress = billingAddress;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Person getCustomer() {
        return customer;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public int getPrice() {
        return price;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public boolean isPaymentMade() {
        return paymentMade;
    }

    public void setPaymentMade(boolean paymentMade) {
        this.paymentMade = paymentMade;
    }

    public String toString() {
        return "Farþegi: " + passenger.getFirstName() + " " + passenger.getLastName() + "Flug: " + flight.getFlightNo() + ", " + flight.getDeparture().getName() + " -> " + flight.getArrival().getName();
    }
}
