package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CinemaStaffTest {

    /** 
     * getId() test
     *   getIdTest: Test getting the ID of the cinema staff  
    */
    @Test
    public void getIdTest() {
        CinemaStaff test = new CinemaStaff("1234");
        assertEquals("1234", test.getId());
    }
    
    /** 
     * addSession() test
     *   addSessionTooManyArgs: Staff enters more than the 8 required session details; should return false
     *   addSessionTooFewArgs: Staff enters less than the 8 required session details; should return false
     * 
     *   addSessionNonIntMovieId: Staff enters a non-integer movie ID; should return false
     *   addSessionNonIntRearSeats: Staff enters a non-integer available rear seats value; should return false
     *   addSessionNonIntMiddleSeats: Staff enters a non-integer available middle seats value; should return false
     *   addSessionNonIntFrontSeats: Staff enters a non-integer available front seats value; should return false
     * 
     *   COMMENTED OUT addSessionBadMovieId: Staff enters a movie ID that is not already present in the database; should return false
     *   addSessionSuccess: Staff enters movie session details that are valid and are added to the database; should return true
    */
    @Test
    public void addSessionTooManyArgs() {
        String input = "3,Hornsby,Tuesday,08:45,Silver,22,45,18,popcorn";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.addSession());
    }

    @Test
    public void addSessionTooFewArgs() {
        String input = "3,Hornsby,Tuesday,08:45,Silver,22,45";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.addSession());
    }

    @Test
    public void addSessionNonIntMovieId() {
        String input = "three,Hornsby,Tuesday,08:45,Silver,13,45,18";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.addSession());
    }

    @Test
    public void addSessionNonIntRearSeats() {
        String input = "3,Hornsby,Tuesday,08:45,Silver,rear seats,45,18";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.addSession());
    }

    @Test
    public void addSessionNonIntMiddleSeats() {
        String input = "3,Hornsby,Tuesday,08:45,Silver,24, ,18";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.addSession());
    }
    
    @Test
    public void addSessionNonIntFrontSeats() {
        String input = "3,Hornsby,Tuesday,08:45,Silver,24,64,eighteen";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.addSession());
    }

    /*
    @Test
    public void addSessionBadMovieId() {
        // String input = "<movie ID>,<cinema location>,<weekday>,<time>,<screen size>,<rear seats available>,<middle seats available>,<front seats available>";
        String input = "24,Hornsby,Tuesday,08:45,Silver,24,64,18";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.addSession());
    }
    */

    @Test
    public void addSessionSuccess() {
        // String input = "<movie ID>,<cinema location>,<weekday>,<time>,<screen size>,<rear seats available>,<middle seats available>,<front seats available>";
        String input = "3,Hornsby,Tuesday,08:45,Silver,24,64,18";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertTrue(CinemaStaff.addSession());
        Crud.del_row("viewings");

    }

    @Test
    public void addGiftCardCanceled(){
        String input = "c";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertFalse(CinemaStaff.enterGiftCard());

    }

    @Test
    public void addGiftCardSuccess(){
        String input = "1000000000000010GC";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertTrue(CinemaStaff.enterGiftCard());
        Crud.del_row("gift_cards");
    }

    @Test
    public void addGiftCardInvalid(){
        String input = "100000000010GC\nN";
        InputStream test1 = new ByteArrayInputStream(input.getBytes());
        System.setIn(test1);
        assertFalse(CinemaStaff.enterGiftCard());

        InputStream test2 = new ByteArrayInputStream("1000000000000010FF\nN".getBytes());
        System.setIn(test2);
        assertFalse(CinemaStaff.enterGiftCard());

        InputStream test3 = new ByteArrayInputStream("aaaaaaaaaaaaaaaaGC\nN".getBytes());
        System.setIn(test3);
        assertFalse(CinemaStaff.enterGiftCard());

        InputStream test4 = new ByteArrayInputStream("1000000000000000GC\nN".getBytes());
        System.setIn(test4);
        assertFalse(CinemaStaff.enterGiftCard());
    }


}
