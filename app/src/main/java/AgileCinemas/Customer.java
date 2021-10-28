package AgileCinemas;

public class Customer {
    private String username;
    private String password;
    private String creditCardNum;
    private String creditCardName;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.creditCardNum = Crud.prefilled_customer_creditcard(username);
        this.creditCardName = Crud.name_for_cardNumber(this.creditCardNum);
    }

    // Getter methods
    public String getUsername(){ return this.username; }
    public String getPassword(){ return this.password; }
    public String getCreditCardNum(){ return this.creditCardNum; }
    public String getCreditCardName(){ return this.creditCardName; }

    // Set credit card name
    public void setCreditCardName(String cardName) {
        this.creditCardName = cardName;
    }

    // Set credit card number
    public void setCreditCardNum(String cardNum) {
        this.creditCardNum = cardNum;
    }

    // Returns true if customer has saved credit card details, false else
    public boolean hasSavedCreditCard() {
        return (this.getCreditCardName() != null && this.getCreditCardNum() != null);
    }

}
