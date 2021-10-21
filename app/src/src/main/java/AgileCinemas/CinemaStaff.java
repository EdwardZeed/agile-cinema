package AgileCinemas;

public class CinemaStaff {
    private int id; // do we need this??
    private String username;
    private String password;

    /**
     * Constructor for cinema staff
    */
    public CinemaStaff(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = 0; // Call crud method - do we need id??
    }

    /**
     * add a new cinema staff to database
     */
    public void addToDatabase(){

    }

    /**
     * Getter methods
    */
    public int getId() { return this.id; }

    /**
     * Insert a movie into the database
     * @param movie to insert
     * @return whether it was correctly inserted or not
    */
    public boolean insertMovieData(Movie movie) {
        return false;
    }
    
    public boolean deleteMovieData(Movie movie) {
        return false;
    }
    
    public boolean modifyMovieData(Movie movie) {
        return false;
    }
    
    public boolean addWeekShow(MovieViewing show) {
        return false;
    }
    
    public boolean enterGiftCard(GiftCard giftCard) {
        return false;
    }
    
    public void reportUpcomingShows() {}
    
    public void reportBookings() {}
}
