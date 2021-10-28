package AgileCinemas;

import java.util.Scanner;

public class StaffInterface {

    private CinemaStaff staff;
    private Manager manager;

    public StaffInterface() {
        this.staff = null;
        this.manager = null;
    }

    // Getters
    public CinemaStaff getStaff() { return this.staff; }
    public Manager getManager() { return this.manager; }

    /*
     * Staff Interface (Used by both managers and normal staff)
     */
    public boolean runStaffInterface(){
        welcomeScreen();
        // Log in
        if (!logIn()) {
            // If not logged in, exit
            exitScreen();
        }

        // Receive reports (.csv or .txt)
        // Choose action: manage movies, manage sessions, add gift cards, (manage staff)

        return true;
    }

    /**
     * Welcome screen for staff interface
     * @return true
    */
    public static boolean welcomeScreen() {
        System.out.println("=========================================================");
        System.out.println("|       Welcome to the AGILE Cinemas staff system       |");
        System.out.println("=========================================================");
        return true;
    }

    /**
     * Exit screen for staff interface
     * @return true
    */
    public static boolean exitScreen() {
        System.out.println("==========================================================");
        System.out.println("|   Thank you for using the AGILE Cinemas staff system   |");
        System.out.println("==========================================================");
        return true;
    }

    /**
     * Allows the staff user enter their ID to log in
     * @return true if logged in successfully, returns false otherwise
    */
    public boolean logIn() {
        System.out.println("Please enter your 4 digit staff ID:");
        Scanner userIn = new Scanner(System.in);
        String id = userIn.nextLine();
        // Check user entered an integer value
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("That staff ID is invalid. You must enter a 4 digit number.");
            return false;
        }
        // Check ID is valid
        if (Crud.is_staffID_valid(id)) {
            // Check staff is active
            if (Crud.isStaffActive(id)) {
                // Check if normal staff or manager
                if (Crud.isManager(id)) {
                    this.manager = new Manager(id);
                } else {
                    this.staff = new CinemaStaff(id);
                }
                System.out.println("Welcome! You have successfully logged in.");
                return true; // Successfully logged in
            } else {
                System.out.println("A manager has deemed your status inactive. You cannot enter the staff system.");
                return false;
            }
        } else {
            System.out.println("That staff ID is invalid.");
            return false;
        }
    }

}
