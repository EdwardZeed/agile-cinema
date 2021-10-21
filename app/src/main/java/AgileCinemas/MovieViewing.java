package AgileCinemas;

public class MovieViewing {
    private Movie movie;
    private String time;
    private String location;
    private String dayOfWeek;
    private int totalSeats;
    private int frontseats;
    private int middleseats;
    private int backseats;
    private int availableSeats;
    private String screenSize;
    //May need duration of movie?

    /** 
     * Constructor for a movie viewing
    */

    public MovieViewing(Movie movie, String dayOfWeek, String time, String location, String screenSize, int backseats, int middleseats,int frontseats) {
        this.movie = movie;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.location = location;
        this.screenSize = screenSize;
    }

    // TODO: getter methods


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public Movie getMovie() {
        return movie;
    }

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

    public int getBackseats() {
        return backseats;
    }

    public int getFrontseats() {
        return frontseats;
    }

    public int getMiddleseats() {
        return middleseats;
    }

    public void addToDatabse(){
        //Upload SQL after booking
    }
}
