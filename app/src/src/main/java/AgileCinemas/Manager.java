package AgileCinemas;

public class Manager extends CinemaStaff {
    
    public Manager(String username, String password) {
        super(username, password);
    }

    /**
     * add a new manger to database
     * @return
     */
    public void addToDatabase(){

    }

    public boolean addCinemaStaff() {
        return false;
    }

    public boolean removeCinemaStaff() {
        return false;
    }

    public void reportCancelledTransactions() {}
    
}