package AgileCinemas;
import java.util.Scanner;

public class Manager extends CinemaStaff {

    public Manager(String id) {
        super(id);
    }

    public static boolean addCinemaStaff() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter the 4 digit Staff ID that you want to add.");
        String idInput = scan.nextLine();
        // Invalid id
        if(!isNumeric(idInput) || idInput.length() != 4){
            System.out.println("Invalid Input.");
            return false;}
        // Check if exist
        if(Crud.is_staffID_valid(idInput)){
            System.out.println("Staff already exist.");
            return false;}
        // Added sucessfully
        Crud.addStaff(idInput);
        System.out.printf("Added successfully.");
        return true;
    }

    public static boolean removeCinemaStaff() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter the 4 digit Staff ID that you want to delete.");
        String idInput = scan.nextLine();
        // Invalid id
        if(!isNumeric(idInput) || idInput.length() != 4){
            System.out.println("Invalid Input.");
            return false;}
        // Check if exist
        if(!Crud.is_staffID_valid(idInput)){
            System.out.println("Staff does not exist.");
            return false;}
        // Added sucessfully
        Crud.delStaff(idInput);
        System.out.printf("Deleted successfully.");
        return true;
    }

    public static void reportCancelledTransactions() {}
    // Helper Methods helps check if staff id input is valid
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}