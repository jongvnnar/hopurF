package flightplanner.data;

import flightplanner.controllers.FlightSearchController;
import flightplanner.entities.*;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class FlDataConnection {
    private Connection conn = null;
    private final DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static FlDataConnection instance = null;

    public FlDataConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:flightData.db");
            initializeDatabaseFromScript();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
    }

    public static FlDataConnection getInstance(){
        if(instance == null){
            instance = new FlDataConnection();
        }
        return instance;
    }

    private void getConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:flightData.db");
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    private void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //stolid frá oracle docs.
    private void initializeDatabaseFromScript() throws Exception {
        InputStream scriptStream = null;
            // ApplicationDirectory returns the private read-write sandbox area
            // of the mobile device's file system that this application can access.
            // This is where the database is created
            String dbName = "/flightData.db";

            // Verify whether or not the database exists.
            // If it does, then it has already been initialized
            // and no further actions are required
            File dbFile = new File(dbName);
            if (dbFile.exists())
                return;

            String current = System.getProperty("user.dir");
            // Since the SQL script has been packaged as a resource within
            // the application, the getResourceAsStream method is used
            scriptStream = Thread.currentThread().getContextClassLoader().
                    getResourceAsStream("META-INF/initialize.sql");
            BufferedReader scriptReader = new BufferedReader(new FileReader(current + "/src/flightplanner/data/database.sql"));
            String nextLine;
            StringBuffer nextStatement = new StringBuffer();

            // The while loop iterates over all the lines in the SQL script,
            // assembling them into valid SQL statements and executing them as
            // a terminating semicolon is encountered
            Statement stmt = conn.createStatement();
            while ((nextLine = scriptReader.readLine()) != null) {
                // Skipping blank lines, comments, and COMMIT statements
                if (nextLine.startsWith("REM") ||
                        nextLine.startsWith("COMMIT") ||
                        nextLine.length() < 1)
                    continue;
                nextStatement.append(nextLine);
                if (nextLine.endsWith(";")) {
                    stmt.execute(nextStatement.toString());
                    nextStatement = new StringBuffer();
                }
            }
    }

    public ArrayList<Person> getAllPersons() throws Exception{
        getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PERSON");
        ArrayList<Person> res = new ArrayList<Person>();
        while (rs.next()) {
            res.add(new Person(rs.getInt("id"),rs.getString("firstName"), rs.getString("lastName"), rs.getString("kennitala"),rs.getString("email"),rs.getString("phoneNumber")));
        }
        rs.close();
        closeConnection();
        return res;
    }

    public Flight getFlightById(int id) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT id, flightNo, depart, arrival, departTime, arrivalTime, price FROM Flight WHERE id = ?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        Airport depart = getAirportByName(rs.getString(3));
        Airport arrival = getAirportByName(rs.getString(4));
        Flight ret =  new Flight(rs.getInt(1), rs.getString(2), depart, arrival, LocalDateTime.parse(rs.getString(5),datetimeformatter), LocalDateTime.parse(rs.getString(6), datetimeformatter), getSeatsForFlight(id), rs.getInt(7));
        pstmt.close();
        rs.close();
        closeConnection();
        return ret;
    }

    public ArrayList<Flight> getAllFlights() throws Exception{
        getConnection();
        Statement stmt = conn.createStatement();
        //String whereDate = "";
        String whereDate = " where departTime >= date('now')";
        ResultSet rs = stmt.executeQuery("SELECT id, flightNo, depart, arrival, departTime, arrivalTime, price FROM Flight" + whereDate);
        ArrayList<Flight> res = new ArrayList<Flight>();
        while(rs.next()){
            int flightId = rs.getInt(1);
            Airport depart = getAirportByName(rs.getString(3));
            Airport arrival = getAirportByName(rs.getString(4));
            Flight newFlight =  new Flight(flightId, rs.getString(2), depart, arrival, LocalDateTime.parse(rs.getString(5),datetimeformatter), LocalDateTime.parse(rs.getString(6), datetimeformatter), getSeatsForFlight(flightId), rs.getInt(7));
            res.add(newFlight);
        }
        rs.close();
        closeConnection();
        return res;
    }

    public Airport getAirportByName(String name) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Airport WHERE name = ?");
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        Airport ret = new Airport(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
        pstmt.close();
        rs.close();
        closeConnection();
        return ret;
    }

    public ArrayList<Airport> getAirportCityName(String cityName) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Airport WHERE cityName = ?");
        pstmt.setString(1, cityName);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Airport> res = new ArrayList<Airport>();
        while(rs.next()) {
            res.add(new Airport(rs.getInt("id"), rs.getString("name"), rs.getString("fullName"), rs.getString("cityName")));
        }
        pstmt.close();
        rs.close();
        closeConnection();
        return res;
    }

    public ArrayList<Airport> getAirports() throws Exception{
        getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Airport");
        ArrayList<Airport> res = new ArrayList<Airport>();
        while(rs.next()) {
            res.add(new Airport(rs.getInt("id"), rs.getString("name"), rs.getString("fullName"), rs.getString("cityName")));
        }
        rs.close();
        closeConnection();
        return res;
    }
    public ArrayList<Seat> getSeatsForFlight(int flightId) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT seatNo, isBooked FROM Seats WHERE flight = ?");
        ArrayList<Seat> res = new ArrayList<Seat>();
        pstmt.setInt(1, flightId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            boolean isBooked = (rs.getInt("isBooked") == 1);
            Seat newSeat = new Seat(rs.getString("seatNo"), isBooked);
            res.add(newSeat);
        }
        pstmt.close();
        rs.close();
        closeConnection();
        return res;
    }
    public void createSeatsForFlight(int flightId, int rows, int cols){
        getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Seats (seatNo, flight) VALUES (?, ?)");
            for (int i = 1; i <= rows; i++) {
                for (int j = 0; j < cols; j++) {
                    String seatNo = Integer.toString(i) + (char) (j + 65);
                    pstmt.setString(1, seatNo);
                    pstmt.setInt(2, flightId);
                    pstmt.executeUpdate();
                }
            }
            pstmt.close();
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
        finally{
            closeConnection();
        }
    }
    public void createFlight(Flight fl) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Flight (flightNo, depart, arrival, departTime, arrivalTime, price) VALUES (?, ?, ?, ?, ?, ?)");
        pstmt.setString(1, fl.getFlightNo());
        pstmt.setString(2, fl.getDeparture().getName());
        pstmt.setString(3, fl.getArrival().getName());
        pstmt.setString(4, fl.getDepartureTime().format(datetimeformatter));
        pstmt.setString(5, fl.getArrivalTime().format(datetimeformatter));
        pstmt.setInt(6, fl.getPrice());
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }

    // Bætum í þetta egtir því hvort okkur langar í fleiri filtera.
    // Ath. það er miklu sniðugra að gera þetta með map. Geri á mrg.
    public ArrayList<Flight> getFlightsByFilter(Airport departure, Airport arrival, LocalDate fromDate, LocalDate toDate) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * FROM Flight WHERE "
                + "(depart = ? or ? is null) "
                + "and (arrival = ? or ? is null) "
                + "and (departTime >= ? or ? is null) "
                + "and (departTime <= ? or ? is null)"
                );
        String departString;
        String arriveString;
        String fromString;
        String toString;
        try{
            departString = departure.getName();
        }
        catch(Exception e){
            departString = null;
        }
        try{
            arriveString = arrival.getName();
        }
        catch(Exception e){
            arriveString = null;
        }
        try{
            fromString = fromDate.format(dateFormatter);
        }
        catch(Exception e){
            fromString = null;
        }
        try{
            toString = toDate.format(dateFormatter);
        }
        catch(Exception e){
            toString = null;
        }
        pstmt.setString(1, departString);
        pstmt.setString(2, departString);
        pstmt.setString(3, arriveString);
        pstmt.setString(4, arriveString);
        pstmt.setString(5, fromString);
        pstmt.setString(6, fromString);
        pstmt.setString(7, toString);
        pstmt.setString(8, toString);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Flight> res = new ArrayList<>();
        while(rs.next()){
            int flightId = rs.getInt(1);
            Airport depart = getAirportByName(rs.getString(3));
            Airport arrive = getAirportByName(rs.getString(4));
            Flight newFlight =  new Flight(flightId, rs.getString(2), depart, arrive, LocalDateTime.parse(rs.getString(5),datetimeformatter), LocalDateTime.parse(rs.getString(6), datetimeformatter), getSeatsForFlight(flightId), rs.getInt(7));
            res.add(newFlight);
        }
        pstmt.close();
        rs.close();
        closeConnection();
        return res;
    }
    public Booking getBooking(int id) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Bookings WHERE id = ?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        Passenger passenger = getPassenger(rs.getInt("passenger"));
        Person customer = getPerson(rs.getInt("customer"));
        int flightId = rs.getInt("flight");
        Flight flight = getFlightById(flightId);
        Seat seat = new Seat(rs.getString("seatNo"), true);
        Booking retVal = new Booking(rs.getInt(1),passenger, customer, flight, seat, rs.getInt("price"), rs.getString("billingAddress"), (rs.getInt("paymentMade")==1));
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }

    public Passenger getPassenger(int id)throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Person WHERE id = ?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        Passenger retVal = new Passenger(rs.getInt(1), rs.getString("firstName"), rs.getString("lastName"), rs.getString("kennitala"),rs.getString("email"),rs.getString("phoneNumber"));
        retVal.setWantsFood((rs.getInt("wantsFood") == 1));
        retVal.setExtraLuggage(rs.getString("extraLuggage"));
        retVal.setAllergies(rs.getString("allergies"));
        retVal.setWheelchair(rs.getString("wheelchair").equals("yes"));
        retVal.setHealthIssues(rs.getString("healthIssues"));
        retVal.setInsurance((rs.getInt("insurance")==1));
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }

    public Passenger getPassenger(String kennitala)throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Person WHERE kennitala = ?");
        pstmt.setString(1, kennitala);
        ResultSet rs = pstmt.executeQuery();
        Passenger retVal = new Passenger(rs.getInt(1), rs.getString("firstName"), rs.getString("lastName"), rs.getString("kennitala"),rs.getString("email"),rs.getString("phoneNumber"));
        retVal.setWantsFood((rs.getInt("wantsFood") == 1));
        retVal.setExtraLuggage(rs.getString("extraLuggage"));
        retVal.setAllergies(rs.getString("allergies"));
        retVal.setWheelchair(rs.getString("wheelchair").equals("yes"));
        retVal.setHealthIssues(rs.getString("healthIssues"));
        retVal.setInsurance((rs.getInt("insurance")==1));
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }

    public Person getPerson(int id) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Person WHERE id = ?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        Person retVal = new Person(rs.getInt(1), rs.getString("firstName"), rs.getString("lastName"), rs.getString("kennitala"),rs.getString("email"),rs.getString("phoneNumber"));
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }

    public User getUser(int id) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Person WHERE id = ?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        User retVal = new User(rs.getString("role"),rs.getInt(1), rs.getString("firstName"), rs.getString("lastName"), rs.getString("kennitala"),rs.getString("email"),rs.getString("phoneNumber"));
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }

    public User getUser(String email, String password) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Person WHERE email = ? AND password = ?");
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        User retVal = new User(rs.getString("role"),rs.getInt(1), rs.getString("firstName"), rs.getString("lastName"), rs.getString("kennitala"),rs.getString("email"),rs.getString("phoneNumber"));
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }

    public ArrayList<Booking> getBookings(int userID) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Bookings WHERE customer = ?");
        pstmt.setInt(1, userID);
        ResultSet rs = pstmt.executeQuery();
        //Person customer = getPerson(userID);
        ArrayList<Booking> retVal = new ArrayList<>();
        while(rs.next()){
            Passenger passenger = getPassenger(rs.getInt("passenger"));
            Person customer = getPerson(rs.getInt("customer"));
            int flightId = rs.getInt("flight");
            Flight flight = getFlightById(flightId);
            Seat seat = new Seat(rs.getString("seatNo"), true);
            retVal.add(new Booking(rs.getInt(1),passenger, customer, flight, seat, rs.getInt("price"), rs.getString("billingAddress"), (rs.getInt("paymentMade")==1)));
        }
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }

    //toTest
    public void createBooking(Booking booking) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Bookings (passenger, customer, flight, seatNo, price, billingAddress, paymentMade) VALUES (?, ?, ?, ?, ?, ?, ?)");
        pstmt.setInt(1, booking.getPassenger().getID());
        pstmt.setInt(2, booking.getCustomer().getID());
        pstmt.setInt(3, booking.getFlight().getID());
        pstmt.setString(4, booking.getSeat().getSeatNumber());
        pstmt.setInt(5, booking.getPrice());
        pstmt.setString(6, booking.getBillingAddress());
        int paymentMade = booking.isPaymentMade() ? 1 : 0;
        pstmt.setInt(7, paymentMade);
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }

    //toTest
    public void updateBookingFlight(int id, Flight newFlight) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE Booking SET flight = ? WHERE id = ?");
        pstmt.setInt(1, newFlight.getID());
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }
    //toTest
    public Seat getSeat(int flight, String seatNo) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Seats WHERE flight = ? AND seatNo = ?");
        pstmt.setInt(1, flight);
        pstmt.setString(2, seatNo);
        ResultSet rs = pstmt.executeQuery();
        Seat retVal = new Seat(rs.getString(1), (rs.getInt(2) == 1));
        pstmt.close();
        rs.close();
        closeConnection();
        return retVal;
    }
    //toTest
    public void updateBookingSeat(int id, Seat newSeat) throws Exception{
        getConnection();
        Booking booking = getBooking(id);
        int flightId = booking.getFlight().getID();
        String seatNo = booking.getSeat().getSeatNumber();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE Booking SET seatNo = ? WHERE id = ?");
        pstmt.setString(1, newSeat.getSeatNumber());
        pstmt.setInt(2, id);
        pstmt.close();
        closeConnection();
    }
    //toTest
    public void updateSeat(int flightId, String seatNo, boolean bookOrNot) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("UPDATE Seats SET isBooked = ? WHERE flight = ? AND seatNo = ?");
        int isBooked = bookOrNot ? 1 : 0;
        pstmt.setInt(1, isBooked);
        pstmt.setInt(2, flightId);
        pstmt.setString(3, seatNo);
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }

    public String getPassword(String email) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT (password) FROM Person WHERE email = ?");
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        String retVal = rs.getString("password");
        rs.close();
        pstmt.close();
        closeConnection();
        return retVal;
    }

    //toTest

    public void createPassenger(Passenger passenger) throws Exception{
        getConnection();
        String insertStatement = "INSERT INTO Person "
                + "(firstName, lastName, kennitala, email, phoneNumber, insurance, luggage, healthIssues, wantsFood, extraLuggage, allergies, wheelchair)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(insertStatement);
        int insurance = passenger.isInsurance() ? 1 : 0;
        int luggage = passenger.isLuggage() ? 1 : 0;
        int wantsFood = passenger.isWantsFood() ? 1 : 0;
        String wheelchair = passenger.isWheelchair() ? "yes" : "no";
        pstmt.setString(1, passenger.getFirstName());
        pstmt.setString(2, passenger.getLastName());
        pstmt.setString(3, passenger.getKennitala());
        pstmt.setString(4, passenger.getEmail());
        pstmt.setString(5, passenger.getPhoneNumber());
        pstmt.setInt(6, insurance);
        pstmt.setInt(7, luggage);
        pstmt.setString(8, passenger.getHealthIssues());
        pstmt.setInt(9, wantsFood);
        pstmt.setString(10, passenger.getExtraLuggage());
        pstmt.setString(11, passenger.getAllergies());
        pstmt.setString(12, wheelchair);
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }
    public void updatePassenger(Passenger passenger) throws Exception{
        getConnection();
        String insertStatement = "UPDATE Person "
                + "SET insurance = ?, luggage = ?, healthIssues = ?, wantsFood = ?, extraLuggage = ?, allergies = ?, wheelchair = ?"
                + "WHERE kennitala = ?";
        PreparedStatement pstmt = conn.prepareStatement(insertStatement);
        int insurance = passenger.isInsurance() ? 1 : 0;
        int luggage = passenger.isLuggage() ? 1 : 0;
        int wantsFood = passenger.isWantsFood() ? 1 : 0;
        String wheelchair = passenger.isWheelchair() ? "yes" : "no";
        pstmt.setInt(1, insurance);
        pstmt.setInt(2, luggage);
        pstmt.setString(3, passenger.getHealthIssues());
        pstmt.setInt(4, wantsFood);
        pstmt.setString(5, passenger.getExtraLuggage());
        pstmt.setString(6, passenger.getAllergies());
        pstmt.setString(7, wheelchair);
        pstmt.setString(8, passenger.getKennitala());
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }
    public void createPerson(Person person) throws Exception{
        getConnection();
        String insertStatement = "INSERT INTO Person "
                + "(firstName, lastName, kennitala, email, phoneNumber)"
                + " VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(insertStatement);
        pstmt.setString(1, person.getFirstName());
        pstmt.setString(2, person.getLastName());
        pstmt.setString(3, person.getKennitala());
        pstmt.setString(4, person.getEmail());
        pstmt.setString(5, person.getPhoneNumber());
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }

    public void createUser(User user) throws Exception{
        getConnection();
        String insertStatement = "INSERT INTO Person "
                + "(firstName, lastName, kennitala, email, phoneNumber, role, password)"
                + " VALUES (?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(insertStatement);
        pstmt.setString(1, user.getFirstName());
        pstmt.setString(2, user.getLastName());
        pstmt.setString(3, user.getKennitala());
        pstmt.setString(4, user.getEmail());
        pstmt.setString(5, user.getPhoneNumber());
        pstmt.setString(6, user.getRole());
        pstmt.setString(7, user.getPassword());
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }

    /*

+ updateBookingArrivalTimes(flightId: int, newTime: LocalDateTime): void

+ updateBookingDepartureTimes(flightId: int, newTime: LocalDateTime): void

+ updateUserName(id: int, newName: String): void

+ updateDateOfBirth(id: int, newDate: LocalDate): void

+ updateFood(id: int, newFood: Boolean): void

+ updateFlightNo(flightId: int, newNum: String): void
     */
    public static void main(String[] args) {
        FlDataConnection connection = new FlDataConnection();
        //connection.getAllPersons();
        /*
        for(int i = 1; i < 745; i++){
            connection.createSeatsForFlight(i, 20, 6);
        }
         */
        try {
            System.out.println("Prenta flug með id = 1");
            Flight firstFlight = connection.getFlightById(1);
            System.out.println(firstFlight.toString());
            System.out.println("Prenta sæti þessa flugs");
            for(Seat e: firstFlight.getSeats()){
                System.out.println(e.toString());
            }
        }catch(Exception e){
            System.out.println("fuck");
            System.err.println(e.getMessage());
        }
        try{
            ArrayList<Flight> test = connection.getAllFlights();
            System.out.println("Prenta öll flug");
            for(Flight e: test){
                System.out.println(e.toString());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            ArrayList<Airport> test = connection.getAirports();
            System.out.println("Búa til flug");
            String[] flightNums = {"FI302", "AN202", "BB703", "ON332", "FX208", "LI534", "FN292", "DR893", "BK832", "FI321", "FK982", "BS312"};
            //Keyrir aldrei
            for(int i = 1; i <= 0; i++ ) {
                for (Airport e : test) {
                    for (Airport f : test) {
                        if (e.getName().equals(f.getName())) continue;
                        LocalDateTime departTime = LocalDateTime.of(2021, 5, i, 15, 20);
                        LocalDateTime arrivalTime = LocalDateTime.of(2021, 5, i, 17, 20);
                        Flight newFlight = new Flight(-1, flightNums[(int) (Math.random()*flightNums.length)], e, f, departTime, arrivalTime,null,(600-12*(i-1))/3);
                        connection.createFlight(newFlight);
                        departTime = LocalDateTime.of(2021, 5, i, 12, 20);
                        arrivalTime = LocalDateTime.of(2021, 5, i, 14, 20);
                        Flight newFlight2 = new Flight(-1, flightNums[(int) (Math.random()*flightNums.length)], e, f, departTime, arrivalTime,null,(600-12*(i-1))/3);
                        connection.createFlight(newFlight2);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            ArrayList<Person> test = connection.getAllPersons();
            System.out.println("Prenta allar manneskjur");
            for(Person e: test){
                System.out.println(e.toString());
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        try{
            System.out.println("Booking with id 2");
            Booking test = connection.getBooking(2);
            System.out.println(test.toString());
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        try{
            System.out.println("All bookings for flight id 2");
            ArrayList<Booking> test = connection.getBookings(2);
            for(Booking e: test){
                System.out.println(e.toString());
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}

