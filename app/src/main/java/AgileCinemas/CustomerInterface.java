package AgileCinemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CustomerInterface {
    private ArrayList<String> cinemaLocations;
    private ArrayList<Movie> moviesShowing;
    private ArrayList<MovieViewing> viewings;
    private Customer customer; // If null, guest. If Customer, logged in.

    /** 
     * Constructor for the Customer Interface
    */
    public CustomerInterface() {
        cinemaLocations = Crud.getViewingLocations();
        moviesShowing = Crud.getMoviesShowing();
        viewings = Crud.getViewings();
        this.customer = null;
    }

    /** 
     * Getter methods
    */
    public ArrayList<String> getCinemaLocations() { return this.cinemaLocations; }
    public ArrayList<Movie> getMoviesShowing() { return this.moviesShowing; }
    public ArrayList<MovieViewing> getViewings() { return this.viewings; }

    /*
    * Customer Interfaces(Contain both customer and guest)
    */
    public void runCustomerInterface() {
        // Welcome screen
        welcomeScreen();
        // Do you have an account? / Wish to log in?
        boolean logInWish = wishToLogin();
        // Do you wish to create an account?
        if (logInWish) {
            userLogin(); // if logged in, this.customer is non-null
        } else {
            askSignUp(); // calls signUp()
        }
        // View movies
        displayMovies();
        // Book a movie
        boolean canBook = askToBook(); // check if logged in
        if (canBook) {
            boolean booked = book_with_session();
            // If successfully booked, exit program
            if (booked) {
                exitScreen();
                return;
            }
        } else {
            exitScreen();
            return;
        }
    }

    public static void welcomeScreen() {
        System.out.println("=========================================================");
        System.out.println("| Welcome to the online booking system of AGILE Cinemas |");
        System.out.println("| Here you can browse upcoming movies and book tickets. |");
        System.out.println("=========================================================");
    }

    public static void exitScreen() {
        System.out.println("==================================================================");
        System.out.println("| Thank you for using the online booking system of AGILE Cinemas |");
        System.out.println("|                      Happy movie watching!                     |");
        System.out.println("==================================================================");
    }

    /**
     * Customer login
     */
    //Check if user wish to log in
    public boolean wishToLogin(){
        System.out.println("Do you have an exiting AGILE Cinemas account you would like to log in with?  Y/N");
        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine().equalsIgnoreCase("y");
    }

    //Login Module, it will ask user if they want to try again when username not match.
    public boolean userLogin(){
        Scanner userInput = new Scanner(System.in);
        //Ask for username input
        System.out.println("Please enter your username:");
        String username = userInput.nextLine();
        // Ask for password
        System.out.println("Please enter your password:");
        String password = userInput.nextLine();
        // Valid username and password together
        boolean usernameExists = Crud.checkUsernameExist(username);
        boolean passwordCorrect = Crud.checkPasswordWithUsername(username, password);
        if (usernameExists && passwordCorrect) {
            // Construct customer object to save to CustomerInterface
            this.customer = new Customer(username, password);
            System.out.println(String.format("Welcome back, %s! You are now logged in.", username));
            return true;
        }
        // Right username, wrong password
        else if (usernameExists && !passwordCorrect) {
            for (int i = 1; i <= 3; i++) {
                System.out.println(String.format("Invalid password, please try again. You have %d more attempts before you will continue as a guest.", 4 - i));
                password = userInput.nextLine();
                passwordCorrect = Crud.checkPasswordWithUsername(username, password);
                if (passwordCorrect) { // Correct password entered
                    this.customer = new Customer(username, password);
                    System.out.println(String.format("Welcome back, %s! You are now logged in.", username));
                    return true;
                }
            }
            return false; // never entered valid log in
        }
        // Wrong username and password
        else {
            System.out.println("That username and password is incorrect.\nDo you wish to try again? Y/N ");
            if(userInput.nextLine().equalsIgnoreCase("y")){
                userLogin();
            }
            return false; //User wish to quit.
        }
    }

    /**
     * Ask if user would like to sign up
     * @return if account was created
    */
    public boolean askSignUp() {
        System.out.println("Would you like to create an AGILE Cinemas account?  Y/N");
        Scanner userInput = new Scanner(System.in);
        String wishSignUp = userInput.nextLine();
        if (wishSignUp.equalsIgnoreCase("Y")) {
            return signUp();
        }
        return false;
    }

    /**
     * Sign up and Create Account
     */
    //Sign up Module
    public boolean signUp(){
        Scanner userInput = new Scanner(System.in);
        //Display message
        System.out.println("Thanks for creating account with us.  Please follow the instructions.");
        System.out.println("\n\nPlease enter the username that you want to add:");
        String usernameIn = userInput.nextLine();
        usernameIn = userInput.nextLine();
        //Username is not unique, ask if user want to try again.
        if(Crud.checkUsernameExist(usernameIn)){
            System.out.println("The username already exists.\nDo you wish to try again?  Y/N.");
            if(userInput.nextLine().equalsIgnoreCase("Y")){return signUp();}
        }
        //Username is valid,ask user Enter the Pin.
        System.out.println("Please enter the password.");
        String passwordIn = userInput.nextLine();
        //Both username and password valid, return a Customer object.
        this.customer = new Customer(usernameIn,passwordIn);
        return true;
    }

    /**
     * displaying available movies
     */
    public void displayMovies(){
        System.out.println("<VIEW MOVIES>");
    }

    /**
     * Only let user book a movie if they are logged
     * @return
    */
    public boolean askToBook() {
        System.out.println("Do you wish to book a movie?  Y/N");
        Scanner wishbook = new Scanner(System.in);
        String wb = wishbook.nextLine();
        // Wishes to book a movie
        if(wb.toUpperCase().equals("Y")){
            // User is logged in - take to book
            if (this.customer != null) {
                return true;
            }
            // User is not logged in
            else {
                System.out.println("You must be logged into an account to book.");
                // Do you have an account? / Wish to log in?
                boolean logInWish = wishToLogin();                
                // Do you wish to create an account?
                if (logInWish) {
                    return userLogin(); // if logged in, this.customer is non-null
                } else {
                    return askSignUp(); // calls signUp()
                }

            }
        }else{
            return false;
        }
    }

    /**
     * book session available to customer
     */
    public boolean book_with_session(){
        System.out.println("Please start booking");
        System.out.println("The following are the movies currently showing.");
        displayMovies();
        System.out.println("Please Choose the movie you would like to see");
        // Let the user choose the movie;
        Scanner userIn = new Scanner(System.in);
        String moviename = userIn.nextLine().toLowerCase();
        HashMap<Integer, Movie> movies = new HashMap<Integer, Movie>();
        //  filter by movie name
        for(int i = 1; i <= getMoviesShowing().size(); i++){
            if(getMoviesShowing().get(i-1).getTitle().toLowerCase().equals(moviename)){
                movies.put(i, getMoviesShowing().get(i));
                System.out.println("ID: " + String.valueOf(i) + " " + getMoviesShowing().get(i).getTitle());
            }
        }
        // If nothing matches. go back or cancel
        if(movies.size() == 0){
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you wan to cancel, please type 'c'");
            String option = userIn.nextLine();
            if(option.equals("1")){
                book_with_session();
            }else{

                System.out.println("Thank you, you will log out");
                //log out
                return false;
            }
        }
        // choose film or go further or cancel
        System.out.println("You can choose the session you like by typing in the ID");
        System.out.println("If you want to filter further. Please press '0'");
        System.out.println("If you wan to cancel, please type 'c'");
        while (true) {
            String choose = userIn.nextLine();
            try {
                int choose_int = Integer.parseInt(choose);
                if(choose_int == 0){
                    break;
                }
                Movie choosen_movie = movies.get(choose_int);
                MovieViewing MV = new MovieViewing(choosen_movie);
                //upload the booking detail
                System.out.println("Thanks for booking " + choosen_movie.getTitle() + " at " + MV.getLocation() + " at " + MV.getTime());
                return true;
            } catch (Exception e) {
                if(choose.equals("c")){
                    //log out
                    return false;
                }
                System.out.println("Please enter a number");
            }
        }

        // Let the user choose the location;
        System.out.println("Please choose The location ");
        String location = userIn.nextLine().toLowerCase();

        for(int i = 1; i <= movies.size() - 1; i++){
            MovieViewing MV = new MovieViewing(movies.get(i));
            if(!location.equals(MV.getLocation().toLowerCase())){
                movies.remove(i);
            }
        }
        // If nothing matches. go back or cancel
        if(movies.size() == 0){
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you wan to cancel, please type 'c'");
            String option = userIn.nextLine();
            if(option.equals("1")){
                book_with_session();
            }else{

                System.out.println("Thank you, you will log out");
                //log out
                return false;
            }
        }
        // choose movie or cancel or go further
        System.out.println("You can choose the session you like by typing in the ID");
        System.out.println("If you want to filter further. Please press '0'");
        System.out.println("If you wan to cancel, please type 'c'");
        while (true) {
            String choose = userIn.nextLine();
            try {
                int choose_int = Integer.parseInt(choose);
                if(choose_int == 0){
                    break;
                }
                Movie choosen_movie = movies.get(choose_int);
                MovieViewing MV = new MovieViewing(choosen_movie);
                //upload the booking detail
                System.out.println("Thanks for booking " + choosen_movie.getTitle() + " at " + MV.getLocation() + " at " + MV.getTime());
                return true;
            } catch (Exception e) {
                if(choose.equals("c")){
                    //log out
                    return false;
                }
                System.out.println("Please enter a valid number");
            }
        }
        // choose time
        System.out.println("Please choose The time ");
        String time = userIn.nextLine().toLowerCase();

        for(int i = 1; i <= movies.size() - 1; i++){
            MovieViewing MV = new MovieViewing(movies.get(i));
            if(!time.equals(MV.getTime().toLowerCase())){
                movies.remove(i);
            }
        }
        if(movies.size() == 0){
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you wan to cancel, please type 'c'");
            String option = userIn.nextLine();
            if(option.equals("1")){
                book_with_session();
            }else{

                System.out.println("Thank you, you will log out");
                //log out
                return false;
            }
        }
        // choose movie or cancel
        System.out.println("You can choose the session you like by typing in the ID");
        System.out.println("If you want to cancel, please type 'c'");
        while (true) {
            String choose = userIn.nextLine();
            try {
                int choose_int = Integer.parseInt(choose);
                Movie choosen_movie = movies.get(choose_int);
                MovieViewing MV = new MovieViewing(choosen_movie);
                //upload the booking detail
                System.out.println("Thanks for booking " + choosen_movie.getTitle() + " at " + MV.getLocation() + " at " + MV.getTime());
                return true;
            } catch (Exception e) {
                if(choose.equals("c")){
                    //log out
                    return false;
                }
                System.out.println("Please enter a valid number");
            }
        }
    }
}
