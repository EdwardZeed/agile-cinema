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

    @Test
    public void testFilterWithName(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();


        Map<Integer, MovieViewing> filtered = CI.filter_with_name(sessions,"The Social Network");
        assertNotNull(filtered);
//        assertEquals(2, filtered.size());
    }


    @Test
    public void testFilterWithLocation(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();

        Map<Integer, MovieViewing> filtered = CI.filter_with_location(sessions,"burwood");
        assertNotNull(filtered);
//        assertEquals(2, filtered.size());
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

        System.setIn(stdin1);
        Scanner test1 = new Scanner("1");
        assertEquals("Front", CI.bookSeats(test1, MV));


        System.setIn(stdin2);
        Scanner test2 = new Scanner("2");
        assertEquals("Middle", CI.bookSeats(test2, MV));


        System.setIn(stdin3);
        Scanner test3 = new Scanner("3");
        assertEquals("Back", CI.bookSeats(test3, MV));


        System.setIn(stdin4);
        Scanner test4 = new Scanner("c");
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

    @Test
    public void testCalculate_total_amount(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        MovieViewing mv1 = sessions.get(9);
        MovieViewing mv2 = sessions.get(2);
        MovieViewing mv3 = sessions.get(3);
        assertEquals(45, CI.Calculate_total_amount(1,1,1,mv1));
        assertEquals(33.75, CI.Calculate_total_amount(1,1,1,mv2));
        assertEquals(22.5, CI.Calculate_total_amount(1,1,1,mv3));
    }

    @Test
    public void testbook_Front(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        MovieViewing mv = sessions.get(10);
        InputStream stdin1 = new ByteArrayInputStream("1".getBytes());
        InputStream stdin2 = new ByteArrayInputStream("100".getBytes());

        System.setIn(stdin1);
        Scanner test1 = new Scanner(System.in);
        assertEquals(1,CI.book(test1, mv,"Front"));
        Crud.alter_viewing_seats(mv.getId(),-1,"Front");

        System.setIn(stdin2);
        Scanner test2 = new Scanner(System.in);
        assertEquals(-2,CI.book(test2, mv,"Front"));
    }

    @Test
    public void testbook_Middle(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        MovieViewing mv = sessions.get(10);
        InputStream stdin1 = new ByteArrayInputStream("1".getBytes());
        InputStream stdin2 = new ByteArrayInputStream("100".getBytes());

        System.setIn(stdin1);
        Scanner test1 = new Scanner(System.in);
        assertEquals(1,CI.book(test1, mv,"Middle"));
        Crud.alter_viewing_seats(mv.getId(),-1,"Middle");

        System.setIn(stdin2);
        Scanner test2 = new Scanner(System.in);
        assertEquals(-2,CI.book(test2, mv,"Middle"));
    }
    //
    @Test
    public void testbook_Back(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        MovieViewing mv = sessions.get(10);
        InputStream stdin1 = new ByteArrayInputStream("1".getBytes());
        InputStream stdin2 = new ByteArrayInputStream("100".getBytes());

        System.setIn(stdin1);
        Scanner test1 = new Scanner(System.in);
        assertEquals(1,CI.book(test1, mv,"Back"));
        Crud.alter_viewing_seats(mv.getId(),-1,"Back");

        System.setIn(stdin2);
        Scanner test2 = new Scanner(System.in);
        assertEquals(-2,CI.book(test2, mv,"Back"));
    }

    @Test
    public void testbook_Cancel(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();
        MovieViewing mv = sessions.get(10);
        InputStream stdin1 = new ByteArrayInputStream("c".getBytes());

        System.setIn(stdin1);
        Scanner test = new Scanner(System.in);
        assertEquals(-1,CI.book(test, mv,"Back"));
        Crud.alter_viewing_seats(mv.getId(),-1,"Back");
    }

}
