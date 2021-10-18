package AgileCinemas;

import java.util.ArrayList;

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
     * displaying login interface
     */
    public int displayLogin(){
        return -1;
    }


    public boolean checkUsername(){
        return false;
    }

    public boolean checkPassword(){
        return false;
    }

    /**
     * sign up
     */
    public Customer signUp(){
        return null;
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
