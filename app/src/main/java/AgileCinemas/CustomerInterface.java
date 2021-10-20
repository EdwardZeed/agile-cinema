package AgileCinemas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class CustomerInterface {
    private ArrayList<String> cinemaLocations;
    private ArrayList<Movie> moviesShowing;
    private ArrayList<MovieViewing> viewings;

    /** 
     * Constructor for the Customer Interface
    */
    public CustomerInterface() {
        cinemaLocations = Crud.getViewingLocations();
        moviesShowing = Crud.getMoviesShowing();
        viewings = Crud.getViewings();
    }

    /**
     * Customer login
     */

    //Check if user wish to log in
    public boolean wishToLogin(){
        System.out.println("Do you wish to log in?  Y/N  ");
        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine().equalsIgnoreCase("y");
    }

    /**
     * Sign up and Create Account
     */
    //Sign up Module
    public Customer signUp(){
        Scanner userInput = new Scanner(System.in);
        //Display message
        System.out.println("Thanks for creating account with us.  Please follow the instructions.");
        System.out.println("\n\nPlease enter the username that you want to add.");
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
        return new Customer(usernameIn,passwordIn);
    }

    /**
     * displaying available movies
     */
    public void displayMovies(){

    }

    /** 
     * Getter methods
    */
    public ArrayList<String> getCinemaLocations() { return this.cinemaLocations; }
    public ArrayList<Movie> getMoviesShowing() { return this.moviesShowing; }
    public ArrayList<MovieViewing> getViewings() { return this.viewings; }

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
