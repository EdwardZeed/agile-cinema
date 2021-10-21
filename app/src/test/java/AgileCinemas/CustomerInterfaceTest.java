package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class CustomerInterfaceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpOutStream() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreOutStream() {
        System.setOut(originalOut);
    }

    /** 
     * welcomeScreen() test - simple output test
    */
    @Test
    public void welcomeScreenTest() {
        CustomerInterface.welcomeScreen();
        assertEquals("=========================================================\n| Welcome to the online booking system of AGILE Cinemas |\n| Here you can browse upcoming movies and book tickets. |\n=========================================================\n", outContent.toString());
    }

    /** 
     * exitScreen() test - simple output test
    */
    @Test
    public void exitScreenTest() {
        CustomerInterface.exitScreen();
        assertEquals("==================================================================\n| Thank you for using the online booking system of AGILE Cinemas |\n|                      Happy movie watching!                     |\n==================================================================\n", outContent.toString());
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


    
}
