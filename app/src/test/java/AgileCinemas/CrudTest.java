package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CrudTest {
    // TODO TEST:
        // TODO Check Credit Card Provided is Valid
    public void is_creditcardValid() {
        assertTrue(Crud.is_creditCard_valid("Charles", "40691"));
    }
        // TODO Check Gift Card Provided is Valid
    public void is_giftCardExists(){
        assertTrue(Crud.is_giftCard_exist("1000000000000000GC"));
    }
        // TODO Check Gift Card Provided is Redeemable
    public void is_giftCardRedeemable(){
        assertTrue(Crud.is_giftCard_redeemable("1000000000000000GC"));
        assertFalse(Crud.is_giftCard_redeemable("1000000000000001GC"));
    }

        // TODO Check if Customer has CREDIT CARD Stored
        // TODO INSERT CREDIT CARD Details TO CUSTOMER
        // TODO INSERT TRANSACTION DATA
        // TODO Return Last Transaction ID - i.e ticket_id
        // TODO Alter Viewings Table Based On Transaction (Seats Available)
        // TODO Check if Staff ID is Valid
        // TODO Check if Staff ID is Active
        // TODO Check if Staff IS is Manager
        // TODO Create Method to Insert New Viewings


    /** 
     * checkUsernameExist() test
     *   usernameExist - given username exists in db, should return true
     *   usernameNotExist - given username does not exist in db, should return false
    */
    @Test
    public void usernameExist() {
        assertTrue(Crud.checkUsernameExist("rachela"));
    }

    @Test
    public void usernameNotExist() {
        assertFalse(Crud.checkUsernameExist("guestuser"));
    }

    /** 
     * checkPasswordWithUsername() test
     *   passwordCorrect - given username exists in db with corresponding password, should return true
     *   passwordIncorrect - given username exists in db but username is incorrect, should return false
     *   passwordAnotherUser - given username and password correspond to different users, should return false
    */
    @Test
    public void passwordCorrect() {
        assertTrue(Crud.checkPasswordWithUsername("rachela", "purple"));
    }

    @Test
    public void passwordIncorrect() {
        assertFalse(Crud.checkPasswordWithUsername("rachela", "badpassword"));
    }

    @Test
    public void passwordAnotherUser() {
        assertFalse(Crud.checkPasswordWithUsername("rachela", "blue"));
    }

}
