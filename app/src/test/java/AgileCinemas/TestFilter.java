package AgileCinemas;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        InputStream stdin = new ByteArrayInputStream("inside out".getBytes());
        System.setIn(stdin);

        Map<Integer, MovieViewing> filtered = CI.filter_with_name(sessions);
        assertNotNull(filtered);
        assertEquals(5, filtered.size());
    }

    @Test
    public void testFilterWithLocation(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();

        InputStream stdin = new ByteArrayInputStream("burwood".getBytes());
        System.setIn(stdin);

        Map<Integer, MovieViewing> filtered = CI.filter_with_location(sessions);
        assertNotNull(filtered);
        assertEquals(2, filtered.size());
    }

    @Test
    public void testFilterWithTime(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();

        InputStream stdin = new ByteArrayInputStream("Monday".getBytes());
        System.setIn(stdin);

        Map<Integer, MovieViewing> filtered = CI.filter_with_time(sessions);
        assertNotNull(filtered);
        assertEquals(2, filtered.size());
    }

    @Test
    public void testComboFilter(){
        CustomerInterface CI = new CustomerInterface();
        Map<Integer, MovieViewing> sessions = CI.toHashMap();

        InputStream stdin1 = new ByteArrayInputStream("There Will Be Blood".getBytes());
        InputStream stdin2 = new ByteArrayInputStream("Thursday".getBytes());
        InputStream stdin3 = new ByteArrayInputStream("Hornsby".getBytes());

        System.setIn(stdin1);
        Map<Integer, MovieViewing> filtered1 = CI.filter_with_name(sessions);
        assertEquals(2, filtered1.size());

        System.setIn(stdin2);
        Map<Integer, MovieViewing> filtered2 = CI.filter_with_time(filtered1);
        assertEquals(1, filtered2.size());

        System.setIn(stdin3);
        Map<Integer, MovieViewing> filtered3 = CI.filter_with_location(filtered2);

        assertNotNull(filtered3);
        assertEquals(1, filtered3.size());
    }
}
