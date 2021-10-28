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
        cinemaLocations = Crud.getViewingLocations(); // extract this from viewings
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
                System.out.println("If you wish to cancel and continue as a guest immediately, type 'c'");
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
        String location = userIn.nextLine().toLowerCase();
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
        String dow = userIn.nextLine().toLowerCase();
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

    public String bookSeats(Scanner userIn, MovieViewing choosen_movie){
        System.out.println("Available Front Seats: " + choosen_movie.getFrontseats());
        System.out.println("Available Middle Seats: " + choosen_movie.getMiddleseats());
        System.out.println("Available Back Seats: " + choosen_movie.getBackseats());
        System.out.println("Please select the area where you want to be seated");
        System.out.println("Type 1: Front");
        System.out.println("Type 2: Middle");
        System.out.println("Type 3: Back");
        System.out.println("Type c: Cancel");

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

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats() + "Screen Size: " + movies.get(j).getScreenSize());
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

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats() + "Screen Size: " + movies.get(j).getScreenSize());
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

                System.out.println("ID: " + String.valueOf(j) + " " + movies.get(j).getMovie().getTitle() + " " + movies.get(j).getLocation() + " " + movies.get(j).getDayOfWeek() + " " + movies.get(j).getTime() + "     Available Front Seats: " + movies.get(j).getFrontseats() + "     Available Middle Seats: " + movies.get(j).getMiddleseats() + "     Available Back Seats: " + movies.get(j).getBackseats() + "Screen Size: " + movies.get(j).getScreenSize());
                j += 1;
            }
        }
        return movies;
    }

    public boolean checkHashmapSize(Scanner userIn, Map<Integer,MovieViewing> m){
        if (m.size() == 0) {
            System.out.println("Sorry, No such movie exists");
            System.out.println("If you want to try again, please type '1'");
            System.out.println("If you want to cancel, please type 'c'");

            String option = userIn.nextLine();
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

    public int printoutfilter_inbook(Scanner userIn, Map<Integer,MovieViewing> movies){
        System.out.println("You can choose the session you like by typing in the ID ");
        System.out.println("If you want to cancel, please type 'c' ");

        while (true) {
            String choose = userIn.nextLine();
            try {
                int choose_int = Integer.parseInt(choose);
                if (choose_int == 0) {
                    return 0;
                }
                String a = new String();
                MovieViewing choosen_movie = movies.get(choose_int);
                a = this.bookSeats(userIn, choosen_movie);
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
                    String type = userIn.nextLine();
                    if(type.equalsIgnoreCase("1")){

                        int ad = book(userIn, choosen_movie,a);
                        if(ad == -2){
                            while(true){
                                System.out.println("Not enough seats. Do you want to try again? Y/N");
                                String ans = userIn.nextLine();
                                if(ans.equalsIgnoreCase("y")){
                                    ad = book(userIn, choosen_movie,a);
                                    if(ad != -2){
                                        break;
                                    }
                                }else{
                                    System.out.println("Transaction Failed");
                                    transaction_status = "fail";
                                    Crud.insertTransaction(customer.getUsername(),0, transaction_status,choosen_movie.getId(),children,concession,adult,"credit card",1,0,a);
                                    return 2;
                                }
                            }
                        }
                        adult = adult + ad;
                        System.out.println("Do you want to book other tickets? Y/N");
                        String again = userIn.nextLine();
                        if(again.equalsIgnoreCase("y")){
                            ;
                        }else{
                            break;
                        }
                    }
                    if(type.equalsIgnoreCase("2")){

                        int chi = book(userIn, choosen_movie,a);
                        if(chi == -2){
                            while(true){
                                System.out.println("Not enough seats. Do you want to try again? Y/N");
                                String ans = userIn.nextLine();
                                if(ans.equalsIgnoreCase("y")){
                                    chi = book(userIn, choosen_movie,a);
                                    if(chi != -2){
                                        break;
                                    }
                                }else{
                                    System.out.println("Transaction Failed");
                                    transaction_status = "fail";
                                    Crud.insertTransaction(customer.getUsername(),0, transaction_status,choosen_movie.getId(),children,concession,adult,"credit card",1,0,a);
                                    return 2;
                                }
                            }
                        }
                        children = children + chi;
                        System.out.println("Do you want to book other tickets? Y/N");
                        String again = userIn.nextLine();
                        if(again.equalsIgnoreCase("y")){
                            ;
                        }else{
                            break;
                        }
                    }
                    if(type.equalsIgnoreCase("3")){

                        int con = book(userIn, choosen_movie,a);
                        if(con == -2){
                            while(true){
                                System.out.println("Not enough seats. Do you want to try again? Y/N");
                                String ans = userIn.nextLine();
                                if(ans.equalsIgnoreCase("y")){
                                    con = book(userIn, choosen_movie,a);
                                    if(con != -2){
                                        break;
                                    }
                                }else{
                                    System.out.println("Transaction Failed");
                                    transaction_status = "fail";
                                    Crud.insertTransaction(customer.getUsername(),0, transaction_status,choosen_movie.getId(),children,concession,adult,"credit card",1,0,a);
                                    return 2;
                                }
                            }
                        }
                        concession = concession + con;
                        System.out.println("Do you want to book other tickets? Y/N");
                        String again = userIn.nextLine();
                        if(again.equalsIgnoreCase("y")){
                            ;
                        }else{
                            break;
                        }
                    }
                    if(type.equalsIgnoreCase("4")){
                        System.out.println("Transaction Failed");
                        transaction_status = "fail";
                        Crud.insertTransaction(customer.getUsername(),0, transaction_status,choosen_movie.getId(),children,concession,adult,"credit card",1,0,a);
                    }
                }
                //upload the booking detail

//                System.out.println("What payment method you want to use?");
//                System.out.println("Type 1: CreditCard");
//                System.out.println("Type 2: gift Card");
//                System.out.println("Type c: Cancel");
//                String ans = userIn.nextLine();

                float cost = 0;
                boolean whether_success = askHowtoPay();

                if(whether_success){
                    cost = Calculate_total_amount(adult,children,concession,choosen_movie);
                    Crud.insertTransaction(customer.getUsername(),cost, transaction_status,choosen_movie.getId(),children,concession,adult,"credit card",0,0,a);
                }else{
                    System.out.println("Transaction Failed.");
                    transaction_status = "fail";
                    Crud.insertTransaction(customer.getUsername(),0, transaction_status,choosen_movie.getId(),children,concession,adult,"credit card",1,0,a);
                    return 2;
                }
                System.out.println("Thanks for booking " + choosen_movie.getMovie().getTitle());
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
        String num = scan.nextLine();
        try {
            int number = Integer.parseInt(num);
            if (area.equalsIgnoreCase("Front")) {
                if (mv.getFrontseats() < number) {
                    return -2;
                } else {
                    Crud.alter_viewing_seats(mv.getId(),number,area);
                    System.out.println("You succussfully add " + number + " tickets");
                    return number;
                }
            } else if (area.equalsIgnoreCase("Middle")) {
                if (mv.getMiddleseats() < number) {
                    return -2;
                } else {
                    Crud.alter_viewing_seats(mv.getId(),number,area);
                    System.out.println("You succussfully add " + number + " tickets");
                    return number;
                }
            } else if (area.equalsIgnoreCase("Back")) {
                if (mv.getBackseats() < number) {
                    return -2;
                } else {
                    Crud.alter_viewing_seats(mv.getId(),number,area);
                    System.out.println("You succussfully add " + number + " tickets");
                    return number;
                }
            }
        } catch (Exception e) {
            if (num.equalsIgnoreCase("c")) {
                System.out.println("The transaction has been canceled");
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
    public boolean askHowtoPay(){
        Scanner scanner = new Scanner(System.in);
        System.out.println( "If you wish to pay by credit card, please enter 1.\n" +
                "If you wish to pay by credit card, please enter 1.\n" +
                "Cancel please enter c.");
        String userInput = scanner.nextLine();
        //Credit Card Payment
        if(userInput.equals("1")){return payWithCreditCard();
            //Gift Card Payment
        }else if(userInput.equals("2")){return payWithGiftCard();
            //Cancel
        }else if(userInput.equalsIgnoreCase("c")){return false;}
        //Invalid Input
        System.out.println("Invalid Input.");
        return askHowtoPay();
    }

     /**
     * Runs payment by credit card
     * @return true if payment successful, else returns false
     */
    public boolean payWithCreditCard() {
        // Scanner object
        Scanner userinput = new Scanner(System.in);
        // Get credit card details
        String cardName = null;
        String cardNum = null;
        // Check if user has saved credit card details
        if (usePreSavedCardDetails()) {
            cardName = this.customer.getCreditCardName();
            cardNum = this.customer.getCreditCardNum();
            // User Input Card Details
        } else {
            // Card Number Input
            cardNum = PasswordField.readPassword("Enter you card number: ");
            // Name on Card Input
            //cardName = inputCreditCardName()
            // Feel free to replace below with above if you want
            System.out.println("Please enter the name on your credit card:");
            cardName = userinput.nextLine();
        }
        // Check if details are valid
        if (Crud.is_creditCard_valid(cardName, cardNum)) {
            // Successful payment, ask if want to save details
            System.out.println("Success! You have purchased your movie tickets!");
            // TODO: actually update transaction and database, etc...
            if (!this.customer.hasSavedCreditCard()) {
                if (askSaveCreditCard(cardName, cardNum)) {
                    Crud.saveCreditCard(cardName, cardNum);
                }
            }
            return true;
        }
        // Invalid credit card details added
        System.out.println("Sorry, those credit card details are invalid");
        return false;
    }
//    /**
//     * TODO: Receives the user's credit card name and number from input
//     * @return credit card number and name
//    */
//    //
//    public String inputCreditCardNumber() {
//        return PasswordField.readPassword("Enter password: ");
//    }

//    /**
//     * Receives the user's credit card name and number from input
//     * @return credit card number and name
//    */
//    public String inputCreditCardName() {
//        Scanner userIn = new Scanner(System.in);
//        System.out.println("Please enter the name on your credit card:");
//        return userIn.nextLine();
//    }

    /**
     * Runs payment by gift card
     * @return true if payment successful, else returns false
     */
    public boolean payWithGiftCard(){
        return true;
    }




    /**
     * TODO: Receives the user's credit card name and number from input
     * @return credit card number and name
    */
    public String inputCreditCardNumber() {
        Scanner userIn = new Scanner(System.in);
        // TODO: hide credit card number with *
        System.out.println("Please enter your credit card number:");
        String cardNum = userIn.nextLine();
        return cardNum;
    }

    /**
     * Receives the user's credit card name and number from input
     * @return credit card number and name
    */
    public String inputCreditCardName() {
        Scanner userIn = new Scanner(System.in);
        System.out.println("Please enter the name on your credit card:");
        String cardName = userIn.nextLine();
        return cardName;
    }

    /**
     * Checks if pre-saved credit card details exist and, if so, checks if user wants to use them
     * @return true if pre-saved details should be used, else returns false
    */
    public boolean usePreSavedCardDetails() {
        // Check if user has saved credit card details
        if (this.customer.hasSavedCreditCard()) {
            System.out.println(String.format("Credit card number: %s\nCredit card name: %s", this.customer.getCreditCardNum(), this.customer.getCreditCardName()));
            System.out.println("Do you wish to proceed with these saved credit card details?  Y/N");
            Scanner userIn = new Scanner(System.in);
            if (userIn.nextLine().toUpperCase().equalsIgnoreCase("Y")) {
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
    public boolean askSaveCreditCard(String cardName, String cardNum) {
        System.out.println("Would you like to save your credit card details for a faster booking process next time?  Y/N");
        Scanner userIn = new Scanner(System.in);
        if (userIn.nextLine().toUpperCase().equalsIgnoreCase("Y")) {
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
        if(MV.getScreenSize().equalsIgnoreCase("Golden")){
            double total_cost = (float) adult * 20 + children * 20 * 0.5 + concession * 20 * 0.75;
            return (float) total_cost;
        }
        return -1;
    }
}