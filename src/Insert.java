import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Runs insert operations for the family-tree database.
 */
public class Insert {
  private Connection connection;

  /**
   * Constructor for Update with a {@link Connection} object.
   * @param connection a SQL connection to the database
   */
  Insert(Connection connection) {
    this.connection = connection;
  }

  /**
   * Default insert method that is called after the user selects "insert" in the main
   * menu. Delegates all insert operations to helper methods.
   */
  public void insert() {
    System.out.print("What do you want to insert? A person, address, house, reunion," +
            "or relationship?");
    Scanner scan = new Scanner(System.in);
    String toUpdate = scan.nextLine();
    if (toUpdate.equalsIgnoreCase("person")) {
      insertPerson();
    }
    else if (toUpdate.equalsIgnoreCase("address")) {
      insertAddress();
    }
    else if (toUpdate.equalsIgnoreCase("house")) {
      insertHouse();
    }
    else if (toUpdate.equalsIgnoreCase("reunion")) {
      // show all reunions
      this.insertReunion();
    }
    else if (toUpdate.equalsIgnoreCase("relationship")) {
      // show all reunions
      this.insertRelationship();
    }
  }

  /**
   * Inserts a person to the database with the prompted fields.
   */
  private void insertPerson() {
    System.out.print("Enter the first name of the new person: ");
    Scanner scanner = new Scanner(System.in);
    String newFirstName = scanner.next();
    System.out.print("Enter the last name of the new person: ");
    String newLast = scanner.next();
    System.out.print("Enter the year of birth of the new person: ");
    String newDOB = scanner.next();
    System.out.print("Is this person alive? Y/N ");
    String yesNo = scanner.next();
    String newDOD = null;
    if (yesNo.equalsIgnoreCase("N")) {
      System.out.print("What is the year of the new person's death? ");
      newDOD = scanner.next();
    }
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet1 = statement.executeQuery("SELECT MAX(person_id) FROM person");
      resultSet1.first();
      int id = Integer.parseInt(resultSet1.getString(1));
      if (newDOD == null) {
        statement.executeUpdate("INSERT INTO person VALUES (" + "\'" + newFirstName + "\',"
            + "\'" + newLast + "\'," + newDOB + ",NULL," + (id + 1) + ")");
      }
      else {
        statement.executeUpdate("INSERT INTO person VALUES (" + "\'" + newFirstName + "\',"
            + "\'" + newLast + "\'," + newDOB + "," + newDOD + "," + (id + 1) + ")");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * Inserts an address to the database with the prompted fields.
   */
  private void insertAddress() {
    System.out.print("Enter the street of the new address: ");
    Scanner scan = new Scanner(System.in);
    scan.useDelimiter("\\n");
    String street = scan.next();
    System.out.print("Enter the city of the new address: ");
    String city = scan.next();
    System.out.print("Does this address have a state? Y/N");
    String yesNo = scan.next();
    String state = null;
    if (yesNo.equalsIgnoreCase("Y")) {
      System.out.print("What is the state? ");
      state = scan.next();
    }
    System.out.print("What is the country of the new address? ");
    String country = scan.next();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet1 = statement.executeQuery("SELECT MAX(house_id) FROM address");
      resultSet1.first();
      int id = Integer.parseInt(resultSet1.getString(1));
      if (state == null) {
        statement.executeUpdate("INSERT INTO address VALUES (\'" + street +
            "\',\'" + city + "\'," + "NULL" + ",\'" + country + "\'," + (id + 1) + ")");
      }
      else {
        statement.executeUpdate("INSERT INTO address VALUES (\'" + street +
            "\',\'" + city + "\',\'" + state + "\',\'" + country + "\'," + (id + 1) + ")");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Inserts a house to the database with the prompted fields.
   */
  private void insertHouse() {

  }

  /**
   * Inserts a reunion to the database with the prompted fields.
   */
  private void insertReunion() {

  }

  /**
   * Inserts a relationship to the database with the prompted fields.
   */
  private void insertRelationship() {

  }
}
