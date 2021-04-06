package flightplanner.data;

import java.io.*;
import java.sql.*;

public class FlDataConnection {
    Connection conn = null;

    public FlDataConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:flightData.db");
            initializeDatabaseFromScript();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void closeConnection() {
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
            conn.commit();
    }
    public void searchPerson(){
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PERSON");
            while(rs.next()){
                System.out.print(rs.getString(2) + " ");
                System.out.println(rs.getString(3));
            }
        }
        catch(SQLException e){
            System.out.println("virkadi ekki");
        }
    }
    public static void main(String[] args) {
        FlDataConnection connection = new FlDataConnection();
        connection.searchPerson();
        connection.closeConnection();
    }
}

