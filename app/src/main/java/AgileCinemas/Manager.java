package AgileCinemas;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

    public static void reportCancelledTransactions() {

        try {

            // Get all cancelled transactions
            String[][] cancelledTransactions = Crud.cancelledTransactions();
            ArrayList<String> cancelledTransactionsList = new ArrayList<>();
            for (int i = 0; i < cancelledTransactions.length; i++) {
                String line = "User: " + cancelledTransactions[i][0] +
                        "\nTime: "  + cancelledTransactions[i][1] +
                        "\nCanceled Reason: " + cancelledTransactions[i][2] + "\n\n";
                cancelledTransactionsList.add(line);
            }

            File managerReport = new File("ManagerReport.txt");
            if (!managerReport.exists()) {
                managerReport.createNewFile();
            }
            FileWriter writer = new FileWriter(managerReport);


            for (String line : cancelledTransactionsList) {
                System.out.println(line);
                writer.write(line);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Canceled Transaction Report generated successfully.");
    }
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