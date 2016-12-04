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

  public void update() {
    System.out.println("What do you want to update? A person, address, house, reunion," +
            "or relationship?");
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


  private void updatePerson() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
      Main.printResultSet(resultSet);
      System.out.println("Select a person to update. Enter their id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.next();
      System.out.println("What do you want to update? Last Name, First Name, DOB, DOD");
      String field = scanner.nextLine();
      if (field.equalsIgnoreCase("Last Name")) {
        System.out.println("Enter the new last name of the person you wish to update");
        String newLastName = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE person SET last_name = " + "\'" +
                newLastName + "\'" + "WHERE person_id = " + id);
      }
      else if (field.equalsIgnoreCase("First Name")) {
        System.out.println("Enter the new first name of the person you wish to update");
        String newFirstName = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE person SET first_name = " + "\'" +
                newFirstName + "\'" + "WHERE person_id = " + id);
      }
      else if (field.equalsIgnoreCase("DOB")) {
        System.out.println("Enter the new DOB of the person you wish to update");
        String newDOB = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE person SET DoB = " + "\'" +
                newDOB + "\'" + "WHERE person_id = " + id);
      }
      else if (field.equalsIgnoreCase("DOD")) {
        System.out.println("Enter the new DOD of the person you wish to update");
        String newDOD = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE person SET DoD = " + "\'" +
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

  private void updateAddress() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM address");
      Main.printResultSet(resultSet);
      System.out.println("Select an address to update. Enter its house_id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.next();
      System.out.println("What do you want to update? Street, City, or Country?");
      String field = scanner.nextLine();
      if (field.equalsIgnoreCase("Street")) {
        System.out.println("Enter the new street of the address you wish to update");
        String newStreet = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE address SET street = " + "\'" +
                newStreet + "\'" + "WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("City")) {
        System.out.println("Enter the new city of the address you wish to update");
        String newCity = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE address SET city = " + "\'" +
                newCity + "\'" + "WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("Country")) {
        System.out.println("Enter the new country of the address you wish to update");
        String newCountry = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE address SET country = " + "\'" +
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

  private void updateHouse() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM house");
      Main.printResultSet(resultSet);
      System.out.println("Select a house to update. Enter its house_id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.next();
      System.out.println("What do you want to update? Resident, Number of Residents, " +
              "or Head of House?");
      String field = scanner.nextLine();
      if (field.equalsIgnoreCase("Resident")) {
        System.out.println("Enter the new resident of the house you wish to update");
        String newResident = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE house SET person_id = " + "\'" +
                newResident + "\'" + "WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("Number of Residents")) {
        System.out.println("Enter the new number of residents of the house you wish to update");
        String newNumResidents = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE house SET num_residents = " + "\'" +
                newNumResidents + "\'" + "WHERE house_id = " + id);
      }
      else if (field.equalsIgnoreCase("Head of House")) {
        System.out.println("Enter the new head of house of the house you wish to update");
        String newHead = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE house SET head_of_house = " + "\'" +
                newHead + "\'" + "WHERE house_id = " + id);
      }
      else {
        System.out.println("Field not identified. Enter again. ");
        this.updateHouse();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }


  private void updateReunion() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM reunion");
      Main.printResultSet(resultSet);
      System.out.println("Select a reunion to update. Enter its id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.nextLine();
      System.out.println("What do you want to update? Head of house, Number of families attending, " +
              "address, or occasion or date?");
      String field = scanner.nextLine();
      if (field.equalsIgnoreCase("Head of house")) {
        System.out.println("Enter the new head of house of the reunion you wish to update");
        String newHead = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET head_of_house = " + "\'" +
                newHead + "\'" + "WHERE reunion_id = " + "\'" + id + "\'");
      }
      else if (field.equalsIgnoreCase("Number of families attending")) {
        System.out.println("Enter the new number of families attending the reunion you wish to update");
        String newNumFamiliesAttending = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET num_families_attending = " + "\'" +
                newNumFamiliesAttending + "\'" + "WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("address")) {
        System.out.println("Enter the new address of the reunion you wish to update");
        String newAddress = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET address = " + "\'" +
                newAddress + "\'" + "WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("occasion")) {
        System.out.println("Enter the new occasion of the reunion you wish to update");
        String newOccasion = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET occasion = " + "\'" +
                newOccasion + "\'" + "WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("date")) {
        System.out.println("Enter the new date of the reunion you wish to update(YYYY-MM-DD)");
        String newDate = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET date = " + "\'" +
                newDate + "\'" + "WHERE reunion_id = " + id);
      }
      else {
        System.out.println("Field not identified. Enter again. ");
        this.updateReunion();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // TODO -- Fix so that all the people show up so you can enter a person's id
  private void updateRelationship() {
    Statement statement = null;
    try {
      statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM relationship");
      Main.printResultSet(resultSet);
      System.out.println("Select a relationship to update. Enter its id: ");
      Scanner scanner = new Scanner(System.in);
      String id = scanner.nextLine();
      System.out.println("What do you want to update? Person 1, Person 2, or the relationship?");
      String field = scanner.nextLine();
      if (field.equalsIgnoreCase("Person 1")) {
        System.out.println("Enter the new first person of the relationship you wish to update");
        String newFirst = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE relationship SET person1_id = " + "\'" +
                newFirst + "\'" + "WHERE relationship_id = " + "\'" + id + "\'");
      }
      else if (field.equalsIgnoreCase("Number of families attending")) {
        System.out.println("Enter the new number of families attending the reunion you wish to update");
        String newNumFamiliesAttending = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET num_families_attending = " + "\'" +
                newNumFamiliesAttending + "\'" + "WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("address")) {
        System.out.println("Enter the new address of the reunion you wish to update");
        String newAddress = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET address = " + "\'" +
                newAddress + "\'" + "WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("occasion")) {
        System.out.println("Enter the new occasion of the reunion you wish to update");
        String newOccasion = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET occasion = " + "\'" +
                newOccasion + "\'" + "WHERE reunion_id = " + id);
      }
      else if (field.equalsIgnoreCase("date")) {
        System.out.println("Enter the new date of the reunion you wish to update(YYYY-MM-DD)");
        String newDate = scanner.nextLine();
        ResultSet resultSet1 = statement.executeQuery("UPDATE reunion SET date = " + "\'" +
                newDate + "\'" + "WHERE reunion_id = " + id);
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
