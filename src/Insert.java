import java.sql.Connection;
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
    System.out.println("What do you want to insert? A person, address, house, reunion," +
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

  private void insertPerson() {

  }

  private void insertAddress() {

  }

  private void insertHouse() {

  }

  private void insertReunion() {

  }

  private void insertRelationship() {

  }
}
