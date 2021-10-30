package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StaffInterfaceTest {

    /** 
     * StaffInterface() test
     *   staffInterfaceCanRun - check interface has a constructor
    */
    @Test 
    public void staffInterfaceConstructor() {
        assertNotNull(new StaffInterface());
    }

    /** 
     * welcomeScreen() test
     *   welcomeScreenTest: Check staff interface has a welcome message
    */
    @Test
    public void welcomeScreenTest() {
        assertTrue(StaffInterface.welcomeScreen());
    }

    /** 
     * exitScreen() test
     *   exitScreenTest: Check staff interface has an exit message
    */
    @Test
    public void exitScreenTest() {
        assertTrue(StaffInterface.exitScreen());
    }

    /** 
     * logIn() test
     *   logInGibberish: Check gibberish non-integer input is handled; should leave staff and manager null and return false
     *   logInInvalidId: Check user cannot log in with an invalid ID that is not in the database; should leave staff and manager null and return false
     *   logInInactive: Check user cannot log in when they have been marked inactive in the database; should leave staff and manager null and return false
     *   logInStaff: Check normal staff that is active can log in; should create CinemaStaff object, leave manager null and return true
     *   logInManager: Check manager that is active can log in; should create Manager object, leave staff null and return true
    */
    @Test
    public void logInGibberish() {
        StaffInterface test = new StaffInterface();
        String input = "jfkd";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(test.logIn());
        assertNull(test.getStaff());
        assertNull(test.getManager());
    }

    @Test
    public void logInInvalidId() {
        StaffInterface test = new StaffInterface();
        String input = "5989";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(test.logIn());
        assertNull(test.getStaff());
        assertNull(test.getManager());
    }

    @Test
    public void logInInactive() {
        StaffInterface test = new StaffInterface();
        String input = "1555";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(test.logIn());
        assertNull(test.getStaff());
        assertNull(test.getManager());
    }

    @Test
    public void logInStaff() {
        StaffInterface test = new StaffInterface();
        String input = "1234";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertTrue(test.logIn());
        assertNotNull(test.getStaff());
        assertNull(test.getManager());
    }

    @Test
    public void logInManager() {
        StaffInterface test = new StaffInterface();
        String input = "1636";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertTrue(test.logIn());
        assertNull(test.getStaff());
        assertNotNull(test.getManager());
    }

    /** 
     * chooseAction() test
     *   chooseAddMovie: Check 1 is returned when staff/manager enters 1 to indicate they wish to add a movie
     *   chooseDeleteMovie: Check 2 is returned when staff/manager enters 2 to indicate they wish to delete a movie
     *   chooseModifyMovie: Check 3 is returned when staff/manager enters 3 to indicate they wish to modify a movie
     *   chooseAddSession: Check 4 is returned when staff/manager enters 4 to indicate they wish to add a movie session
     *   chooseEnterGiftCard: Check 5 is returned when staff/manager enters 5 to indicate they wish to add a gift card into the database
     *   chooseAddStaff: Check 6 is returned when a manager enters 6 to indicate they wish to add a staff member
     *   chooseRemoveStaff: Check 7 is returned when a manager enters 7 to indicate they wish to remove a staff member
     *   notManagerChooseAddStaff: Check " " is returned when a non-manager enters 6
     *   notManagerRemoveStaff: Check " " is returned when a non-manager enters 7
     *   chooseExit: Check input is returned when staff/manager enters any other key to indicate they wish to exit the system
    */
    @Test
    public void chooseAddMovie() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        test.setManager(new Manager("1636"));
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("1", test.chooseAction());
    }

    @Test
    public void chooseDeleteMovie() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        test.setManager(new Manager("1636"));
        String input = "2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("2", test.chooseAction());
    }

    @Test
    public void chooseModifyMovie() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        test.setManager(new Manager("1636"));
        String input = "3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("3", test.chooseAction());
    }

    @Test
    public void chooseAddSession() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        test.setManager(new Manager("1636"));
        String input = "4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("4", test.chooseAction());
    }

    @Test
    public void chooseEnterGiftCard() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        test.setManager(new Manager("1636"));
        String input = "5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("5", test.chooseAction());
    }

    @Test
    public void chooseAddStaff() {
        StaffInterface test = new StaffInterface();
        test.setManager(new Manager("1636"));
        String input = "6";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("6", test.chooseAction());
    }

    @Test
    public void chooseRemoveStaff() {
        StaffInterface test = new StaffInterface();
        test.setManager(new Manager("1636"));
        String input = "7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("7", test.chooseAction());
    }

    @Test
    public void notManagerChooseAddStaff() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        String input = "6";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(" ", test.chooseAction());
    }

    
    @Test
    public void notManagerRemoveStaff() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        String input = "7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(" ", test.chooseAction());
    }
    

    @Test
    public void chooseExit() {
        StaffInterface test = new StaffInterface();
        test.setStaff(new CinemaStaff("1234"));
        test.setManager(new Manager("1636"));
        String input = "fjdkslfjd";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals("fjdkslfjd", test.chooseAction());
    }


}