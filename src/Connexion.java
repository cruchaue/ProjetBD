
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	private static final String configurationFile = "BD.properties";
	protected Connection conn;

	Connexion() {
		try {
			String jdbcDriver, dbUrl, username, password;

			DatabaseAccessProperties dap = new DatabaseAccessProperties(
					configurationFile);
			jdbcDriver = dap.getJdbcDriver();
			dbUrl = dap.getDatabaseUrl();
			username = dap.getUsername();
			password = dap.getPassword();

			// Load the database driver
			Class.forName(jdbcDriver);

			// Get a connection to the database
			conn = DriverManager.getConnection(dbUrl, username,
					password);

			// Print information about connection warnings
			SQLWarningsExceptions.printWarnings(conn);

			
		} catch (SQLException se) {

			// Print information about SQL exceptions
			SQLWarningsExceptions.printExceptions(se);
			return;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}
	
	
	public static void closeConnexion(Connection conn) throws SQLException{
		conn.close();
	}


	public Connection getConn() {
		return conn;
	}
	
}
