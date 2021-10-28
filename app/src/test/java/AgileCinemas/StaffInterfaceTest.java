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

    // /** 
    //  * runStaffInterface() test
    //  *   staffInterfaceCanRun - check interface has a run method and prints out correct message
    // */
    // @Test 
    // public void staffInterfaceCanRun() {
    //     StaffInterface test = new StaffInterface();
    //     assertTrue(test.runStaffInterface());
    // }

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


}