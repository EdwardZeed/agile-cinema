package AgileCinemas;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilter {
    @Test
    public void testToHashMap(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        assertNotNull(sessions);
        assertEquals(2, sessions.get(1).getMovie().getId());
    }
/*
    @Test
    public void testFilterWithName(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();


        Map<Integer, MovieViewing> filtered = CI.filter_with_name(sessions,"inside out");
        assertNotNull(filtered);
        assertEquals(5, filtered.size());
    }
*/
    @Test
    public void testFilterWithLocation(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();

        Map<Integer, MovieViewing> filtered = CI.filter_with_location(sessions,"burwood");
        assertNotNull(filtered);
        assertEquals(2, filtered.size());
    }

    @Test
    public void testFilterWithTime(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();

        Map<Integer, MovieViewing> filtered = CI.filter_with_time(sessions,"Monday");
        assertNotNull(filtered);
        assertEquals(2, filtered.size());
    }

    @Test
    public void testComboFilter(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();

        Map<Integer, MovieViewing> filtered1 = CI.filter_with_name(sessions,"There Will Be Blood");
        assertEquals(2, filtered1.size());

        Map<Integer, MovieViewing> filtered2 = CI.filter_with_time(filtered1,"Thursday");
        assertEquals(1, filtered2.size());

        Map<Integer, MovieViewing> filtered3 = CI.filter_with_location(filtered2,"Hornsby");

        assertNotNull(filtered3);
        assertEquals(1, filtered3.size());
    }

    @Test
    public void testbookseats(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        MovieViewing MV = sessions.get(1);
        InputStream stdin1 = new ByteArrayInputStream("1".getBytes());
        InputStream stdin2 = new ByteArrayInputStream("2".getBytes());
        InputStream stdin3 = new ByteArrayInputStream("3".getBytes());
        InputStream stdin4 = new ByteArrayInputStream("c".getBytes());

        Scanner test1 = new Scanner("1");

        System.setIn(stdin1);
        assertEquals("Front", CI.bookSeats(test1, MV));

        Scanner test2 = new Scanner("2");
        System.setIn(stdin2);
        assertEquals("Middle", CI.bookSeats(test2, MV));

        Scanner test3 = new Scanner("3");
        System.setIn(stdin3);
        assertEquals("Back", CI.bookSeats(test3, MV));

        Scanner test4 = new Scanner("c");
        System.setIn(stdin4);
        assertEquals("Cancel", CI.bookSeats(test4, MV));
    }

    @Test
    public void testFilteroutput_inbook(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        InputStream stdin1 = new ByteArrayInputStream("0".getBytes());
        InputStream stdin3 = new ByteArrayInputStream("c".getBytes());

        Scanner test1 = new Scanner("0");

        System.setIn(stdin1);
        assertEquals(0, CI.printoutfilter_inbook(test1, sessions));

        Scanner test2 =  new Scanner("c");
        System.setIn(stdin3);
        assertEquals(2, CI.printoutfilter_inbook(test2, sessions));
    }

    @Test
    public void testcheckmap(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = new HashMap<>();
        InputStream stdin1 = new ByteArrayInputStream("c".getBytes());

        Scanner scan = new Scanner("c");

        System.setIn(stdin1);
        assertFalse(CI.checkHashmapSize(scan, sessions));

        Map<Integer, MovieViewing> sessions_1 = CI.toHashMap();
        assertTrue(CI.checkHashmapSize(scan, sessions_1));
    }

}
