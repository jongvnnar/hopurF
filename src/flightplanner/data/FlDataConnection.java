package flightplanner.data;

import flightplanner.entities.Airport;
import flightplanner.entities.Flight;
import flightplanner.entities.Person;
import flightplanner.entities.Seat;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FlDataConnection {
    private Connection conn = null;
    private final DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
            res.add(new Person(rs.getInt("id"),rs.getString("firstName"), rs.getString("lastName"), LocalDate.parse(rs.getString("dateOfBirth"),dateFormatter),rs.getString("email"),rs.getString("phoneNumber")));
        }
        rs.close();
        closeConnection();
        return res;
    }

    public Flight getFlightById(int id) throws Exception{
        getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT id, flightNo, depart, arrival, departTime, arrivalTime FROM Flight WHERE id = ?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        Airport depart = getAirportByName(rs.getString(3));
        Airport arrival = getAirportByName(rs.getString(4));
        Flight ret =  new Flight(rs.getInt(1), rs.getString(2), depart, arrival, LocalDateTime.parse(rs.getString(5),datetimeformatter), LocalDateTime.parse(rs.getString(6), datetimeformatter), getSeatsForFlight(id));
        pstmt.close();
        rs.close();
        closeConnection();
        return ret;
    }

    public ArrayList<Flight> getAllFlights() throws Exception{
        getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, flightNo, depart, arrival, departTime, arrivalTime FROM Flight");
        ArrayList<Flight> res = new ArrayList<Flight>();
        Airport depart = getAirportByName(rs.getString(3));
        Airport arrival = getAirportByName(rs.getString(4));
        while(rs.next()){
            int flightId = rs.getInt(1);
            Flight newFlight =  new Flight(flightId, rs.getString(2), depart, arrival, LocalDateTime.parse(rs.getString(5),datetimeformatter), LocalDateTime.parse(rs.getString(6), datetimeformatter), getSeatsForFlight(flightId));
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
        Airport ret = new Airport(rs.getInt(1), rs.getString(2), rs.getString(3));
        pstmt.close();
        rs.close();
        closeConnection();
        return ret;
    }

    public ArrayList<Airport> getAirports() throws Exception{
        getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, name, fullName FROM Airport");
        ArrayList<Airport> res = new ArrayList<Airport>();
        while(rs.next()) {
            res.add(new Airport(rs.getInt("id"), rs.getString("name"), rs.getString("fullName")));
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
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Flights (flightNo, depart, arrival, departTime, arrivalTime) VALUES (?, ?, ?, ?, ?)");
        pstmt.setString(1, fl.getFlightNo());
        pstmt.setString(2, fl.getArrival().getName());
        pstmt.setString(3, fl.getDeparture().getName());
        pstmt.setString(4, fl.getArrivalTime().format(datetimeformatter));
        pstmt.setString(5, fl.getDepartureTime().format(datetimeformatter));
        pstmt.executeUpdate();
        pstmt.close();
        closeConnection();
    }
    public static void main(String[] args) {
        FlDataConnection connection = new FlDataConnection();
        //connection.getAllPersons();
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
            System.out.println("Prenta alla flugvelli");
            for(Airport e: test){
                System.out.println(e.toString());
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
    }
}

