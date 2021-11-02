package AgileCinemas;

import AgileCinemas.InputMethods.PasswordField;
import AgileCinemas.InputMethods.TimerInput;

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
        //cinemaLocations = Crud.getViewingLocations(); // extract this from viewings
        moviesShowing = null; // extract this from viewings
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

    // Setter for testing
    public void setCustomer(Customer customer) { this.customer = customer; }

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
        // Take customer back to default page after cancelled transaction
        do {
            // View movies
            displayMovies();
            // Book a movie
            boolean wantToBook = askToBook(); // checks if logged in
            if (wantToBook) {
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
        } while (true);


    }

    public static boolean welcomeScreen() {
        System.out.println("=========================================================");
        System.out.println("| Welcome to the online booking system of AGILE Cinemas |");
        System.out.println("| Here you can browse upcoming movies and book tickets. |");
        System.out.println("=========================================================");
        return true;
    }

    public static boolean exitScreen() {
        System.out.println("==================================================================");
        System.out.println("| Thank you for using the online booking system of AGILE Cinemas |");
        System.out.println("|                      Happy movie watching!                     |");
        System.out.println("==================================================================");
        return true;
    }

    /**
     * Customer login
     */
    //Check if user wish to log in
    public boolean wishToLogin() {
        System.out.println("Do you have an existing AGILE Cinemas account you would like to log in with?  Y/N");
        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine().toUpperCase().equalsIgnoreCase("Y");
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
                System.out.println("If you wish to cancel and continue as a guest, type 'c'");
                password = userInput.nextLine();
                // Cancel and continue as guest
                if (password.equals("c")) {
                    return false;
                }
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
            System.out.println("The username already exists.\nDo you wish to try again?  Y/N");
            if (userInput.nextLine().equalsIgnoreCase("Y")) {
                return signUp();
            }
            else {
                System.out.println("Continuing as a guest...");
                return false;
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
    public boolean displayMovies() {
        // new Crud().retrieve_upcoming_sessions();
        for (MovieViewing viewing : this.viewings) {
            // Display title
            System.out.println("\n" + viewing.getMovie().getTitle());
            // Display location
            System.out.println("    Cinema location: " + viewing.getLocation());
            // Display synopsis
            System.out.println("    Synopsis: " + viewing.getMovie().getTitle());
            // Display classification
            System.out.println("    Classification: " + viewing.getMovie().getClassification());
            // Display release date
            System.out.println("    Release date: " + viewing.getMovie().getReleaseDate());
            // Display director
            System.out.println("    Director: " + viewing.getMovie().getDirector());
            // Display cast 
            System.out.println("    Cast: " + viewing.getMovie().getCast());
            // Display upcoming times
            System.out.println("    Day: " + viewing.getDayOfWeek());
            System.out.println("    Time: " + viewing.getTime());
            // Display screen size
            System.out.println("    Screen size: " + viewing.getScreenSize());
            System.out.println();
        }
        return true;
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

    public String checkTimeOut(Scanner scan){
        TimerInput TI = new TimerInput();

        try {
            return TI.getInput(scan).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * book session available to customer
     */
    public boolean book_with_session() {
        System.out.println("Please start booking");
        System.out.println("The following are the movies currently showing.");
        displayMovies();
        // Let the user choose the movie;

        Scanner userIn = new Scanner(System.in);
        Map<Integer, MovieViewing> sessions = this.toHashMap();

        System.out.println("Filter the movies by ScreenSize, Please type in the Screen Size you would like to search 'Bronze, Silver, Gold'");
        String screenSize = checkTimeOut(userIn);
        Map<Integer, MovieViewing> movies_s = this.filter_with_ScreenSize(sessions, screenSize);
        if (!checkHashmapSize(userIn, movies_s)) {
            return false;
        }
        // choose film or go further or cancel
        System.out.println("If you want to filter further. Please press '0' ");
        int test_movies_s = printoutfilter_inbook(userIn, movies_s);
        if(test_movies_s == 1){
            return true;
        }else if(test_movies_s == 2){
            return false;
        }

        System.out.println("Filter the movies by name, Please type in the movie you would like to search");
        String moviename = checkTimeOut(userIn);
        Map<Integer, MovieViewing> movies = this.filter_with_name(movies_s, moviename);
        if (!checkHashmapSize(userIn, movies)) {
            return false;
        }
        // choose film or go further or cancel
        System.out.println("If you want to filter further. Please press '0' ");
        int test_movies = printoutfilter_inbook(userIn, movies);
        if(test_movies == 1){
            return true;
        }else if(test_movies == 2){
            return false;
        }


        //Filter by location
        System.out.println("Filter the movies by location, Please type in the movie you would like to search");
        String location = checkTimeOut(userIn);
        Map<Integer, MovieViewing> movies_l = this.filter_with_location(movies,location);

        // If nothing matches. go back or cancel
        if (!checkHashmapSize(userIn, movies_l)) {
            return false;
        }

        // choose film or go further or cancel
        System.out.println("If you want to filter further. Please press '0' ");
        int test_location = printoutfilter_inbook(userIn, movies_l);
        if(test_location == 1){
            return true;
        }else if(test_location == 2){
            return false;
        }

        //Filter by dayofWeek
        System.out.println("Filter the movies by time, Please type in the movie you would like to search");
        String dow = checkTimeOut(userIn);
        Map<Integer, MovieViewing> movies_dow = this.filter_with_time(movies_l,dow);

        // If nothing matches. go back or cancel
        if (!checkHashmapSize(userIn, movies_dow)) {
            return false;
        }

        // choose film or go further or cancel
        int test_dow = printoutfilter_inbook(userIn, movies_dow);
        if(test_dow == 1){
            return true;
        }else if(test_dow == 2){
            return false;
        }

        return true;
    }

    public String bookSeats(Scanner scan, MovieViewing choosen_movie){
        System.out.println("Available Front Seats: " + choosen_movie.getFrontseats());
        System.out.println("Available Middle Seats: " + choosen_movie.getMiddleseats());
        System.out.println("Available Back Seats: " + choosen_movie.getBackseats());
        System.out.println("Please select the area where you want to be seated");
        System.out.println("Type 1: Front");
        System.out.println("Type 2: Middle");
        System.out.println("Type 3: Back");
        System.out.println("Type c: Cancel");

        while (true) {
            String area = checkTimeOut(scan);
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

    public Map<Integer, MovieViewing> filter_with_ScreenSize(Map<Integer, MovieViewing> m, String ScreenSize) {
        // Let the user choose the movie;
        HashMap<Integer, MovieViewing> movies = new HashMap<Integer, MovieViewing>();
        //  filter by movie name
        int j = 1;
        for (int i = 1; i <= m.size(); i++) {
            if (m.get(i).getScreenSize().toLowerCase().equalsIgnoreCase(ScreenSize)) {

                movies.put(j, m.get(i));

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats() + "Screen Size: " + movies.get(j).getScreenSize());
                j += 1;
            }
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

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats() + "     Screen Size: " + movies.get(j).getScreenSize());
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

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats() + "     Screen Size: " + movies.get(j).getScreenSize());
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

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats() + "     Screen Size: " + movies.get(j).getScreenSize());
                j += 1;
            }
        }
        return movies;
    }

    public boolean checkHashmapSize(Scanner scan, Map<Integer,MovieViewing> m){
        if (m.size() == 0) {
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you want to cancel, please type 'c'");


            String option = checkTimeOut(scan);
            if (option.equals("1")) {
                book_with_session();
            } else {

                System.out.println("Thank you, you will log out");
                //log out
                return false;
            }
        }
        return true;
    }

    public int printoutfilter_inbook(Scanner scan, Map<Integer,MovieViewing> movies){
        System.out.println("You can choose the session you like by typing in the ID ");
        System.out.println("If you want to cancel, please type 'c' ");

        while (true) {
            String choose = checkTimeOut(scan);
            try {
                int choose_int = Integer.parseInt(choose);
                if (choose_int == 0) {
                    return 0;
                }
                String a = new String();
                MovieViewing choosen_movie = movies.get(choose_int);
                a = this.bookSeats(scan, choosen_movie);
                if(a.equalsIgnoreCase("cancel")){
                    return 2;
                }
                int adult = 0;
                int children = 0;
                int concession =0;
                String transaction_status = "active";
                int is_cancelled = 0;
                while (true){
                    System.out.println("What kind of tickets you want to book?");
                    System.out.println("Type 1: Adult ");
                    System.out.println("Type 2: Child ");
                    System.out.println("Type 3: Concession ");
                    System.out.println("Type 4: Cancel ");
                    String type = checkTimeOut(scan);
                    if(type.equalsIgnoreCase("1")){

                        int ad = book(scan, choosen_movie,a);
                        if(ad == -2){
                            while(true){
                                System.out.println("Not enough seats. Do you want to try again? Y/N");
                                String ans = checkTimeOut(scan);
                                if(ans.equalsIgnoreCase("y")){
                                    ad = book(scan, choosen_movie,a);
                                    if(ad != -2){
                                        break;
                                    }
                                }else{
                                    System.out.println("Transaction Failed");
                                    transaction_status = "fail";
                                    Crud.addTransaction(customer.getUsername(),0, transaction_status,choosen_movie.getId(),
                                            children, concession, adult,"credit card",1, a, "User Cancelled");
                                    return 2;
                                }
                            }
                        }
                        adult = adult + ad;
                        System.out.println("Do you want to book other tickets? Y/N");
                        String again = checkTimeOut(scan);
                        if(again.equalsIgnoreCase("y")){
                            ;
                        }else{
                            break;
                        }
                    }
                    if(type.equalsIgnoreCase("2")){

                        int chi = book(scan, choosen_movie,a);
                        if(chi == -2){
                            while(true){
                                System.out.println("Not enough seats. Do you want to try again? Y/N");
                                String ans = checkTimeOut(scan);
                                if(ans.equalsIgnoreCase("y")){
                                    chi = book(scan, choosen_movie,a);
                                    if(chi != -2){
                                        break;
                                    }
                                }else{
                                    System.out.println("Transaction Failed");
                                    transaction_status = "fail";
                                    Crud.addTransaction(customer.getUsername(),0, transaction_status, choosen_movie.getId(),
                                            children, concession, adult,"credit card",1, a, "User Cancelled");
                                    return 2;
                                }
                            }
                        }
                        children = children + chi;
                        System.out.println("Do you want to book other tickets? Y/N");
                        String again = checkTimeOut(scan);
                        if(again.equalsIgnoreCase("y")){
                            ;
                        }else{
                            break;
                        }
                    }
                    if(type.equalsIgnoreCase("3")){

                        int con = book(scan, choosen_movie,a);
                        if(con == -2){
                            while(true){
                                System.out.println("Not enough seats. Do you want to try again? Y/N");
                                String ans = checkTimeOut(scan);
                                if(ans.equalsIgnoreCase("y")){
                                    con = book(scan, choosen_movie,a);
                                    if(con != -2){
                                        break;
                                    }
                                }else{
                                    System.out.println("Transaction Failed");
                                    transaction_status = "fail";
                                    Crud.addTransaction(customer.getUsername(),0, transaction_status, choosen_movie.getId(),
                                            children, concession, adult,"credit card",1,a, "User Cancelled");
                                    return 2;
                                }
                            }
                        }
                        concession = concession + con;
                        System.out.println("Do you want to book other tickets? Y/N");
                        String again = checkTimeOut(scan);
                        if(again.equalsIgnoreCase("y")){
                            ;
                        }else{
                            break;
                        }
                    }
                    if(type.equalsIgnoreCase("4")){
                        System.out.println("Transaction Failed");
                        transaction_status = "fail";
                        Crud.addTransaction(customer.getUsername(),0, transaction_status, choosen_movie.getId(),
                                children, concession, adult,"credit card",1,a, "User Cancelled");
                        return 2;
                    }
                }
                //upload the booking detail
                float cost = 0;
                int whether_success = askHowtoPay(scan);

                if(whether_success == 1){
                    cost = Calculate_total_amount(adult,children,concession,choosen_movie);
                    Crud.addTransaction(customer.getUsername(), cost, transaction_status, choosen_movie.getId(),
                            children, concession, adult,"credit card",0, a, "Not Cancelled");
                }
                else if (whether_success == 2){
                    cost = Calculate_total_amount(adult,children,concession,choosen_movie);
                    Crud.addTransaction(customer.getUsername(), cost, transaction_status, choosen_movie.getId(),
                            children, concession, adult,"gift card",0, a, "Not Cancelled");
                }
                else if (whether_success == -2){
                    System.out.println("Transaction Failed");
                    transaction_status = "failed";
                    Crud.addTransaction(customer.getUsername(),0, transaction_status, choosen_movie.getId(),
                            children, concession, adult,"credit card",1, a, "Card Payment Failed");
                    return 2;
                }
                else{
                    System.out.println("Transaction Failed.");
                    transaction_status = "fail";
                    Crud.addTransaction(customer.getUsername(),0, transaction_status, choosen_movie.getId(),
                            children, concession, adult,"credit card",1, a, "User Cancelled");
                    return 2;
                }

                System.out.println("Thanks for booking " + choosen_movie.getMovie().getTitle());
                System.out.println("Ticket ID: " + String.valueOf(Crud.last_transactionID("transactions") + 1));
                System.out.println("Location: " + choosen_movie.getLocation());
                System.out.println("Time: " + choosen_movie.getDayOfWeek() + "    " + choosen_movie.getTime());
                System.out.println("Area: " + a);
                System.out.println("Adult tickets: " +  adult);
                System.out.println("Children tickets: " +  children);
                System.out.println("Concession tickets: " +  concession);
                System.out.println("Total Cost: " + cost);
                return 1;
            } catch (Exception e) {
                if (choose.equals("c")) {
                    //log out
                    return 2;
                }

                System.out.println("Please enter a valid number");
            }
        }
    }


    public int book(Scanner scan, MovieViewing mv, String area) {
        System.out.println("Please Enter the number you want to book.");
        String num = checkTimeOut(scan);
        try {
            int number = Integer.parseInt(num);
            if (area.equalsIgnoreCase("Front")) {
                if (mv.getFrontseats() < number) {
                    return -2;
                } else {
                    Crud.alter_viewing_seats(mv.getId(),number,area);
                    System.out.println("You succussfully added " + number + " tickets");
                    return number;
                }
            } else if (area.equalsIgnoreCase("Middle")) {
                if (mv.getMiddleseats() < number) {
                    return -2;
                } else {
                    Crud.alter_viewing_seats(mv.getId(),number,area);
                    System.out.println("You succussfully added " + number + " tickets");
                    return number;
                }
            } else if (area.equalsIgnoreCase("Back")) {
                if (mv.getBackseats() < number) {
                    return -2;
                } else {
                    Crud.alter_viewing_seats(mv.getId(),number,area);
                    System.out.println("You successfully added " + number + " tickets");
                    return number;
                }
            }
        } catch (Exception e) {
            if (num.equalsIgnoreCase("c")) {
                System.out.println("The transaction has been cancelled");
                return -1;
            } else {
                book(scan, mv, area);
            }
        }
        return 0;
    }

    /**
     * Ask for payment methods
     * @return true if user paid successfully by any methods, return false if user wish to cancel.
     */
    public int askHowtoPay(Scanner scan){

        System.out.println( "If you wish to pay by credit card, please enter 1.\n" +
                "If you wish to pay by gift card, please enter 2.\n" +
                "Cancel please enter c.");
        String userInput = checkTimeOut(scan);
        //Credit Card Payment
        if(userInput.equals("1")){
            boolean check = this.payWithCreditCard(scan);
            if (!check){
                System.out.println("Would you like to try again? Y/N");
                if (checkTimeOut(scan).equalsIgnoreCase("y")){
                    return askHowtoPay(scan);
                }
                else{
                    return -2;
                }
            }
            return 1;
            //Gift Card Payment
        }else if(userInput.equals("2")){
            boolean check = this.payWithGiftCard(scan);
            if(!check){
                return -2;
            }
            return 2;

            //Cancel
        }else if(userInput.equalsIgnoreCase("c")){return -1;}
        //Invalid Input
        System.out.println("Invalid Input.");
        return askHowtoPay(scan);
    }

     /**
     * Runs payment by credit card
     * @return true if payment successful, else returns false
     */
    public boolean payWithCreditCard(Scanner scan) {
        // Scanner object

        // Get credit card details
        String cardName = null;
        String cardNum = null;
        // Check if user has saved credit card details
        if (usePreSavedCardDetails(scan)) {
            cardName = this.customer.getCreditCardName();
            cardNum = this.customer.getCreditCardNum();
            // User Input Card Details
        } else {
            cardNum = inputCreditCardNumber(scan); // Card Number Input
            cardName = inputCreditCardName(scan); // Name on Card Input
        }
        // Check if details are valid
        if (Crud.is_creditCard_valid(cardName, cardNum)) {
            // Successful payment, ask if want to save details
            System.out.println("Success! You have purchased your movie tickets!");
            if (!this.customer.hasSavedCreditCard()) {
                if (askSaveCreditCard(scan, cardName, cardNum)) {
                    Crud.saveCreditCard(cardName, cardNum);
                }
            }
            return true;
        }
        // Invalid credit card details added
        System.out.println("Sorry, those credit card details are invalid");
        return false;
    }
    
    /**
     * Receives the user's credit card number from input, masked
     * @return credit card number
     */
    public String inputCreditCardNumber(Scanner scan) {
//        return PasswordField.readPassword("Please enter your credit card number:");
        return new TimerInput().get_input_mask(scan, "Please enter your credit card number:");
    }

    public boolean payWithGiftCard(Scanner scan){
        System.out.println("Enter your gift card number: ");
        String cardNumber = checkTimeOut(scan);

        if (!Crud.is_giftCard_exist(cardNumber)){
            System.out.println("Sorry, the card number is invalid");
            System.out.println("Would you like to try again? Y/N");

            String option = checkTimeOut(scan);

            if (option.toUpperCase().equals("Y")){
                payWithGiftCard(scan);
            }
            else{
                return false;
            }
        }

        if (Crud.hasGiftCardbeenRedeemed(cardNumber)){
            System.out.println("Sorry, this gift card has been redeemed");
            System.out.println("Would you like to try another one? Y/N");

            String option = checkTimeOut(scan);
            if (option.toUpperCase().equals("Y")){
                payWithCreditCard(scan);
            }
            else{
                return false;
            }
        }
        Crud.changeGiftCardStatustoRedeemed(cardNumber);
        System.out.println("Success! You have purchased your tickets!");
        return true;
    }

    /**
     * Receives the user's credit card name and number from input
     * @return credit card number and name
    */
    public String inputCreditCardName(Scanner scan) {
        System.out.println("Please enter the name on your credit card:");
        String cardName = checkTimeOut(scan);
        return cardName;
    }

    /**
     * Checks if pre-saved credit card details exist and, if so, checks if user wants to use them
     * @return true if pre-saved details should be used, else returns false
    */
    public boolean usePreSavedCardDetails(Scanner scan) {
        // Check if user has saved credit card details
        if (this.customer.hasSavedCreditCard()) {
            System.out.println(String.format("Credit card number: %s\nCredit card name: %s", this.customer.getCreditCardNum(), this.customer.getCreditCardName()));
            System.out.println("Do you wish to proceed with these saved credit card details?  Y/N");
            Scanner userIn = new Scanner(System.in);
            if (checkTimeOut(scan).equalsIgnoreCase("Y")) {
                // User proceeds with saved details
                return true;
            }
            return false; // User does not want to use saved details
        }
        return false;
    }
    
    /**
     * Asks user if they would like to save their credit card details
     * @param cardName the card name to enter
     * @param cardNum the card number to enter
     * @return true if credit card details were saved, false else
    */
    public boolean askSaveCreditCard(Scanner scan, String cardName, String cardNum) {
        System.out.println("Would you like to save your credit card details for a faster booking process next time?  Y/N");

        if (checkTimeOut(scan).toUpperCase().equalsIgnoreCase("Y")) {
            this.customer.setCreditCardName(cardName);
            this.customer.setCreditCardNum(cardNum);
            return true;
        }
        return false; // details not saved
    }

    /**
     * Calculate total amount of Tickests
     * suppose children*0.5, concession*0.75, adult*1
     * Bronze: $10, Silver: $15, Golden: $20
     */
    public float Calculate_total_amount(int adult, int children, int concession, MovieViewing MV ){

        if(MV.getScreenSize().equalsIgnoreCase("Bronze")){
            double total_cost = (float) adult * 10 + children * 10 * 0.5 + concession * 10 * 0.75;
            return (float) total_cost;
        }
        if(MV.getScreenSize().equalsIgnoreCase("Silver")){
            double total_cost = (float) adult * 15 + children * 15 * 0.5 + concession * 15 * 0.75;
            return (float) total_cost;
        }
        if(MV.getScreenSize().equalsIgnoreCase("Gold")){
            double total_cost = (float) adult * 20 + children * 20 * 0.5 + concession * 20 * 0.75;
            return (float) total_cost;
        }
        return -1;
    }


}