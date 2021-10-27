package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CrudTest {


    
    // TODO TEST:
        // TODO Check Credit Card Provided is Valid
    @Test
    public void is_creditcardValid() {
        assertTrue(Crud.is_creditCard_valid("Charles", "40691"));
    }

        // TODO Check Gift Card Provided is Valid
    @Test
    public void is_giftCardExists(){
        assertTrue(Crud.is_giftCard_exist("1000000000000000GC"));
    }


        // TODO Check if Customer has CREDIT CARD Stored
    @Test
    public void does_CustomerhaveCreditCard(){
        assertFalse(Crud.customerContains_creditCard("Pauly601"));
    }

        // TODO INSERT CREDIT CARD Details TO CUSTOMER
    @Test
    public void saveCreditCardCustomer(){
        assertTrue(Crud.saveCreditCard("rachela", "40691"));
    }

    // TODO Return Last Transaction ID - i.e ticket_id
    @Test
    public void lastTransactionID(){
        assertEquals(0, Crud.last_transactionID("transactions_test"));
    }

    // TODO Check if Staff ID is Valid
    @Test
    public void testStaffIDValidity(){
        assertTrue(Crud.is_staffID_valid("1234"));
    }
    // TODO Check if Staff ID is Active
    @Test
    public void testStaffActive(){
        assertTrue(Crud.isStaffActive("1234"));
        assertFalse(Crud.isStaffActive("1555"));
    }

    // TODO Check if Staff IS is Manager
    @Test
    public void testisManager(){
        assertTrue(Crud.isManager("1636"));
        assertFalse(Crud.isManager("1234"));
    }
    // TODO Check Gift Card Provided is Redeemable
    @Test
    public void is_giftCardbeenRedeemed(){
        assertTrue(Crud.hasGiftCardbeenRedeemed("1000000000000000GC"));
        assertFalse(Crud.hasGiftCardbeenRedeemed("1000000000000001GC"));
    }

    // TODO INSERT TRANSACTION DATA
    @Test
    public void insertnew_transaction(){
        assertTrue(Crud.insertTransaction("Pauly601", 21, "active",
                2, 0, 1, 0, "credit card",
                0, 0, "middle"));
    }



    // TODO Create Method to Insert New Viewings
    @Test //DEBUG
    public void checkViewingAdded(){
        assertTrue(Crud.add_newViewing(2, "Broadway", "Friday", "19:00",
                "Bronze", 15, 15, 15));
    }

    // TODO Alter Viewings Table Based On Transaction (Seats Available)
    @Test //DEBUG
    public void alterViewingSeats(){
        assertTrue(Crud.alter_viewing_seats(1, 3, "back"));
        assertTrue(Crud.alter_viewing_seats(1, 3, "middle"));
        assertTrue(Crud.alter_viewing_seats(1, 3, "front"));

    }


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
