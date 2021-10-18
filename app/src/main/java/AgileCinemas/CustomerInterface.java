package AgileCinemas;

import java.util.ArrayList;
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

}
