import java.sql.Connection;
import java.sql.ResultSet;
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

  /**
   * Default method that delegates all specific find operations after the user selects
   * "find" in the main menu.
   */
  public void find() {
    System.out.print("What do you want to find? A person, address, house, reunion," +
        "or relationship? ");
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
      System.out.print("Would you like to search by date, host, address, or occasion? ");
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
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE first_name = " +
          "\'" + person + "\'");
      Utils.printResultSet(resultSet);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finds and prints the address from the user's inputted first name.
   */
  private void findAddress() {
    Scanner scan = new Scanner(System.in);
    System.out.print("Enter the person's first name: ");
    String person = scan.next();
    int id = Utils.selectPersonFromFirstName(person, this.connection);
    try {
      Statement statement = this.connection.createStatement();
      //TODO: this query doesn't work
      ResultSet resultSet = statement.executeQuery("SELECT * FROM address WHERE house_id = " +
          "(SELECT house_id FROM (person JOIN house ON person.person_id = " + id + "))");
      if (!resultSet.next()) {
        System.out.println("Invalid address. Please re-enter.");
        findAddress();
      }
      else {
        resultSet.beforeFirst();
        Utils.printResultSet(resultSet);
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
    int id = Utils.selectPersonFromFirstName(person, this.connection);
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM house WHERE person_id = " + id);
      if (!resultSet.next()) {
        System.out.println("Invalid house. Please re-enter.");
        findHouse();
      }
      else {
        resultSet.beforeFirst();
        Utils.printResultSet(resultSet);
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
    System.out.print("What is the date of the reunion (YYYY-MM-DD)? ");
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
        Utils.printResultSet(resultSet);
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
    System.out.print("What is the first name of the head of the household? ");
    Scanner scan = new Scanner(System.in);
    String head = scan.next();
    try {
      Statement statement = this.connection.createStatement();
      int id = Utils.selectPersonFromFirstName(head, connection);
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion WHERE head_of_house = " + id);
      if (!resultSet.next()) {
        System.out.println("Invalid head of house.");
        findReunionFromPerson();
      }
      else {
        resultSet.beforeFirst();
        Utils.printResultSet(resultSet);
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
    System.out.print("What street is the reunion on? ");
    String street = scan.nextLine();
    System.out.print("What city is the reunion in? ");
    String city = scan.nextLine();
    System.out.print("What country is the reunion in? ");
    String country = scan.nextLine();
    try {
      Statement statement = this.connection.createStatement();
      String query = "SELECT * FROM reunion WHERE reunion.address = " +
          "(SELECT house_id FROM address WHERE " +
          "street = " + "\'" + street + "\' AND " + "city = " + "\'" + city + "\' AND " +
          "country = " + "\'" + country + "\')";
      ResultSet resultSet = statement.executeQuery(query);
      if (!resultSet.next()) {
        System.out.println("Invalid address. Please re-enter.");
        findReunionFromAddress();
      }
      else {
        resultSet.beforeFirst();
        Utils.printResultSet(resultSet);
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
    System.out.print("Enter the occasion of the reunion: ");
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
        Utils.printResultSet(resultSet);
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
    System.out.print("Enter the first name of the first person in the relationship: ");
    String person1 = scan.nextLine();
    int id1 = 0;
    try {
      id1 = Utils.selectPersonFromFirstName(person1, this.connection);
    }
    catch (IllegalArgumentException e) {
      System.out.println("Invalid person.");
      findRelationship();
    }
    System.out.print("Enter the first name of the second person in the relationship: ");
    String person2 = scan.nextLine();
    int id2 = 0;
    try {
      id2 = Utils.selectPersonFromFirstName(person2, this.connection);
    }
    catch (IllegalArgumentException e) {
      System.out.println("Invalid person. Please re-enter both people.");
      findRelationship();
    }
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT relationship FROM relationship WHERE " +
          "person1_id = " + id1 + " AND person2_id = " + id2);
      if (!resultSet.next()) {
        System.out.println("Invalid relationship. Please re-enter.");
        findRelationship();
      }
      else {
        System.out.println(person1 + " is " + person2 + "'s " + resultSet.getString(1) + ".");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
