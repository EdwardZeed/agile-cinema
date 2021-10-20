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

    //Identify the user. Check if staff or customer.
    public void runCinema(){

        if(isStaff()){
            StaffInterface SI = new StaffInterface();
            SI.runStaffInterface();
        }else{
            CustomerInterface CI = new CustomerInterface();
            CI.runCustomerInterface();
        }
    }
}
