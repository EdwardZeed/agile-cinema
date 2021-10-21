package AgileCinemas;

import java.util.HashMap;

public class BookingTransaction {
    private int id;
    private Movie movie;
    private int numTickets;
    private String seatsChoice; // could use an enum
    private HashMap<String, Integer> ticketTypes;
    private String location;
    private String time;

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
    
}
