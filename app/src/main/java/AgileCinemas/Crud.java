package AgileCinemas;
import java.sql.*;
import java.util.ArrayList;

public class Crud {
    // All static methods RETURN AT LEAST A BOOLEAN (FOR SUCCESS)
    public void retrieve_upcoming_sessions() {
        Connection conn = null;
        ResultSet resultSet = null;

        try {
            // Connect to Database
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();

            // Create and execute a SELECT SQL statement
            String selectSQL = "SELECT M.name, V.location, V.day_week, V.session_time, V.screen_type, M.synopsis, M.rating, M.date_release, M.director, M.cast " +
                    "FROM cinemas.dbo.viewings V LEFT JOIN cinemas.dbo.movies M on V.movie_id = M.id;";
            resultSet = statement.executeQuery(selectSQL);

            int i = 1;
            while (resultSet.next()) {
               System.out.printf("Session " + i + " includes - \n");
               System.out.printf("Film Title: " + resultSet.getString(1) + " ");
               System.out.println("Synopsis: "  + resultSet.getString(6) + " ");
               System.out.println("Classification: " + resultSet.getString(7)  + " ");
               System.out.println("Release Date: " + resultSet.getString(8)  + " ");
               System.out.println("Director: " + resultSet.getString(9) + "");
               System.out.println("Cast: " + resultSet.getString(10)  + " ");
               System.out.printf("Location: " + resultSet.getString(2) + " ");
               System.out.printf("Day & Time: " + resultSet.getString(3) + " ");
               System.out.printf(resultSet.getString(4) + " ");
               System.out.printf("Screen Type: " + resultSet.getString(5) + " \n");
               i++;

            }


        } catch (SQLException e) {
            throw new Error("Problem", e);

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

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
        Connection conn = null;
        ResultSet resultSet = null;
        ArrayList<MovieViewing> sessions = new ArrayList<>();

        try {
            // Connect to Database
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // sql statement
            String sql = "SELECT m.id, m.name, m.synopsis, m.rating, m.date_release, m.director, m.cast, v.day_week, v.session_time, v.location, v.screen_type, v.backseats_remaining, v.middleseats_remaining, v.frontseats_remaining " +
                    "from cinemas.dbo.movies m join cinemas.dbo.viewings v on m.id = v.movie_id ;";

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Movie movie = new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                MovieViewing mv = new MovieViewing(movie, resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),resultSet.getInt(12),resultSet.getInt(13),resultSet.getInt(14));
                sessions.add(mv);
            }

            conn.close();
        } catch (SQLException e) {
            throw new Error("Problem", e);

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return sessions;
    }

    //Check arg with database, return false if not exit, otherwise return true.
    public static boolean checkUsernameExist(String username){
        return username.equals("soft2412");
    }

    //Check arg(password) with database, return false if not exit, otherwise return true.
    public static boolean checkPasswordWithUsername(String username, String password){
        return password.equals("1508");
    }

    public static void main(String[] args) {
        ArrayList<MovieViewing> sessions = Crud.getViewings();
        System.out.println(sessions.toString());
    }

}
