package AgileCinemas;

public class Customer {
    private String username;
    private String password;
    private CreditCard card;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.card = null;
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

    public CreditCard getCreditCard(){
        return this.card;
    }

    //Sign Up Setting, no need more setter methods.
    public void signUpSet(String username, String password){
        this.username = username;
        this.password = password;
    }

}
