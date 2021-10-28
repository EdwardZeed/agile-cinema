package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CustomerInterfaceTest {

    /** 
     * welcomeScreen() test - simple output test
    */
    @Test
    public void welcomeScreenTest() {
        assertTrue(CustomerInterface.welcomeScreen());
    }

    /** 
     * exitScreen() test - simple output test
    */
    @Test
    public void exitScreenTest() {
        assertTrue(CustomerInterface.exitScreen());
    }

    /** 
     * askToBook() test
     *   notWantBook: Customer does not want to book, should return false
    */
    @Test
    public void notWantBook() {
        CustomerInterface test = new CustomerInterface();
        String input = "N";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean wantsToBook = test.askToBook();
        assertFalse(wantsToBook);
    }

    /** 
     * userLogin() test
     *   correctLogInFirstGo: Customer immediately enters their correct log in details, should return true
     *   giveUpLogInFirstGo: Customer enters wrong username and password and doens't wish to try again. Should return false
     *   incorrectLoginTwice: Customer enters incorrect username and password, then enters a wrong username and password again and gives up, should return false
     *   correctUsernameNeverCorrectPassword: Customer enters the correct username first try but incorrectly enters password on all 3 attempts, should return false
     *   logInCancel: Customer enters incorrect username and password and then cancels to continue as a guest, should return false
    */
    // Issues - doesn't recognise password first go, but does second go
    @Test
    public void correctLogInFirstGo() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "rachela\rpurple\r";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean loggedIn = testCI.userLogin();
        assertTrue(loggedIn);
        assertNotNull(testCI.getCustomer());
    }

    @Test
    public void giveUpLogInFirstGo() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "guest\n\rbadpassword\n\rN\n\r";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean loggedIn = testCI.userLogin();
        assertFalse(loggedIn);
        assertNull(testCI.getCustomer());
    }

    // Isn't doing what is expected
    @Test
    public void incorrectLoginTwice() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "guest\rbadpassword\rY\rguest2\rbadpassword2\rN\r";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean loggedIn = testCI.userLogin();
        assertFalse(loggedIn);
        assertNull(testCI.getCustomer());
    }

    @Test
    public void correctUsernameNeverCorrectPassword() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "rachela\n\rbadpassword\n\rbadpassword\n\rbadpassword\n\rbadpassword\n\r";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean loggedIn = testCI.userLogin();
        assertFalse(loggedIn);
        assertNull(testCI.getCustomer());
    }

    // Isn't doing what is expected
    @Test
    public void logInCancel() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "badusername\n\rbadpassword\n\rc";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean loggedIn = testCI.userLogin();
        assertFalse(loggedIn);
        assertNull(testCI.getCustomer());
    }

    /** 
     * wishToLogin() test
     *   doesWishToLogin: Customer indicates they do want to log in, should return true
     *   doesNotWishToLogin: Customer indicates they do not want to log in, should return false
    */
    @Test
    public void doesWishToLogin() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "Y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean wishesToLogin = testCI.wishToLogin();
        assertTrue(wishesToLogin);
    }

    @Test
    public void doesNotWishToLogin() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "N";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean wishesToLogin = testCI.wishToLogin();
        assertFalse(wishesToLogin);
    }

    /** 
     * askSignUp() test
     *   doesWishToSignIn: Customer indicates they do want to sign in, should return true
     *   doesNotWishToSignIn: Customer indicates they do not want to sign in, should return false
    */
    // @Test
    // public void doesWishToSignIn() {
    //     CustomerInterface testCI = new CustomerInterface();
    //     String input = "Y";
    //     InputStream in = new ByteArrayInputStream(input.getBytes());
    //     System.setIn(in);
    //     boolean wishesToLogin = testCI.askSignUp();
    //     assertTrue(wishesToLogin);
    // }

    @Test
    public void doesNotWishToSignIn() {
        CustomerInterface testCI = new CustomerInterface();
        String input = "N";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        boolean wishesToLogin = testCI.askSignUp();
        assertFalse(wishesToLogin);
    }

    @Test
    public void displayOptionsTest() {
        CustomerInterface testCI = new CustomerInterface();
        assertTrue(testCI.displayMovies());
    }


}
