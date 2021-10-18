package AgileCinemas;

public class MovieViewing {
    private Movie movie;
    private String time;
    private String location;
    private int totalSeats;
    private int bookedSeats;
    private int availableSeats;
    private ScreenSize screenSize;
    //May need duration of movie?

    /** 
     * Constructor for a movie viewing
    */
    public MovieViewing(Movie movie) {
        this.movie = movie;
    }

    // TODO: getter methods

    public String getTime(){
        // From Crud
        return null;
    }

    public String getLocation(){
        // From Crud
        return location;
    }

    public ScreenSize getScreenSize(){
        // From Crud
        return screenSize;
    }

    public int getTotalSeats(){
        //From Crud
        return totalSeats;
    }

    public int getAvailableSeats() {
        // From Crud
        return availableSeats;
    }

    public int getBookedSeats() {
        //From Crud
        return bookedSeats;
    }

    public void addToDatabse(){
        //Upload SQL after booking
    }
}
