package AgileCinemas;

public class Customer {
    private String username;
    private String password;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * insert a new customer to database
     */
    public void addToDatabase(){

    }

    // TODO: getter methods
    public String getUsername(){
        return this.username;
    }

}
