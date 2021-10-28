package AgileCinemas;

public class CinemaStaff {
    private String id;

    /**
     * Constructor for cinema staff
    */
    public CinemaStaff(String id) {
        this.id = id;
    }

    /**
     * Getter methods
    */
    public String getId() { return this.id; }

    /**
     * Add a new cinema staff to database
     */
    public void addToDatabase(){

    }

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
    
    public boolean addSession(MovieViewing show) {
        return false;
    }
    
    public boolean enterGiftCard(GiftCard giftCard) {
        return false;
    }
    
    public void reportUpcomingShows() {}
    
    public void reportBookings() {}
}
