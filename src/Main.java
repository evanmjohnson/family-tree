import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
   * Connect to the DB and do some stuff
   */
  public static void main(String[] args) {
    Main main = new Main();
    main.promptUser();
  }

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
      System.out.println("Choose an operation: Find, Update, Insert, Delete, or Exit.");
      String operation = scan.nextLine();
      if (operation.equalsIgnoreCase("find")) {
        find();
      }
    }
    while (!exit);
  }

  /**
   * Calls any of the find operations that the user selects.
   */
  private void find() {
    System.out.println("What do you want to find? A person, address, house, reunion," +
        "or relationship?");
    Scanner scan = new Scanner(System.in);
    String toFind = scan.nextLine();
    if (toFind.equalsIgnoreCase("person")) {
      findPerson();
    }
    else if (toFind.equalsIgnoreCase("address")) {
      findAddress();
    }
    else if (toFind.equalsIgnoreCase("house")) {
      findHouse();
    }
    else if (toFind.equalsIgnoreCase("reunion")) {
      System.out.println("Would you like to search by date, host, address, or occasion?");
      String searchBy = scan.next();
      if (searchBy.equalsIgnoreCase("date")) {
        findReunionFromDate();
      }
      else if (searchBy.equalsIgnoreCase("host")) {
        findReunionFromPerson();
      }
      else if (searchBy.equalsIgnoreCase("address")) {
        findReunionFromAddress();
      }
      else if (searchBy.equalsIgnoreCase("occasion")) {
        findReunionFromOccasion();
      }
    }
    else if (toFind.equalsIgnoreCase("relationship")) {
      findRelationship();
    }
  }

  /**
   * Finds a person from the user's input and outputs all of their information.
   */
  private void findPerson() {
    System.out.print("Enter the person's first name: ");
    Scanner scan = new Scanner(System.in);
    String person = scan.next();
    int id = 0;
    try {
      id = selectPersonFromFirstName(person);
    }
    catch (IllegalArgumentException e) {
      System.out.println("Couldn't find that person.");
      findPerson();
    }
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE person_id =" + id);
      printResultSet(resultSet);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finds the ID of a person from their first name. If there is more than one person
   * with this name, show a list of all of the people with this name and have the user
   * select the ID of the person they want.
   * @param firstName the first name of the person to select
   * @return the ID of the person that the user wants to select
   * @throws IllegalArgumentException if the person is not in the database
   */
  private int selectPersonFromFirstName(String firstName) throws IllegalArgumentException {
    int id = 0;
    try {
      Statement statement = this.getConnection().createStatement();
      // count the number of people with this name
      ResultSet count = statement.executeQuery("SELECT COUNT(*) FROM person WHERE first_name =" +
          "\'" + firstName + "\'");
      // select all of the people with this name
      ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE first_name =" +
          "\'" + firstName + "\'");
      // if there is only one person with this name, just return their ID
      if (Integer.parseInt(count.getString(1)) == 1) {
        id = Integer.parseInt(resultSet.getString(1));
      }
      // if there are no people with this name, throw an exception.
      if (!resultSet.next()) {
        throw new IllegalArgumentException("Person doesn't exist.");
      }
      ResultSetMetaData rsmd = resultSet.getMetaData();
      int numberColumns = rsmd.getColumnCount();
      // print all of the people with this name
      while (resultSet.next()) {
        for (int i = 1; i <= numberColumns; i++) {
          System.out.println(resultSet.getString(i) + " ");
        }
        System.out.println();
      }
      System.out.print("Enter the ID of the person you want to select: ");
      Scanner scan = new Scanner(System.in);
      id = scan.nextInt();
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  /**
   * Finds and prints the address from the user's inputted first name.
   */
  private void findAddress() {
    Scanner scan = new Scanner(System.in);
    System.out.print("Enter the person's first name: ");
    String person = scan.next();
    int id = selectPersonFromFirstName(person);
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM address WHERE house_id = " +
          "(SELECT house_id FROM (person JOIN house ON person_id = " + id + "))");
      if (!resultSet.next()) {
        System.out.println("Invalid address. Please re-enter.");
        findAddress();
      }
      else {
        resultSet.beforeFirst();
        printResultSet(resultSet);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finds and prints the house from the user's inputted first name.
   */
  private void findHouse() {
    Scanner scan = new Scanner(System.in);
    System.out.print("Enter the person's first name: ");
    String person = scan.next();
    int id = selectPersonFromFirstName(person);
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM house WHERE person_id = " + id);
      if (!resultSet.next()) {
        System.out.println("Invalid house. Please re-enter.");
        findHouse();
      }
      else {
        resultSet.beforeFirst();
        printResultSet(resultSet);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finds a prints a reunion from the user's inputted date.
   */
  private void findReunionFromDate() {
    System.out.println("What is the date of the reunion (YYYY-MM-DD)?");
    Scanner scan = new Scanner(System.in);
    String date = scan.nextLine();
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE reunion_date = " +
          "\'" + date + "\'");
      if (!resultSet.next()) {
        System.out.println("Invalid address. Please re-enter.");
        findReunionFromDate();
      }
      else {
        resultSet.beforeFirst();
        printResultSet(resultSet);
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finds and prints a reunion from the user's inputted first name.
   */
  private void findReunionFromPerson() {
    System.out.println("What is the first name of the head of the household?");
    Scanner scan = new Scanner(System.in);
    String head = scan.next();
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE head_of_house =" +
          "\'" + head + "\'");
      if (!resultSet.next()) {
        System.out.println("Invalid head of house.");
        findReunionFromPerson();
      }
      else {
        resultSet.beforeFirst();
        printResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finds and prints a reunion from the user's inputted street, city, and country.
   */
  private void findReunionFromAddress() {
    Scanner scan = new Scanner(System.in);
    System.out.println("What street is the reunion on?");
    String street = scan.nextLine();
    System.out.println("What city is the reunion in?");
    String city = scan.nextLine();
    System.out.println("What country is the reunion in?");
    String country = scan.nextLine();
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE house_id = " +
          "(SELECT house_id FROM address WHERE" +
          "street = " + "\'" + street + "\' AND " + "city = " + "\'" + city + "\' AND " +
          "country = " + "\'" + country + "\')");
      if (!resultSet.next()) {
        System.out.println("Invalid address. Please re-enter.");
        findReunionFromAddress();
      }
      else {
        resultSet.beforeFirst();
        printResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finds and prints a reunion from the user's inputted occasion.
   */
  private void findReunionFromOccasion() {
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter the occasion of the reunion");
    String occasion = scan.nextLine();
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE occasion = " +
          "\'" + occasion + "\'");
      if (!resultSet.next()) {
        System.out.println("Not a valid occasion. Please re-enter.");
        findReunionFromOccasion();
      }
      else {
        resultSet.beforeFirst();
        printResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Prints the given result set to standard out.
   * @param resultSet the {@link ResultSet} to print
   * @throws SQLException if the ResultSet is not valid
   */
  private void printResultSet(ResultSet resultSet) throws SQLException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int numberOfColumns = resultSetMetaData.getColumnCount();
    while(resultSet.next()) {
      for (int i = 1; i <= numberOfColumns; i ++) {
        System.out.println(resultSet.getString(i) + " ");
      }
      System.out.println();
    }
  }

  /**
   * Finds and prints the relationship between two people given their first names.
   */
  private void findRelationship() {
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter the first name of the first person in the relationship.");
    String person1 = scan.nextLine();
    int id1 = 0;
    try {
      id1 = selectPersonFromFirstName(person1);
    }
    catch (IllegalArgumentException e) {
      System.out.println("Invalid person.");
      findRelationship();
    }
    System.out.println("Enter the first name of the second person in the relationship.");
    String person2 = scan.nextLine();
    int id2 = 0;
    try {
      id2 = selectPersonFromFirstName(person2);
    }
    catch (IllegalArgumentException e) {
      System.out.println("Invalid person. Please re-enter both people.");
      findRelationship();
    }
    try {
      Statement statement = this.getConnection().createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT relationship FROM relationship WHERE" +
          "person1_id = " + id1 + " AND person2_id = " + id2);
      if (!resultSet.next()) {
        System.out.println("Invalid relationship. Please re-enter.");
        findRelationship();
      }
      else {
        resultSet.beforeFirst();
        System.out.println(person1 + " is " + person2 + "'s " + resultSet.getString(1) + ".");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


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

}