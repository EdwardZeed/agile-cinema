package AgileCinemas;
import java.sql.*;
import java.util.ArrayList;

public class Crud {

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
        ArrayList<MovieViewing> sessions = new ArrayList<>(); // list of session objects

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

    /**
     * Queries whether the given username is in the database
     * @param username given username to query with database
     * @return true if username found, otherwise false
    */
    public static boolean checkUsernameExist(String username){
        Connection conn = null;
		ResultSet resultSet = null;
		String result = null;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String usernameExistQuery = "SELECT user_name FROM cinemas.dbo.customers WHERE user_name = '%s'";
			resultSet = statement.executeQuery(String.format(usernameExistQuery, username));
			// Print Result
			while(resultSet.next()) {
				result = resultSet.getString(1); // null is doesn't exist
			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		} finally {
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
        return (result != null); // non-null if username found, else is null
    }

    //Check arg(password) with database, return false if not exit, otherwise return true.
    public static boolean checkPasswordWithUsername(String username, String password){
        Connection conn = null;
		ResultSet resultSet = null;
		String result = null;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			String usernameExistQuery = "SELECT user_name FROM cinemas.dbo.customers WHERE user_name = '%s' AND password = '%s'";
			resultSet = statement.executeQuery(String.format(usernameExistQuery, username, password));
			// Print Result
			while(resultSet.next()) {
				result = resultSet.getString(1); // null is doesn't exist
			}
		}
		catch (SQLException e){
			throw new Error("Problem", e);
		} finally {
			try{
				if (conn!=null){
					conn.close();
				}
			} catch (SQLException ex){
				System.out.println(ex.getMessage());
			}
		}
        return (result != null); // non-null if username + password found, else is null
    }

    public static void main(String[] args) {
        ArrayList<MovieViewing> sessions = Crud.getViewings();
        System.out.println(sessions.toString());
    }
}
