package AgileCinemas;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class CinemaStaff {
    private final String id;

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
        System.out.println("Please enter the gift card number you would like to add in the format: ");
        System.out.println("xxxxxxxxxxxxxxxxGC (x represents a number)");
        System.out.println("type c to cancel");

        Scanner scan = new Scanner(System.in);
        String cardNumber = scan.nextLine();

        if (cardNumber.equalsIgnoreCase("c")){
            return false;
        }
        // check if the input is correct
        if (cardNumber.length() != 18){
            System.out.println("The gift card number must be 18 digits. Would you like to try again? Y/N");
            if (scan.nextLine().equalsIgnoreCase("Y")){
                return enterGiftCard();
            }
            else{
                return false;
            }
        }

        String cardID = cardNumber.substring(0, 16);
        String suffix = cardNumber.substring(16, 18);

        try{
            long cardID_int = Long.valueOf(cardID);
        }catch(Exception e){
            System.out.println("The first 16 digits must be numeric. Would you like to try again? Y/N");
            if (scan.nextLine().toUpperCase().equals("Y")){
                return enterGiftCard();
            }
            else{
                return false;
            }
        }
        if (!suffix.equals("GC")){
            System.out.println("The suffix must be 'GC'. Would you like to try again? Y/N");
            if (scan.nextLine().equalsIgnoreCase("Y")){
                return enterGiftCard();
            }
            else{
                return false;
            }
        }
        //check if the gift card is already in the database
        if (Crud.is_giftCard_exist(cardNumber)){
            System.out.println("This gift card already exist. Would you like to enter a new one? Y/N");
            if (scan.nextLine().equalsIgnoreCase("Y")){
                return enterGiftCard();
            }
            else{
                return false;
            }
        }

        Crud.new_giftCard(cardNumber);
        return true;
    }
    
    /** 
     * Writes to a .csv file with the upcoming movies in the database and their details
    */
    public static void reportUpcomingShows() {
        // Get list of upcoming movies/shows
        ArrayList<MovieViewing> viewings = Crud.getViewings();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (MovieViewing viewing : viewings) {
            boolean alreadyAdded = false;
            for (Movie movie : movies) {
                if (viewing.getMovie().getId() == movie.getId()) {
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) {
                movies.add(viewing.getMovie());
            }
        }
        // Put movie info in .txt format
        ArrayList<String> lines = new ArrayList<String>();
        for (Movie movie : movies) {
            String line = "ID:             " +  Integer.toString(movie.getId()) + "\nTitle:          " + movie.getTitle() + 
                "\nSynopsis:       " + movie.getSynopsis() + "\nClassification: " + movie.getClassification() + "\nRelease date:   " + 
                movie.getReleaseDate() + "\nDirector:       " + movie.getDirector() + "\nCast:           " + movie.getCast() + "\n\n";
            lines.add(line);
        }
        // Write to file
        File moviesFile = new File("movies_report.txt");
        try {
            moviesFile.createNewFile(); // Create file if it doesn't already exist
            FileWriter moviesFileWriter = new FileWriter(moviesFile);
            for (String line : lines) {
                moviesFileWriter.write(line);
            }
            moviesFileWriter.close();
        } catch (IOException e) {
            System.out.println("Error! The upcoming movies report could not be generated.");
            e.printStackTrace();
            return;
        }
        System.out.println("Upcoming movies report successfully generated (AGILE-Cinemas-Reports/movies_report.txt)");
    }
    
    /** 
     * Writes to a .csv file with the upcoming movie sessions with the booking information
    */
    public static void reportBookings() {
        // Get list of upcoming viewings
        ArrayList<MovieViewing> viewings = Crud.getViewings();
        // Put viewing info in .txt format
        ArrayList<String> lines = new ArrayList<String>();
        for (MovieViewing viewing : viewings) {
            String line = "ID: " + viewing.getId() + "\nMovie: " + viewing.getMovie().getTitle() + "\nLocation: " + viewing.getLocation() +
                "\nDate/time: " + viewing.getDayOfWeek() + " " + viewing.getTime() + "\nScreen size: " + viewing.getScreenSize() + 
                "\nSeats booked: " + Integer.toString(viewing.getTotalSeats()) + "\nSeats available: " + Integer.toString(viewing.getAvailableSeats()) + 
                "\n    Front: " + Integer.toString(viewing.getFrontseats()) + " Middle: " + Integer.toString(viewing.getMiddleseats()) + 
                " Rear: " + Integer.toString(viewing.getBackseats()) + "\n\n";
            lines.add(line);
        }
        // Write to file
        File bookingsFile = new File("bookings_report.txt");
        try {
            bookingsFile.createNewFile(); // Create file if it doesn't already exist
            FileWriter bookingsFileWriter = new FileWriter(bookingsFile);
            for (String line : lines) {
                bookingsFileWriter.write(line);
            }
            bookingsFileWriter.close();
        } catch (IOException e) {
            System.out.println("Error! The upcoming session bookings report could not be generated.");
            e.printStackTrace();
            return;
        }
        System.out.println("Upcoming session bookings report successfully generated (AGILE-Cinemas-Reports/bookings_report.txt)");
    }

    public static void main(String[] args) {
        reportBookings();
    }
}
