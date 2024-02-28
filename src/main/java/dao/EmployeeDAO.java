package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDAO {

private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";

	private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO employees" + "  (name, email, phone, birthday) VALUES "
			+ " (?, ?, ?, ?);";

	private static final String SELECT_EMPLOYEES_BY_ID = "select id,name,email,phone,birthday from employees where id =?";
	private static final String SELECT_ALL_EMPLOYEES = "select * from employees";
	private static final String DELETE_EMPLOYEES_SQL = "delete from employees where id = ?;";
	private static final String UPDATE_EMPLOYEES_SQL = "update employees set name = ?,email= ?, phone =?, birthday =? where id = ?;";

	public EmployeeDAO() {
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public void insertEmployee(Employee employee) throws SQLException {
		System.out.println(INSERT_EMPLOYEES_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEES_SQL)) {
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getEmail());
			preparedStatement.setString(3, employee.getPhone());
			preparedStatement.setDate(4, employee.getBirthday());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();;
		}
	}
	
	public Employee selectEmployee(int id) {
		Employee user = null;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEES_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String birthday = rs.getString("birthday");
				employee = new Employee(id, name, email, phone, birthday);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	public List<Employee> selectAllEmployees() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Employee> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String birthday = rs.getString("birthday");
				employees.add(new Employee(id, name, email, phone, birthday));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}

	public boolean deleteEmployee(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEES_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateEmployee(Employee employee) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEES_SQL);) {
			statement.setString(1, employee.getName());
			statement.setString(2, employee.getEmail());
			statement.setString(3, employee.getPhone());
			statement.setDate(4, employee.getBirthday());
			statement.setInt(4, employee.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
  
}
