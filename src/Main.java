import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;


/**
 * Homework 6
 * Ashna Shah
 */


/**
 * This class demonstrates how to connect to MySQL and run some basic commands.
 *
 * In order to use this, you have to download the Connector/J driver and add its .jar file to your
 * build path.  You can find it here:
 *
 * http://dev.mysql.com/downloads/connector/j/
 *
 * You will see the following exception if it's not in your class path:
 *
 * java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost:3306/
 *
 * To add it to your class path: 1. Right click on your project 2. Go to Build Path -> Add External
 * Archives... 3. Select the file mysql-connector-java-5.1.24-bin.jar NOTE: If you have a different
 * version of the .jar file, the name may be a little different.
 *
 * The user name and password are both "root", which should be correct if you followed the advice in
 * the MySQL tutorial. If you want to use different credentials, you can change them below.
 *
 * You will get the following exception if the credentials are wrong:
 *
 * java.sql.SQLException: Access denied for user 'userName'@'localhost' (using password: YES)
 *
 * You will instead get the following exception if MySQL isn't installed, isn't running, or if your
 * serverName or portNumber are wrong:
 *
 * java.net.ConnectException: Connection refused
 */
public class DBDemo {

  /**
   * The name of the MySQL account to use (or empty for anonymous)
   */
  private String userName;

  /**
   * The password for the MySQL account (or empty for anonymous)
   */
  private String password;

  /**
   * The name of the computer running MySQL
   */
  private final String serverName = "localhost";

  /**
   * The port of the MySQL server (default is 3306)
   */
  private final int portNumber = 3306;

  /**
   * The name of the database we are testing with (this default is installed with MySQL)
   */
  private String dbName;

  /**
   * The name of the table we are testing with
   */
  private final String tableName = "JDBC_TEST";

  /**
   * Get a new database connection
   */
  public Connection getConnection() throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);
    conn = DriverManager.getConnection("jdbc:mysql://"
            + this.serverName + ":" + this.portNumber + "/" + this.dbName
            + "?autoReconnect=true&useSSL=false", connectionProps);

    return conn;
  }

  /**
   * Run a SQL command which does not return a recordset: CREATE/INSERT/UPDATE/DELETE/DROP/etc.
   *
   * @throws SQLException If something goes wrong
   */
  public boolean executeUpdate(Connection conn, String command) throws SQLException {
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(command); // This will throw a SQLException if it fails
      return true;
    } finally {

      // This will run whether we throw an exception or not
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * Connect to MySQL and do some stuff.
   */
  public void run() {

    // Connect to MySQL
    Connection conn = null;
    try {
      conn = this.getConnection();
      System.out.println("Connected to database");
    } catch (SQLException e) {
      System.out.println("ERROR: Could not connect to the database");
      e.printStackTrace();
      return;
    }

    // Create a table
    try {
      String createString =
              "CREATE TABLE " + this.tableName + " ( " +
                      "ID INTEGER NOT NULL, " +
                      "NAME varchar(40) NOT NULL, " +
                      "STREET varchar(40) NOT NULL, " +
                      "CITY varchar(20) NOT NULL, " +
                      "STATE char(2) NOT NULL, " +
                      "ZIP char(5), " +
                      "PRIMARY KEY (ID))";
      this.executeUpdate(conn, createString);
      System.out.println("Created a table");
    } catch (SQLException e) {
      System.out.println("ERROR: Could not create the table");
      e.printStackTrace();
      return;
    }

    // Drop the table
    try {
      String dropString = "DROP TABLE " + this.tableName;
      this.executeUpdate(conn, dropString);
      System.out.println("Dropped the table");
    } catch (SQLException e) {
      System.out.println("ERROR: Could not drop the table");
      e.printStackTrace();
      return;
    }
  }

  /**
   * Prompts the user to enter their username and password and sets
   * the database to be used to starwarsfinal.
   */
  public void promptUser() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Username: ");
    this.userName = scanner.nextLine();
    System.out.print("Password: ");
    this.password = scanner.nextLine();
    this.dbName = "starwarsfinal";
  }

  /**
   * Prompts the user to enter a characterName. If the characterName is within the list of all the
   * characters, the track_character() function is called and the resultSet is returned.
   */
  public void getCharacter() {
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT character_name FROM characters");

      // Prints out the list of all the characters in the character table.
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1) + " ");
      }

      // Prompts a user to enter a character name.
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter a character name from the list: ");
      String characterName = scanner.nextLine();

      // To check that the characterName exists in the list of characters
      resultSet = statement.executeQuery("SELECT character_name FROM characters WHERE character_name = " + "\'" + characterName + "\'");

      // If the resultSet does not have a next, then prompt the user to enter a name again.
      if (!resultSet.next()) {
        System.out.println("Invalid character name. Please enter a name from this list: ");
        this.getCharacter();
      }
      else {
        resultSet.beforeFirst();

        // while the resultSet has a next, call the track_character() function using the valid
        // inputted characterName.
        while (resultSet.next()) {

          resultSet = statement.executeQuery("CALL track_character(\'" + characterName + "\')");

          ResultSetMetaData rsmd = resultSet.getMetaData();
          int numberOfColumns = rsmd.getColumnCount();

          // To print out the resultSet after inputting a certain character
          while (resultSet.next()) {
            for (int i = 1; i <= numberOfColumns; i += 1) {
              System.out.print(resultSet.getString(i) + " ");
            }
            System.out.println(" ");
          }

        }
      }

    }
    // Catch a SQLException if it occurs and prompt the user to enter a new character name.
    catch (SQLException e) {
      System.out.print("Invalid character name. Please enter a name from this list: \n");
      this.getCharacter();
    }

    // To close the connection to the database and end the program.
    try {
      this.getConnection().close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Connect to the DB and do some stuff
   */
  public static void main(String[] args) {
    DBDemo app = new DBDemo();
    app.promptUser();
    app.getCharacter();
    app.run();

  }
}