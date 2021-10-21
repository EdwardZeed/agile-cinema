package AgileCinemas;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CrudTest {

    /** 
     * checkUsernameExist() test
     *   usernameExist - given username exists in db, should return true
     *   usernameNotExist - given username does not exist in db, should return false
    */
    @Test
    public void usernameExist() {
        assertTrue(Crud.checkUsernameExist("rachela"));
    }

    @Test
    public void usernameNotExist() {
        assertFalse(Crud.checkUsernameExist("guestuser"));
    }

    /** 
     * checkPasswordWithUsername() test
     *   passwordCorrect - given username exists in db with corresponding password, should return true
     *   passwordIncorrect - given username exists in db but username is incorrect, should return false
     *   passwordAnotherUser - given username and password correspond to different users, should return false
    */
    @Test
    public void passwordCorrect() {
        assertTrue(Crud.checkPasswordWithUsername("rachela", "purple"));
    }

    @Test
    public void passwordIncorrect() {
        assertFalse(Crud.checkPasswordWithUsername("rachela", "badpassword"));
    }

    @Test
    public void passwordAnotherUser() {
        assertFalse(Crud.checkPasswordWithUsername("rachela", "blue"));
    }

}
