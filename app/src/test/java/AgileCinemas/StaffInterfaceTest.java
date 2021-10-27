package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StaffInterfaceTest {
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
     * StaffInterface() test
     *   staffInterfaceCanRun - check interface has a constructor
    */
    @Test 
    public void staffInterfaceConstructor() {
        assertNotNull(new StaffInterface());
    }

    /** 
     * runStaffInterface() test
     *   staffInterfaceCanRun - check interface has a run method and prints out correct message
    */
    @Test 
    public void staffInterfaceCanRun() {
        StaffInterface test = new StaffInterface();
        assertTrue(test.runStaffInterface());
    }
}