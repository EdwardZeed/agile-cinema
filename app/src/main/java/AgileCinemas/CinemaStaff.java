package AgileCinemas;

import java.util.Scanner;

public class CinemaStaff {
    private String id;

    /**
     * Constructor for cinema staff
    */
    public CinemaStaff(String id) {
        this.id = id;
    }

    /**
     * Getter methods
    */
    public String getId() { return this.id; }

    // TODO
    public static boolean insertMovieData() {
        return false;
    }
    
    // TODO
    public static boolean deleteMovieData() {
        return false;
    }
    
    // TODO
    public static boolean modifyMovieData() {
        return false;
    }
    
    /**
     * Main method for adding a session into the database
     * @return true if the session was entered successfully, otherwise false
    */
    public static boolean addSession() {
        System.out.println("Please enter the details of the session you would like to add in the format:");
        System.out.println("<movie ID>,<cinema location>,<weekday>,<time>,<screen size>,<rear seats available>,<middle seats available>,<front seats available>");
        System.out.println("Movie ID and seats available must be positive integers; Time must be in hh:mm format; Screen size is either Bronze, Silver or Gold");
        Scanner userIn = new Scanner(System.in);
        String[] sessionDetails = userIn.nextLine().split(",");
        // Error if wrong length
        if (sessionDetails.length != 8) {
            System.out.println("Error! You entered the wrong number of session details.");
            return false;
        }
        // Check movie ID and seats can be converted to integers
        int movieID = -1;
        int rearSeats = -1;
        int middleSeats = -1;
        int frontSeats = -1;
        try {
            movieID = Integer.parseInt(sessionDetails[0]);
            rearSeats = Integer.parseInt(sessionDetails[5]);
            middleSeats = Integer.parseInt(sessionDetails[6]);
            frontSeats = Integer.parseInt(sessionDetails[7]);
        } catch (NumberFormatException e) {
            System.out.println("Error! You need to give number values for the movie ID and available seats.");
            return false;
        }
        // Insert viewing to database
        if (!Crud.add_newViewing(movieID, sessionDetails[1], sessionDetails[2], sessionDetails[3], sessionDetails[4], rearSeats, middleSeats, frontSeats)) {
            System.out.println("Error! The viewing could not be entered into the database.");
            return false;
        }
        System.out.println("Success! Movie viewing inserted into the database.");
        return true;
    }
    
    public static boolean enterGiftCard() {
        return false;
    }
    
    public static void reportUpcomingShows() {}
    
    public static void reportBookings() {}
}
