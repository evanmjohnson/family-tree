import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Contains methods that are useful to every class in the family-tree project.
 */
public class Utils {
  /**
   * Finds the ID of a person from their first name. If there is more than one person
   * with this name, show a list of all of the people with this name and have the user
   * select the ID of the person they want.
   * @param firstName the first name of the person to select
   * @return the ID of the person that the user wants to select
   * @throws IllegalArgumentException if the person is not in the database
   */
  static int selectPersonFromFirstName(String firstName, Connection connection) throws IllegalArgumentException {
    int id = 0;
    try {
      Statement statement = connection.createStatement();
      // count the number of people with this name
      ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE first_name =" +
          "\'" + firstName + "\'");
      // if there are no people with this name, throw an exception.
      if (!resultSet.next()) {
        throw new IllegalArgumentException("Person doesn't exist.");
      }
      ResultSetMetaData rsmd = resultSet.getMetaData();
      int numberColumns = rsmd.getColumnCount();
      // print all of the people with this name
      resultSet.beforeFirst();
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
   * Prints the given result set to standard out.
   * @param resultSet the {@link ResultSet} to print
   * @throws SQLException if the ResultSet is not valid
   */
  static void printResultSet(ResultSet resultSet) throws SQLException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    int numberOfColumns = resultSetMetaData.getColumnCount();
    while(resultSet.next()) {
      for (int i = 1; i <= numberOfColumns; i ++) {
        System.out.println(resultSetMetaData.getColumnName(i) + ": " + resultSet.getString(i) + " ");
      }
      System.out.println();
    }
  }
}
