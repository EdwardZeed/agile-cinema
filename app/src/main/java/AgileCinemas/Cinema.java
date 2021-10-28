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
     * to enter the staff interface, they must enter the staff code
     */
    public boolean isStaff(){
        //Initialize the Scanner object.
        Scanner userIn = new Scanner(System.in);
        //Display Options
        System.out.println("If you are a staff member, please enter the staff code (12345). If you are a customer, please enter any other key.");
        String userInput = userIn.nextLine();
        //Check if entered staff code
        if(userInput.equals("12345")){
            //return true if user is a staff.
            return true;
        }
        //Input is not staff code, normal user
        return false;
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
