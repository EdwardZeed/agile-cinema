package AgileCinemas;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        ArrayList<MovieViewing> sessions = new ArrayList<>();
        // get current date
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = format.format(date);
        // get date after a week
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date dateAfterWeek = calendar.getTime();
        String dateAfterWeekString = format.format(dateAfterWeek);

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            String sql = "select MOVIE_ID from VIEWINGS join MOVIES on (MOVIE_ID = ID) where Date between ? and ? group by MOVIE_ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, currentDate);
            ps.setString(2, dateAfterWeekString);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                // need to double-check the exact column number
                Movie movie = new Movie(rs.getInt(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16));
                MovieViewing movieViewing = new MovieViewing(movie);
                sessions.add(movieViewing);
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
