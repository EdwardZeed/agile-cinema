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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("+ synopsis: " + this.movie.getSynopsis() + "\n");
        sb.append("+ classification: " + this.movie.getClassification() + "\n");
        sb.append("+ release date: " + this.movie.getReleaseDate() + "\n");
        sb.append("+ director: " + this.movie.getDirector() + "\n");
        sb.append("+ cast: " + this.movie.getCast() + "\n");
        sb.append("+ upcoming time: " + this.time + "\n");
        sb.append("+ screen size: " + this.screenSize + "\n");
        return sb.toString();
    }


}
