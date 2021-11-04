package AgileCinemas;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CrudTest {
    //TODO Test Movie Method Modification
    @Test
    public void isMovieEdited(){
        assertTrue(Crud.modifyMovie("Inside Out", "Inside Out", "Eleven-year-old Riley moves to San Francisco, leaving behind her life in Minnesota. She and her five core emotions, Fear, Anger, Joy, Disgust and Sadness, struggle to cope with her new life.",
                "G", "June 2015", "Pete Docter", "Amy Poehler, Phyllis Smith, Richard Kind, Mindy Kaling"));
        assertTrue(Crud.modifyMovie("Inside Out", "Inside Out", "Eleven-year-old Riley moves to San Francisco, leaving behind her life in Minnesota. She and her five core emotions, Fear, Anger, Joy, Disgust and Sadness, struggle to cope with her new life.",
                "PG", "June 2015", "Pete Docter", "Amy Poehler, Phyllis Smith, Richard Kind, Mindy Kaling"));
    }

    //TODO Create Test for Method that retrieves booked seats for a viewing id
    @Test
    public void doesRetrieveBookedSeats(){
        assertTrue(Crud.seatsBookedforViewing(2) > -1);
    }

    //TODO Test Retrieval of Failed Transactions
    @Test
    public void canRetrieveFailedTransactions(){
        assertTrue(Crud.cancelledTransactions().length != -1);
        int x = Crud.cancelledTransactions().length;
        Crud.addTransaction("Pauly601", 21, "cancelled",
                2, 0, 1, 1, "gift card", 1,
                "middle", "timeout");
        assertEquals(Crud.cancelledTransactions().length, x+1);
        assertEquals(Crud.cancelledTransactions()[x][0], "Pauly601");
        assertEquals(Crud.cancelledTransactions()[x][2], "timeout");
        Crud.del_row("transactions");

    }


    //TODO Insert Movie and Return Movie ID
    @Test
    public void addMovieRetrieveID(){
        assertTrue(Crud.addNewMovie("Indiana Jones", "The Guy Sheldon Watches", "M",
                "1978", "Stevie Spielberg", "Tony Abott") > 0);
        Crud.del_row("movies");
    }

    //TODO Tests Update Gift Card from not redeemed, to redeemed
    @Test
    public void giftCardHasBeenRedeemed(){
        Crud.new_giftCard("1000001000000002GC");
        assertFalse(Crud.hasGiftCardbeenRedeemed("1000001000000002GC"));
        Crud.changeGiftCardStatustoRedeemed("1000001000000002GC");
        assertTrue(Crud.hasGiftCardbeenRedeemed("1000001000000002GC"));
        Crud.del_row("gift_cards");

    }

    //TODO CRUD Methods to Create, Change Status and Delete Staff Member
    @Test
    public void crudStaff(){
        Crud.addStaff("1985");
        assertTrue(Crud.is_staffID_valid("1985"));
        assertTrue(Crud.isStaffActive("1985"));
        Crud.makeStaffInactive("1985");
        assertFalse(Crud.isStaffActive("1985"));
        Crud.delStaff("1985");
        assertFalse(Crud.is_staffID_valid("1985"));
    }



    //TODO Create Method to Insert New Gift Card to Database
    @Test
    public void insertGiftCard() {
        Crud.new_giftCard("1000001000000002GC");
        assertTrue(Crud.is_giftCard_exist("1000001000000002GC"));
        assertFalse(Crud.hasGiftCardbeenRedeemed("1000001000000002GC"));
        Crud.del_row("gift_cards");
    }
    
    // TODO TEST:
    //TODO Check correct name against card number
    @Test
    public void is_cardNameCorrect(){
        assertEquals("Charles", Crud.name_for_cardNumber("40691"));
    }

    // TODO Check correct Credit Card number is retrieved from Customer DB
    @Test
    public void is_saved_creditCardValid(){
        assertEquals("40691", Crud.prefilled_customer_creditcard("rachela"));
    }

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
    public void addnew_transaction(){
        assertTrue(Crud.addTransaction("Pauly601", 21, "active",
                2, 0, 1, 1, "gift card", 0,
                "middle", "None"));
        Crud.del_row("transactions");
    }

    // TODO Create Method to Insert New Viewings
    @Test //DEBUG
    public void checkViewingAdded(){
        assertTrue(Crud.add_newViewing(2, "Broadway", "Friday", "19:00",
                "Bronze", 15, 15, 15));
        Crud.del_row("viewings");
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
    public void createnewUser(){
        Crud.create_new_user("newtown", "sydney");
        assertTrue(Crud.checkUsernameExist("newtown"));
        assertTrue(Crud.checkPasswordWithUsername("newtown", "sydney"));
        Crud.del_row("customers");
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
