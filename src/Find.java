import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Runs find operations for the family tree database.
 */
public class Find {
  private Connection connection;

  /**
   * Constructor for Find with a {@link Connection} object.
   * @param connection a SQL connection to the database
   */
  Find(Connection connection) {
    this.connection = connection;
  }

  public void find() {
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
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE person_id =" + id);
      Main.printResultSet(resultSet);
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
      Statement statement = this.connection.createStatement();
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
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM address WHERE house_id = " +
          "(SELECT house_id FROM (person JOIN house ON person_id = " + id + "))");
      if (!resultSet.next()) {
        System.out.println("Invalid address. Please re-enter.");
        findAddress();
      }
      else {
        resultSet.beforeFirst();
        Main.printResultSet(resultSet);
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
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM house WHERE person_id = " + id);
      if (!resultSet.next()) {
        System.out.println("Invalid house. Please re-enter.");
        findHouse();
      }
      else {
        resultSet.beforeFirst();
        Main.printResultSet(resultSet);
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
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE reunion_date = " +
          "\'" + date + "\'");
      if (!resultSet.next()) {
        System.out.println("Invalid address. Please re-enter.");
        findReunionFromDate();
      }
      else {
        resultSet.beforeFirst();
        Main.printResultSet(resultSet);
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
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE head_of_house =" +
          "\'" + head + "\'");
      if (!resultSet.next()) {
        System.out.println("Invalid head of house.");
        findReunionFromPerson();
      }
      else {
        resultSet.beforeFirst();
        Main.printResultSet(resultSet);
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
      Statement statement = this.connection.createStatement();
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
        Main.printResultSet(resultSet);
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
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE occasion = " +
          "\'" + occasion + "\'");
      if (!resultSet.next()) {
        System.out.println("Not a valid occasion. Please re-enter.");
        findReunionFromOccasion();
      }
      else {
        resultSet.beforeFirst();
        Main.printResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
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
      Statement statement = this.connection.createStatement();
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
}
