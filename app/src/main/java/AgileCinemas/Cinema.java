package AgileCinemas;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import org.checkerframework.checker.units.qual.C;
import javax.xml.transform.Source;

public class Cinema{
    //Confirm the identity of the current user.
    /**
     * prompt user to choose which system to login
     * @return number 1 represents the customer interface, number 2 represents the staff interface
     */
    public int displayOptions(){
            //Initialize the Scanner object.
            Scanner userIn = new Scanner(System.in);
            //Display Options
            System.out.println("Normal User Please Enter 1.\nStaff User Please Enter 2.");
        return userIn.nextInt();
    }

    //Check Option
    public boolean isStaff(){
        int userInput = displayOptions();
        //Check if valid input
        if(userInput == 1 || userInput == 2){
            //return true if user is a staff.
            return userInput != 1;
        }
        //Input is neither 1 nor 2, take the input from user again.
        System.out.println("Invalid input, please try again.");
        return isStaff();
    }




    //Since staff and customer all need to log in, will use the same method.
    //Login Module, it will ask user if they want to try again when username not match.
    public boolean userLogin(){
        Scanner userInput = new Scanner(System.in);
        //Ask for username input
        System.out.println("Please enter your username.");
        String username = userInput.nextLine();
        //Validate both username and password
        if(Crud.checkUsernameExist(username)){
            //Ask for password input
            System.out.println("Please enter your password.");
            String password = userInput.nextLine();
            //Username Valid, check the PIN
            if(Crud.checkPasswordExist(password)){
                //Both valid, logged in successfully.
                return true;
            }
            //Invalid username, ask user if wish to try again, if not, return false.
        }else{
            System.out.println("We can't find your username in our database.\nDo you wish to try again? Y/N ");
            //User wish to try again, call this method again.
            if(userInput.nextLine().equalsIgnoreCase("y")){return userLogin();}
            //User wish to quit.
            return false;
        };
        //Invalid Password, ask user if wish to try again, if not, return false.
        int counter = 1;
        while(counter<3){
            System.out.println("Invalid Password Please Try again.\nSystem will quit if you enter the wrong password for THREE times.");
            if(Crud.checkPasswordExist(userInput.nextLine())){return true;}
            else{counter++;}
        }
        return false;
    }

    /*
    * Customer Interfaces(Contain both customer and guest)
    */

    public void runCustomerInterface() {
        CustomerInterface CI = new CustomerInterface();
        //User wish to log in
        boolean login = false;
        if (CI.wishToLogin()) {
            login = userLogin();
            System.out.println("Do you wish to book? Y/N");
            Scanner wishbook = new Scanner(System.in);
            String wb = wishbook.nextLine();
            if(wb.equals("Y")){
                CI.book_with_session();
            }else{
                // display movies
            }
        }
        //User wish not to log in, do the operations of the Guest.
        else {
            System.out.println("You are Guest!");

            //........Display information
            //........Ask if want to order
            System.out.println("Sorry, you need to login before booking.\nIf you already have an account with us, please enter 1.\nIf you wish to sign up, please enter 2.");
            Scanner userInput = new Scanner(System.in);
            //User wish to sign up
            if(userInput.nextLine().equals("1")){
                Customer newCustomer = CI.signUp();
                //.....update to database
                //Let user stay logged in
                login = true;
                System.out.println("Do you wish to book? Y/N");
                Scanner wishbook = new Scanner(System.in);
                String wb = wishbook.nextLine();
                if(wb.equals("Y")){
                    CI.book_with_session();
                }else{
                    // display movies
                }
            }
            //User wish to log in
            else if(userInput.nextLine().equals("2")){
                login = userLogin();
                if(login){
                    System.out.println("Do you wish to book? Y/N");
                    Scanner wishbook = new Scanner(System.in);
                    String wb = wishbook.nextLine();
                    if(wb.equals("Y")){
                        CI.book_with_session();
                    }else{
                        // display movies
                    }
                }else{
                    // display movies or ask for login again?
                }
            }
            //Other options
            else{
                System.out.println("Sorry, more features are not available yet.");
                //........
            }
        }

        //User login successfully, do next operations.
        if (login) {
            System.out.println("You have logged in.");
            //..........
            //..........
            //....Do next operations.
        }
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
    //Identify the user. Check if staff or customer.
    public void runCinema(){

        if(isStaff()){
            runStaffInterface();
        }else{
            runCustomerInterface();
        }
    }
}
