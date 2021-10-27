package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CinemaTest {
    
    /** 
     * isStaff() test
     *   enterNormalUser - User indicates that they are a normal customer by not entering the staff code, should return false
     *   enterStaffUser - User indicates they are staff by entering the staff code, should return true
    */
    @Test
    public void enterNormalUser() {
        Cinema test = new Cinema();
        String input = "hello";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(test.isStaff());
    }

    @Test
    public void enterStaffUser() {
        Cinema test = new Cinema();
        String input = "12345";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertTrue(test.isStaff());
    }
}
