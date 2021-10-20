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
            String selectSQL = "SELECT M.name, V.location, V.day_week, V.session_time, V.screen_type " +
                    "FROM cinemas.dbo.viewings V LEFT JOIN cinemas.dbo.movies M on V.movie_id = M.id;";
            resultSet = statement.executeQuery(selectSQL);

            int i = 1;
            while (resultSet.next()) {
               System.out.printf("Session " + i + " includes - \n");
               System.out.printf("Film Title: " + resultSet.getString(1) + " ");
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
        return null;
    }

    //Check arg with database, return false if not exit, otherwise return true.
    public static boolean checkUsernameExist(String username){
        return username.equals("soft2412");
    }

    //Check arg(password) with database, return false if not exit, otherwise return true.
    public static boolean checkPasswordWithUsername(String username, String password){
        return password.equals("1508");
    }

}
