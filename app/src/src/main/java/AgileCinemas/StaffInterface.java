package AgileCinemas;

import java.util.ArrayList;
import java.util.Scanner;

public class StaffInterface {

    private CinemaStaff staff;

    public StaffInterface(){
        this.staff = null;
    }

    /*
     * Staff Interfaces(Contain both manager and normal staff)
     */
    public void runStaffInterface(){
        System.out.println("Welcome to Cinema Management System.");
        //Successfully Logged in
        if(userLogin()){
            //*** Maybe check if staff is manager or not here. ***
            System.out.println("You have Logged in.");
            //.......
            //.......
            //..Do next operations.
        }
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
            // Construct CinemaStaff object to save to StaffInterface
            this.staff = new CinemaStaff(username, password);
            System.out.println(String.format("Welcome back, %s! You are now logged in.", username));
            return true;
        }
        // Right username, wrong password
        else if (usernameExists && !passwordCorrect) {
            for (int i = 1; i <= 3; i++) {
                System.out.println(String.format("Invalid password, please try again. You have %d more attempts before the system exits.", 4 - i));
                password = userInput.nextLine();
                passwordCorrect = Crud.checkPasswordWithUsername(username, password);
                if (passwordCorrect) { // Correct password entered
                    this.staff = new CinemaStaff(username, password);
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

    public int displayOptions(){
        return -1;
    }

    public void doOperation(int operation){

    }


    
}
