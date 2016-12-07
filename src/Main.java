import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

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
public final class Main {

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
   * Prompts the user to enter their username and password and sets
   * the database to be used to starwarsfinal.
   */
  public void promptUser() {
    Scanner scan = new Scanner(System.in);
    System.out.print("Username: ");
    userName = scan.nextLine();
    System.out.print("Password: ");
    this.password = scan.nextLine();
    this.dbName = "familyTree";
    boolean exit = false;
    do {
      System.out.print("Choose an operation: Find, Update, Insert, Delete, or Exit: ");
      String operation = scan.nextLine();
      try {
        if (operation.equalsIgnoreCase("find")) {
          Find find = new Find(this.getConnection());
          find.find();
        }
        else if (operation.equalsIgnoreCase("update")) {
          Update update = new Update(this.getConnection());
          update.update();
        }
        else if (operation.equalsIgnoreCase("insert")) {
          Insert insert = new Insert(this.getConnection());
          insert.insert();
        }
        else if (operation.equalsIgnoreCase("delete")) {
          Delete delete = new Delete(this.getConnection());
          delete.delete();
        }
        else if (operation.equalsIgnoreCase("exit")) {
          exit = true;
        }
        else {
          System.out.println("Not a valid operation. Please try again.");
          continue;
        }
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
    while (!exit);
  }

  /**
   * Get a new database connection.
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
   * Connect to the DB and run the program.
   */
  public static void main(String[] args) {
    Main main = new Main();
    main.promptUser();
  }
}