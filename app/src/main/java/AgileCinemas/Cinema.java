package AgileCinemas;

import java.sql.SQLOutput;
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
            System.out.println("Normal User Please Enther 1.\nStaff User Please Enter 2.");
            int in = userIn.nextInt();
            return in;
    }
    //Check Option
    public boolean isStaff(){
        int userInput = displayOptions();
        System.out.println("User: "+ userInput);
        //Check if valid input
        if(userInput == 1 || userInput == 2){
            //Customer&Guest
            if (userInput == 1){
                System.out.println("Boolean test1: false");
                return false;}
            System.out.println("Boolean test2: true");
            return true;
        }
        System.out.println("Invalid input, please try again.");
        return isStaff();

    }

    public void runCustomerInterface(){
        System.out.println("Welcome, you are Customer.");
    }
    public void runStaffInterface(){
        System.out.println("Welcome, you are staff.");

    }

    public void runCinema(){

        if(isStaff()){
            runStaffInterface();
        }else{
            runCustomerInterface();
        }
    }
}
