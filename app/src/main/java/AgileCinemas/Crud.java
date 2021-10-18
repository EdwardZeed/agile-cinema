package AgileCinemas;

import java.util.ArrayList;

public class Crud {
    // All static methods RETURN AT LEAST A BOOLEAN (FOR SUCCESS)
    
    // TODO
    public static ArrayList<String> getViewingLocations() {
        // Access database
        return null;
    }

    // TODO
    public static ArrayList<Movie> getMoviesShowing() {
        // Access database
        return null;
    }

    // TODO
    public static ArrayList<MovieViewing> getViewings() {
        // Access database
        return null;
    }
    //Check arg with database, return false if not exit, otherwise return true.
    public static boolean checkUsernameExist(String username){
        return username.equals("soft2412");
    }
    //Check arg(password) with database, return false if not exit, otherwise return true.
    public static boolean checkPasswordExist(String password){
        return password.equals("1508");
    }

}
