package AgileCinemas;

public class Movie {
    private int id;
    private String title;
    private String synopsis;
    private String classification; // could use an enum
    private String releaseDate;
    private String director;
    private String cast;

    /**
     * Constructor for a Movie
     */
    public Movie(int id, String title, String synopsis, String classification, String releaseDate, String director, String cast) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.classification = classification;
        this.releaseDate = releaseDate;
        this.director = director;
        this.cast = cast;
    }

    /**
     * Getter methods
     */
    public int getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getSynopsis() { return this.synopsis; }
    public String getClassification() { return this.classification; }
    public String getReleaseDate() { return this.releaseDate; }
    public String getDirector() { return this.director; }
    public String getCast() { return this.cast; }

}
