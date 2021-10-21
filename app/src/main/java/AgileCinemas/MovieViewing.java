package AgileCinemas;

public class MovieViewing {
    private Movie movie;
    private String time;
    private String location;
    private String dayOfWeek;
    private int totalSeats;
    private int bookedSeats;
    private int availableSeats;
    private String screenSize;
    //May need duration of movie?

    /** 
     * Constructor for a movie viewing
    */
    public MovieViewing(Movie movie, String dayOfWeek, String time, String location, String screenSize) {
        this.movie = movie;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.location = location;
        this.screenSize = screenSize;
    }

    // TODO: getter methods

    public String getTime(){
        // From Crud
        return time;
    }

    public String getLocation(){
        // From Crud
        return location;
    }

    public String  getScreenSize(){
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
