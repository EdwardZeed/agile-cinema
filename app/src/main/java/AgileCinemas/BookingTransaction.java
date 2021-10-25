package AgileCinemas;

import java.util.HashMap;

public class BookingTransaction {
    private int id;
    private int customerID;
    private double amount;
    private MovieViewing movieViewing;
    private int totalTickets;
    private String seatsChoice; // could use an enum - either front, middle, rear
    private HashMap<String, Integer> ticketTypes;
    private String transactionDateTime;
    private boolean completed; // True if transaction completed, false if cancelled

    public BookingTransaction() {
        // TODO: set other attributes

        ticketTypes = new HashMap<String, Integer>();
        int zero = 0;
        ticketTypes.put("Child", Integer.valueOf(zero));
        ticketTypes.put("Student", Integer.valueOf(zero));
        ticketTypes.put("Adult", Integer.valueOf(zero));
        ticketTypes.put("Senior", Integer.valueOf(zero));
    }

    // TODO: getter methods
    

    // TODO: toString method to print out receipt

}
