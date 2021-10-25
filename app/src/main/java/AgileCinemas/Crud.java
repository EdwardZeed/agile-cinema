package AgileCinemas;
import java.sql.*;
import java.util.ArrayList;

public class Crud {

    // TODO Check Credit Card Provided is Valid

    public static boolean is_creditCard_valid(String name, String card_number){
        Connection conn = null;
        ResultSet resultSet = null;
        boolean is_valid = false;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String creditCardExistsQuery = "SELECT COUNT(*) FROM cinemas.dbo.credit_cards " +
                    "WHERE name LIKE '%s' AND card_number LIKE '%s'";
            resultSet = statement.executeQuery(String.format(creditCardExistsQuery, name, card_number));
            // Print Result
            int i = 0;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            if(i == 1){
                is_valid = true;
            }
            else{
                is_valid = false;
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
        return is_valid;

    }

    // TODO Check Gift Card Provided is Valid

    public static boolean is_giftCard_exist(String card_number){
        Connection conn = null;
        ResultSet resultSet = null;
        boolean is_valid = false;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String giftCardExistsQuery = "SELECT COUNT(*) FROM cinemas.dbo.gift_cards " +
                    "WHERE Card_Number LIKE '%s'";
            resultSet = statement.executeQuery(String.format(giftCardExistsQuery, card_number));
            // Print Result
            int i = 0;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            if(i == 1){
                is_valid = true;
            }
            else{
                is_valid = false;
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
        return is_valid;

    }

    // TODO Check Gift Card Provided is Redeemable

    public static boolean is_giftCard_redeemable(String card_number){
        Connection conn = null;
        ResultSet resultSet = null;
        boolean is_valid = false;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String giftCardRedeemableQuery = "SELECT IS_REDEEMED FROM cinemas.dbo.gift_cards " +
                    "WHERE Card_Number LIKE '%s'";
            resultSet = statement.executeQuery(String.format(giftCardRedeemableQuery, card_number));
            // Print Result
            int i = 1;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            if(i == 0){
                is_valid = true;
            }
            else{
                is_valid = false;
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
        return is_valid;

    }

    // TODO Check if Customer has CREDIT CARD Stored
    public static boolean customerContains_creditCard(String username){
        Connection conn = null;
        ResultSet resultSet = null;
        boolean is_valid = false;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String customerHasCreditCard = "SELECT COUNT(credit_card_id) " +
                    "FROM cinemas.dbo.customers " +
                    "WHERE user_name LIKE '%s'";
            resultSet = statement.executeQuery(String.format(customerHasCreditCard, username));
            // Print Result
            int i = 0;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            if(i == 1){
                is_valid = true;
            }
            else{
                is_valid = false;
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
        return is_valid;

    }

    // TODO INSERT CREDIT CARD Details TO CUSTOMER
    public static boolean saveCreditCard(String username, String card_number){
        boolean status = false;
        Connection conn = null;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String saveCardtoCustomer = "UPDATE cinemas.dbo.customers " +
                    "SET credit_card_id = '%s' " +
                    "WHERE user_name LIKE '%s'";
            statement.executeUpdate(String.format(saveCardtoCustomer, card_number, username));
            // Print Result
            status = true;
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
        return status;
    }

    // TODO INSERT TRANSACTION DATA
    public static boolean insertTransaction(String username, float total_amount, String transaction_status,
    int viewing_id, int child_tickets, int concession_tickets, int adult_tickets,
                                            String payment_type, int is_cancelled, int is_idle, String seat_position){
        boolean status = false;
        int total_tickets = child_tickets + concession_tickets + adult_tickets;

        Connection conn = null;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String insert_query = "INSERT INTO cinemas.dbo.transactions " +
                    "(customer_username, Total_Amount, Transaction_Status, Viewing_ID, " +
                    "Number_Child_Tickets, Number_Concession_Tickets, Number_Adult_Tickets, Total_Number_Tickets, " +
                    "Payment_Type, Is_Cancelled, Is_Idle, Seat_Position, INSERTDATE) " +
                    "VALUES ('%s', '%f', '%s', '%d', '%d', '%d', '%d', '%s', '%d', '%d', '%s', CURRENT_TIMESTAMP)";
            statement.executeUpdate(String.format(insert_query, username, total_amount, transaction_status,
                    viewing_id, child_tickets, concession_tickets, adult_tickets, total_tickets, payment_type,
                    is_cancelled, is_idle, seat_position));
            // Return True Status Due to Update Executing Successfully
            status = true;
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
        return status;
        // Zero is used to represent false
    }


    // TODO Return Last Transaction ID - i.e ticket_id
    public static int last_transactionID(String table_name){
        // Use "transactions" in app.
        // Use "transactions_test" in test cases.
        Connection conn = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String retrieve_lastRow = "SELECT TOP 1 id FROM cinemas.dbo.%s ORDER BY id DESC";
            resultSet = statement.executeQuery(String.format(retrieve_lastRow, table_name));
            // Print Result
            int i = 0;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            result = i;
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
        return result;

    }


    // TODO Check if Staff ID is Valid
    public static boolean is_staffID_valid(String staff_id){
        Connection conn = null;
        ResultSet resultSet = null;
        boolean is_valid = false;
        try {
            int id = Integer.parseInt(staff_id);
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String doesStaffIDExist = "SELECT COUNT(*) FROM cinemas.dbo.staff " +
                    "WHERE id = '%d'";
            resultSet = statement.executeQuery(String.format(doesStaffIDExist, id));
            // Print Result
            int i = 0;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            if(i == 1){
                is_valid = true;
            }
            else{
                is_valid = false;
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
        return is_valid;

    }

    // TODO Check if Staff ID is Active
    public static boolean isStaffActive(String staffID){
        Connection conn = null;
        ResultSet resultSet = null;
        boolean is_valid = false;
        try {
            int id = Integer.parseInt(staffID);
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String isStaffActiveQuery = "SELECT IS_ACTIVE FROM cinemas.dbo.staff " +
                    "WHERE ID = '%d'";
            resultSet = statement.executeQuery(String.format(isStaffActiveQuery, id));
            // Print Result
            int i = 0;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            if(i == 1){
                is_valid = true;
            }
            else{
                is_valid = false;
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
        return is_valid;

    }

    // TODO Check if Staff IS is Manager
    public static boolean isManager(String staffID){
        Connection conn = null;
        ResultSet resultSet = null;
        boolean is_valid = false;
        try {
            int id = Integer.parseInt(staffID);
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String isManagerQuery = "SELECT IS_MANAGER FROM cinemas.dbo.staff " +
                    "WHERE ID = '%d'";
            resultSet = statement.executeQuery(String.format(isManagerQuery, id));
            // Print Result
            int i = 0;
            while(resultSet.next()) {
                i = Integer.parseInt(resultSet.getString(1));
            }
            if(i == 1){
                is_valid = true;
            }
            else{
                is_valid = false;
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
        return is_valid;

    }

    // TODO Create Method to Insert New Viewings
    public static boolean add_newViewing(int movieID, String location, String weekday, String sessionTime,
                                         String ScreenType, int backseats, int middleseats, int frontseats){
        boolean status = false;

        Connection conn = null;
        try {
            // Connect to DB
            int totalseats = backseats + middleseats + frontseats;
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String insert_query = "INSERT INTO cinemas.dbo.viewings " +
                    "(movie_id, location, day_week, session_time, " +
                    "screen_type, backseats_remaining, middleseats_remaining, frontseats_remaining, totalseats_remaining" +
                    "VALUES ('%d', '%s', '%s', '%s', '%s', '%d', '%d', '%d', '%d')";
            statement.executeUpdate(String.format(insert_query, movieID, location, weekday, sessionTime, ScreenType,
                    backseats, middleseats, frontseats, totalseats));
            // Return True Status Due to Update Executing Successfully
            status = true;
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
        return status;
        // Zero is used to represent false
    }

    // TODO Alter Viewings Table Based On Transaction (Seats Available)
    public static boolean alter_viewing_seats(int viewing_id, int seats, String seats_type){
        boolean status = false;
        Connection conn = null;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String updateViewings = "UPDATE cinemas.dbo.viewings " +
                    "SET " + seats_type + "seats_remaining = " + seats_type +"seats_remaining + " + seats + ", " +
                    "totalseats_remaining = totalseats_remaining - " + seats +
                    " WHERE id = " + viewing_id;
            statement.executeUpdate(updateViewings);
            // Print Result
            status = true;
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
        return status;

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
        ArrayList<MovieViewing> sessions = new ArrayList<>(); // list of session objects

        try {
            // Connect to Database
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // sql statement
            String sql = "SELECT m.id, m.name, m.synopsis, m.rating, m.date_release, m.director, m.cast, v.day_week, v.session_time, v.location, v.screen_type, v.backseats_remaining, v.middleseats_remaining, v.frontseats_remaining, v.id " +
                    "from cinemas.dbo.movies m join cinemas.dbo.viewings v on m.id = v.movie_id ;";


            resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Movie movie = new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                MovieViewing mv = new MovieViewing(resultSet.getInt(15), movie, resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11),resultSet.getInt(12),resultSet.getInt(13),resultSet.getInt(14));
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
		int result = 0;
		boolean captain;
		try {
			// Connect to DB
			conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
			Statement statement = conn.createStatement();
			// Create and Run Query
			//String usernameExistQuery = "SELECT user_name FROM cinemas.dbo.customers WHERE user_name = '%s'";
			String check_username = "Select COUNT(*) from cinemas.dbo.customers where user_name LIKE " + "'" +
                    username + "'" + ";";
			resultSet = statement.executeQuery(check_username);
			// Print Result
			while(resultSet.next()) {
			    int i = Integer.parseInt(resultSet.getString(1));
			    result = i;
			}
			if(result == 1){
			    captain = true;
            }
			else{
			    captain = false;
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
        return captain;
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

    public static void create_new_user(String username, String password){
        Connection conn = null;
        try {
            // Connect to DB
            conn = DriverManager.getConnection("jdbc:sqlserver://soft2412-a2.cyg3iolyiokd.ap-southeast-2.rds.amazonaws.com:1433;", "admin", "gr0up!wo");
            Statement statement = conn.createStatement();
            // Create and Run Query
            String insert_new_User = "INSERT INTO cinemas.dbo.customers (user_name, password) " +
                    "VALUES ( '" + username + "', '" + password + "');";
            statement.executeUpdate(insert_new_User);
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
    }

    public static void main(String[] args) {
        ArrayList<MovieViewing> sessions = Crud.getViewings();
        System.out.println(sessions.toString());
    }


}
