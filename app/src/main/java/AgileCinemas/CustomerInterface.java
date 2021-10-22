package AgileCinemas;

import AgileCinemas.PasswdMask.PasswordField;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

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
    public ArrayList<String> getCinemaLocations() {
        return this.cinemaLocations;
    }

    public ArrayList<Movie> getMoviesShowing() {
        return this.moviesShowing;
    }

    public ArrayList<MovieViewing> getViewings() {
        return this.viewings;
    }
    public Customer getCustomer() { return this.customer; }

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
    public boolean wishToLogin() {
        System.out.println("Do you have an existing AGILE Cinemas account you would like to log in with?  Y/N");
        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine().equalsIgnoreCase("y");
    }

    //Login Module, it will ask user if they want to try again when username not match.
    public boolean userLogin() {
        Scanner userInput = new Scanner(System.in);
        //Ask for username input
        System.out.println("Please enter your username:");
        String username = userInput.nextLine();
        // Ask for password
        String password = PasswordField.readPassword("Enter password: ");

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
            System.out.println("That username and password is incorrect.\nDo you wish to try again?  Y/N");
            if (userInput.nextLine().toUpperCase().equalsIgnoreCase("Y")) {
                userLogin();
            }
            return false; //User wish to quit.
        }
    }

    /**
     * Ask if user would like to sign up
     *
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
    public boolean signUp() {
        Scanner userInput = new Scanner(System.in);
        //Display message
        System.out.println("To create an account, please follow the instructions.");
        System.out.println("\n\nPlease enter the username that you want to add:");
        String usernameIn = userInput.nextLine();
        boolean i = Crud.checkUsernameExist(usernameIn);
        //Username is not unique, ask if user want to try again.

        if (i == true) {
            System.out.println("The username already exists.\nDo you wish to try again?  Y/N.");
            if (userInput.nextLine().equalsIgnoreCase("Y")) {
                return signUp();
            }
        }
        else {
            //Username is valid,ask user Enter the Password.
            System.out.println("Please enter the password.");
            String passwordIn = userInput.nextLine();
            //Both username and password valid, return a Customer object.
            Crud.create_new_user(usernameIn, passwordIn);
            this.customer = new Customer(usernameIn, passwordIn);
        }

        return true;
    }

    /**
     * displaying available movies
     */
    public void displayMovies() {
        new Crud().retrieve_upcoming_sessions();
    }

    /**
     * Only let user book a movie if they are logged
     *
     * @return
     */
    public boolean askToBook() {
        System.out.println("Do you wish to book a movie?  Y/N");
        Scanner wishbook = new Scanner(System.in);
        String wb = wishbook.nextLine();
        // Wishes to book a movie
        if (wb.toUpperCase().equals("Y")) {
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
        } else {
            return false;
        }
    }

    /**
     * book session available to customer
     */
    public boolean book_with_session() {
        System.out.println("Please start booking");
        System.out.println("The following are the movies currently showing.");
        displayMovies();
        // Let the user choose the movie;
        System.out.println("Filter the movies by name, Please type in the movie you would like to search");
        Scanner userIn = new Scanner(System.in);
        String moviename = userIn.nextLine().toLowerCase();
        Map<Integer, MovieViewing> sessions = this.toHashMap();
        Map<Integer, MovieViewing> movies = this.filter_with_name(sessions, moviename);
        // If nothing matches. go back or cancel
        if (movies.size() == 0) {
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you wan to cancel, please type 'c'");
            String option = userIn.nextLine();
            if (option.equals("1")) {
                book_with_session();
            } else {

                System.out.println("Thank you, you will log out");
                //log out
                return false;
            }
        }
        // choose film or go further or cancel
        System.out.println("You can choose the session you like by typing in the ID ");
        System.out.println("If you want to filter further. Please press '0' ");
        System.out.println("If you wan to cancel, please type 'c' ");
        while (true) {
            String choose = userIn.nextLine();
            try {
                int choose_int = Integer.parseInt(choose);
                if (choose_int == 0) {
                    break;
                }
                String a = new String();
                MovieViewing choosen_movie = movies.get(choose_int);
                a = this.bookSeats(choosen_movie);

                //upload the booking detail
                System.out.println("Thanks for booking " + choosen_movie.getMovie().getTitle());
                System.out.println("Location: " + choosen_movie.getLocation());
                System.out.println("Time: " + choosen_movie.getDayOfWeek() + "    " + choosen_movie.getTime());
                System.out.println("Area: " + a);
                return true;
            } catch (Exception e) {
                if (choose.equals("c")) {
                    //log out
                    return false;
                }
                System.out.println("Please enter a valid number");
            }
        }


        //Filter by location
        System.out.println("Filter the movies by location, Please type in the movie you would like to search");
        String location = userIn.nextLine().toLowerCase();
        Map<Integer, MovieViewing> movies_l = this.filter_with_location(movies,location);

        // If nothing matches. go back or cancel
        if (movies_l.size() == 0) {
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you wan to cancel, please type 'c'");
            String option = userIn.nextLine();
            if (option.equals("1")) {
                book_with_session();
            } else {

                System.out.println("Thank you, you will log out");
                //log out
                return false;
            }
        }

        // choose film or go further or cancel
        System.out.println("You can choose the session you like by typing in the ID ");
        System.out.println("If you want to filter further. Please press '0' ");
        System.out.println("If you wan to cancel, please type 'c' ");
        while (true) {
            String choose = userIn.nextLine();
            try {
                int choose_int = Integer.parseInt(choose);
                if (choose_int == 0) {
                    break;
                }
                String a = new String();
                MovieViewing choosen_movie = movies.get(choose_int);
                a = this.bookSeats(choosen_movie);
                //upload the booking detail
                System.out.println("Thanks for booking " + choosen_movie.getMovie().getTitle());
                System.out.println("Location: " + choosen_movie.getLocation());
                System.out.println("Time: " + choosen_movie.getDayOfWeek() + "    " + choosen_movie.getTime());
                System.out.println("Area: " + a);
                return true;
            } catch (Exception e) {
                if (choose.equals("c")) {
                    //log out
                    return false;
                }
                System.out.println("Please enter a valid number");
            }
        }

        //Filter by dayofWeek
        System.out.println("Filter the movies by time, Please type in the movie you would like to search");
        String dow = userIn.nextLine().toLowerCase();
        Map<Integer, MovieViewing> movies_dow = this.filter_with_time(movies_l,dow);

        // If nothing matches. go back or cancel
        if (movies_dow.size() == 0) {
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you wan to cancel, please type 'c'");
            String option = userIn.nextLine();
            if (option.equals("1")) {
                book_with_session();
            } else {

                System.out.println("Thank you, you will log out");
                //log out
                return false;
            }
        }

        // choose film or go further or cancel
        System.out.println("You can choose the session you like by typing in the ID");
        System.out.println("If you wan to cancel, please type 'c'");
        while (true) {
            String choose = userIn.nextLine();
            try {
                int choose_int = Integer.parseInt(choose);
                if (choose_int == 0) {
                    break;
                }
                String a = new String();
                MovieViewing choosen_movie = movies.get(choose_int);
                a = this.bookSeats(choosen_movie);
                //upload the booking detail
                System.out.println("Thanks for booking " + choosen_movie.getMovie().getTitle());
                System.out.println("Location: " + choosen_movie.getLocation());
                System.out.println("Time: " + choosen_movie.getDayOfWeek() + "    " + choosen_movie.getTime());
                System.out.println("Area: " + a);
                return true;
            } catch (Exception e) {
                if (choose.equals("c")) {
                    //log out
                    return false;
                }
                System.out.println("Please enter a valid number");
            }
        }

        return false;
    }

    public String bookSeats(MovieViewing choosen_movie){
        System.out.println("Available Front Seats: " + choosen_movie.getFrontseats());
        System.out.println("Available Middle Seats: " + choosen_movie.getMiddleseats());
        System.out.println("Available Back Seats: " + choosen_movie.getBackseats());
        System.out.println("Please select the area where you want to be seated");
        System.out.println("Type 1: Front");
        System.out.println("Type 2: Middle");
        System.out.println("Type 3: Back");
        System.out.println("Type c: Cancel");
        Scanner userIn = new Scanner(System.in);
        while (true) {
            String area = userIn.nextLine();
            if (area.equalsIgnoreCase("1")) {
                //check enough seats
                return "Front";
            }
            if (area.equalsIgnoreCase("2")) {
                //check enough seats
                return "Middle";
            }
            if (area.equalsIgnoreCase("3")) {
                //check enough seats
                return "Back";
            }
            if (area.equalsIgnoreCase("c")) {
                return "Cancel";
            }

            System.out.println("please enter a valid number ");
        }
    }

    public Map<Integer, MovieViewing> toHashMap() {
        ArrayList<MovieViewing> MV = getViewings();
        HashMap<Integer, MovieViewing> movies = new HashMap<Integer, MovieViewing>();
        for (int i = 1; i <= MV.size(); i++) {
            movies.put(i, MV.get(i - 1));
        }
        return movies;
    }

    public Map<Integer, MovieViewing> filter_with_name(Map<Integer, MovieViewing> m, String moviename) {
        // Let the user choose the movie;
        HashMap<Integer, MovieViewing> movies = new HashMap<Integer, MovieViewing>();
        //  filter by movie name
        int j = 1;
        for (int i = 1; i <= m.size(); i++) {
            if (m.get(i).getMovie().getTitle().toLowerCase().equalsIgnoreCase(moviename)) {

                movies.put(j, m.get(i));

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats());
                j += 1;
            }
        }
        return movies;
    }

    public Map<Integer, MovieViewing> filter_with_location(Map<Integer, MovieViewing> m, String location) {
        // Let the user choose the movie;
        HashMap<Integer, MovieViewing> movies = new HashMap<Integer, MovieViewing>();
        //  filter by movie name
        int j = 1;
        for (int i = 1; i <= m.size(); i++) {
            if (m.get(i).getLocation().toLowerCase().equalsIgnoreCase(location)) {

                movies.put(j, m.get(i));

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats());
                j += 1;
            }
        }
        return movies;
    }

    public Map<Integer, MovieViewing> filter_with_time(Map<Integer, MovieViewing> m, String dow) {
        // Let the user choose the movie;
        HashMap<Integer, MovieViewing> movies = new HashMap<Integer, MovieViewing>();
        //  filter by movie name
        int j = 1;
        for (int i = 1; i <= m.size(); i++) {
            if (m.get(i).getDayOfWeek().toLowerCase().equalsIgnoreCase(dow)) {

                movies.put(j, m.get(i));

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats());
                j += 1;
            }
        }
        return movies;
    }


}