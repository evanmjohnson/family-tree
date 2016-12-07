import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Runs update operations for the family-tree database
 */
public class Update {
  private Connection connection;

  /**
   * Constructor for Update with a {@link Connection} object.
   * @param connection a SQL connection to the database
   */
  Update(Connection connection) {
    this.connection = connection;
  }

  /**
   * Default update method that is called after the user selects "update" in the main
   * menu. Delegates all update operations to helper methods.
   */
  public void update() {
    System.out.print("What do you want to update? A person, address, house, reunion, " +
            "or relationship? ");
    Scanner scan = new Scanner(System.in);
    String toUpdate = scan.nextLine();
    if (toUpdate.equalsIgnoreCase("person")) {
      updatePerson();
    }
    else if (toUpdate.equalsIgnoreCase("address")) {
      updateAddress();
    }
    else if (toUpdate.equalsIgnoreCase("house")) {
      updateHouse();
    }
    else if (toUpdate.equalsIgnoreCase("reunion")) {
      // show all reunions
      this.updateReunion();
    }
    else if (toUpdate.equalsIgnoreCase("relationship")) {
      // show all reunions
      this.updateRelationship();
    }
  }

  /**
   * Displays all of the entries in the person table and has the user select one
   * from their ID, then updates the information that the user wants.
   */
  private void updatePerson() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      System.out.print("What is the first name of the person you would like to update? ");
      Scanner scanner = new Scanner(System.in);
      String first = scanner.next();
      int id = Utils.selectPersonFromFirstName(first, connection);
      System.out.print("What do you want to update? LastName, FirstName, DOB, or DOD? ");
      String field = scanner.next();
      System.out.println(field);
      if (field.equalsIgnoreCase("LastName")) {
        System.out.print("Enter the new last name of the person you wish to update: ");
        String newLastName = scanner.next();
        statement.executeUpdate("UPDATE person SET last_name = " + "\'" +
                newLastName + "\'" + "WHERE person_id = " + id);
      }
      else if (field.equalsIgnoreCase("FirstName")) {
        System.out.print("Enter the new first name of the person you wish to update: ");
        String newFirstName = scanner.next();
        statement.executeUpdate("UPDATE person SET first_name = " + "\'" +
                newFirstName + "\'" + "WHERE person_id = " + id);
      }
      else if (field.equalsIgnoreCase("DOB")) {
        System.out.print("Enter the new DOB of the person you wish to update: ");
        String newDOB = scanner.next();
        statement.executeUpdate("UPDATE person SET DoB = " + "\'" +
                newDOB + "\'" + "WHERE person_id = " + id);
      }
      else if (field.equalsIgnoreCase("DOD")) {
        System.out.print("Enter the new DOD of the person you wish to update: ");
        String newDOD = scanner.next();
        statement.executeUpdate("UPDATE person SET DoD = " + "\'" +
                newDOD + "\'" + "WHERE person_id = " + id);
      }
      else {
        System.out.println("Field not identified. Enter again. ");
        this.updatePerson();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * Shows all of the entries in the address table, and has the user select one based on
   * its house_id, then updates the information that the user wants.
   */
  private void updateAddress() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM address");
      Utils.printResultSet(resultSet);
      System.out.print("Select an address to update. Enter its house_id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.next();
      System.out.print("What do you want to update? Street, City, or Country? ");
      String field = scanner.next();
      scanner.nextLine();
      if (field.equalsIgnoreCase("Street")) {
        System.out.print("Enter the new street of the address you wish to update: ");
        String newStreet = scanner.next();
        statement.executeUpdate("UPDATE address SET street = " + "\'" +
                newStreet + "\'" + "WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("City")) {
        System.out.println("Enter the new city of the address you wish to update");
        String newCity = scanner.nextLine();
        statement.executeUpdate("UPDATE address SET city = " + "\'" +
                newCity + "\'" + "WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("Country")) {
        System.out.println("Enter the new country of the address you wish to update");
        String newCountry = scanner.nextLine();
        statement.executeUpdate("UPDATE address SET country = " + "\'" +
                newCountry + "\'" + "WHERE house_id = " + id);
      }
      else {
        System.out.println("Field not identified. Enter again. ");
        this.updateAddress();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Shows all of the entries in the house table, and has the user select one based on
   * its house_id, then updates the information that the user wants.
   */
  private void updateHouse() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM house");
      Utils.printResultSet(resultSet);
      System.out.print("Select a house to update. Enter its house_id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.next();
      System.out.print("What do you want to update? Resident, Number of Residents, " +
              "or Head of House? ");
      String field = scanner.next();
      if (field.equalsIgnoreCase("Resident")) {
        System.out.println("Enter the first name of the new resident of the house you wish to update: ");
        String firstName = scanner.next();
        int personID = Utils.selectPersonFromFirstName(firstName, connection);
        statement.executeUpdate("UPDATE house SET person_id = " + personID +
            " WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("Number")) {
        System.out.println("Enter the new number of residents of the house you wish to update");
        int newNumResidents = scanner.nextInt();
        statement.executeUpdate("UPDATE house SET num_residents = " +
                newNumResidents + " WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("Head")) {
        System.out.println("Enter the first name of the new head of house of the house you wish to update: ");
        String firstName = scanner.next();
        int personID = Utils.selectPersonFromFirstName(firstName, connection);
        statement.executeUpdate("UPDATE house SET head_of_house = " +
                personID + " WHERE house_id = " + id);
      }
      else {
        System.out.println("Field not identified. Enter again. ");
        this.updateHouse();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * Shows all of the entries in the address table, and has the user select one based on
   * its reunion_id, then updates the information that the user wants.
   */
  private void updateReunion() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion");
      Utils.printResultSet(resultSet);
      System.out.print("Select a reunion to update. Enter its id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.next();
      System.out.print("What do you want to update? Head of house, Number of families attending, " +
              "address, occasion, or date? ");
      String field = scanner.next();
      if (field.equalsIgnoreCase("Head")) {
        System.out.print("Enter the first name of the new head of house of the reunion you wish to update: ");
        String firstName = scanner.next();
        int personID = Utils.selectPersonFromFirstName(firstName, connection);
        statement.executeUpdate("UPDATE reunion SET head_of_house = " +
                personID + " WHERE reunion_id = " + "\'" + id + "\'");
      }
      else if (field.equalsIgnoreCase("Number")) {
        System.out.println("Enter the new number of families attending the reunion you wish to update: ");
        int newNumFamiliesAttending = scanner.nextInt();
        statement.executeUpdate("UPDATE reunion SET num_families_attending = " +
                newNumFamiliesAttending + " WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("address")) {
        System.out.print("What is the street of the new house that you want to update the reunion with? ");
        scanner.useDelimiter("\\n");
        String street = scanner.next();
        int houseID = Utils.getHouseIDFromStreet(street, connection);
        statement.executeUpdate("UPDATE reunion SET address = " +
                houseID + " WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("occasion")) {
        System.out.print("Enter the new occasion of the reunion you wish to update: ");
        String newOccasion = scanner.next();
        statement.executeUpdate("UPDATE reunion SET occasion = " + "\'" +
                newOccasion + "\'" + " WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("date")) {
        System.out.print("Enter the new date of the reunion you wish to update(YYYY-MM-DD)");
        String newDate = scanner.next();
        statement.executeUpdate("UPDATE reunion SET reunion_date = " + "\'" +
                newDate + "\'" + " WHERE reunion_id = " + id);
      }
      else {
        System.out.println("Field not identified. Enter again. ");
        this.updateReunion();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Shows all of the entries in the relationship table, and has the user select one based on
   * the id of the first person, then updates the information that the user wants.
   */
  private void updateRelationship() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM relationship");
      Utils.printResultSet(resultSet);
      System.out.print("Select a relationship to update. Enter its id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.next();
      System.out.print("What do you want to update? Person1, Person2, or the relationship? ");
      String field = scanner.next();
      if (field.equalsIgnoreCase("Person1")) {
        System.out.print("What is the first name of the person you want to update this relationship with?: ");
        String newPerson = scanner.next();
        int newID = Utils.selectPersonFromFirstName(newPerson, connection);
        statement.executeUpdate("UPDATE relationship SET person1_id = " +
                newID + " WHERE relation_id = " + id);
      }
      else if (field.equalsIgnoreCase("Person2")) {
        System.out.print("What is the first name of the person you want to update this relationship with?: ");
        String newPerson = scanner.next();
        int newID = Utils.selectPersonFromFirstName(newPerson, connection);
        statement.executeUpdate("UPDATE relationship SET person2_id = " +
                newID + " WHERE relation_id = " + id);
      }
      else if (field.equalsIgnoreCase("relationship")) {
        System.out.println("Enter the new relationship: ");
        String newRelationship = scanner.nextLine();
        statement.executeUpdate("UPDATE relationship SET relationship = " +
                newRelationship + " WHERE relation_id = " + id);
      }
      else {
        System.out.println("Field not identified. Enter again. ");
        this.updateReunion();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
