import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Runs delete operations for the family-tree database
 */
public class Delete {
  private Connection connection;

  /**
   * Constructor for Update with a {@link Connection} object.
   * @param connection a SQL connection to the database
   */
  Delete(Connection connection) {
    this.connection = connection;
  }

  /**
   * Default insert method that is called after the user selects "insert" in the main
   * menu. Delegates all insert operations to helper methods.
   */
  public void delete() {
    System.out.print("What do you want to delete? A person, address, house, reunion," +
        "or relationship? ");
    Scanner scan = new Scanner(System.in);
    String toDelete = scan.nextLine();
    if (toDelete.equalsIgnoreCase("person")) {
      deletePerson();
    }
    else if (toDelete.equalsIgnoreCase("address")) {
      deleteAddress();
    }
    else if (toDelete.equalsIgnoreCase("house")) {
      deleteHouse();
    }
    else if (toDelete.equalsIgnoreCase("reunion")) {
      deleteReunion();
    }
    else if (toDelete.equalsIgnoreCase("relationship")) {
      deleteRelationship();
    }
  }

  /**
   * Deletes a person from the database based on the inputted first name.
   */
  private void deletePerson() {
    System.out.print("Enter the first name of the person you would like to delete: ");
    Scanner scan = new Scanner(System.in);
    String first = scan.next();
    int id = Utils.selectPersonFromFirstName(first, connection);
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM person WHERE person_id = " + id);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes an address based on the inputted street name.
   */
  private void deleteAddress() {
    System.out.print("Enter the street of the address you want to delete: ");
    Scanner scan = new Scanner(System.in);
    scan.useDelimiter("\\n");
    String street = scan.next();
    int id = Utils.getHouseIDFromStreet(street, connection);
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM address WHERE house_id = " + id);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes a house based on the inputted inhabitant's first name.
   */
  private void deleteHouse() {
    Scanner scan = new Scanner(System.in);
    System.out.print("What is the first name of the person that lives in the house that you want to delete? ");
    String name = scan.next();
    try {
      Statement statement = connection.createStatement();
      int houseID = Utils.getHouseIDFromPerson(name, connection);
      statement.executeUpdate("DELETE FROM house WHERE house_id = " + houseID);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes a reunion based on the inputted first name of the host of the reunion.
   */
  private void deleteReunion() {
    System.out.print("Enter the first name of the host of the reunion: ");
    Scanner scan = new Scanner(System.in);
    String first = scan.next();
    int id = Utils.selectPersonFromFirstName(first, connection);
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT reunion_id FROM Reunion WHERE head_of_house = " + id);
      resultSet.first();
      int reunionID = Integer.parseInt(resultSet.getString(1));
      statement.executeUpdate("DELETE FROM reunion WHERE reunion_id = " + reunionID);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes a relationship based on the inputted first names of both of the people in the relationship.
   */
  private void deleteRelationship() {
    System.out.print("Enter the first name of the first person of the relationship you would like to delete: ");
    Scanner scan = new Scanner(System.in);
    String firstFirst = scan.next();
    int firstID = Utils.selectPersonFromFirstName(firstFirst, connection);
    System.out.print("Enter the first name of the second person of the relationship you would like to delete: ");
    String secondFirst = scan.next();
    int secondID = Utils.selectPersonFromFirstName(secondFirst, connection);
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM relationship WHERE person1_id = " + firstID + " AND " +
          "person2_id = " + secondID);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
