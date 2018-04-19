package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.CashierDTO;
import database.DBOConnectionFactory;

public class CashierRepository implements CashierRepositoryInterface{
	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;
	
	public CashierRepository() {
		
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn;
		conn = DBOConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	public CashierDTO retrieve(int id) {
		CashierDTO cashier = new CashierDTO();
		try {
			String querry = "SELECT * FROM nationaltheatre.cashier WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			
			while(resultSet.next()) {
//				System.out.println("\nId: " + resultSet.getInt(1)
//					 + " Username: " + resultSet.getString(2) + " Password: " + resultSet.getString(3) + " Is admin: " + resultSet.getInt(4));
				
				cashier.setId(resultSet.getInt(1));
				cashier.setUsername(resultSet.getString(2));
				cashier.setPassword(resultSet.getString(3));
				cashier.setAdmin(resultSet.getInt(4));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ptmt != null){
					ptmt.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return cashier;
	}
	
	public List<CashierDTO> retrieveAll() {
		List<CashierDTO> cashiers = new ArrayList<CashierDTO>();
		CashierDTO cashier = new CashierDTO();
		try {
			String querry = "SELECT * FROM nationaltheatre.cashier ORDER BY nationaltheatre.cashier.username";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			resultSet = ptmt.executeQuery();
			
			while(resultSet.next()) {
				cashier = new CashierDTO();
//				System.out.println("\nId: " + resultSet.getInt(1)
//				 + " Username: " + resultSet.getString(2) + " Password: " + resultSet.getString(3) + "Is Admin: " + resultSet.getInt(4));
				
				cashier.setId(resultSet.getInt(1));
				cashier.setUsername(resultSet.getString(2));
				cashier.setPassword(resultSet.getString(3));
				cashier.setAdmin(resultSet.getInt(4));
				cashiers.add(cashier);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ptmt != null){
					ptmt.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return cashiers;
	}
	
	public void create(CashierDTO cashier) {
		try {
			String querry = "INSERT INTO nationaltheatre.cashier(username, password, is_admin) VALUES(?, ?, ?)";
			connection = getConnection();
		    ptmt = connection.prepareStatement(querry);
			ptmt.setString(1, cashier.getUsername());
			ptmt.setString(2, cashier.getEncryptedPassword());
			ptmt.setInt(3, cashier.getAdmin());
			ptmt.executeUpdate();
			
			System.out.println("\nInsert into cashier executed succesfully");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ptmt != null){
					ptmt.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(CashierDTO cashier) {
		try {
			String querry = "UPDATE nationaltheatre.cashier SET username = ?, password = ?, is_admin = ? WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			//ptmt.setInt(1, cashier.getId());
			ptmt.setString(1, cashier.getUsername());
			ptmt.setString(2, cashier.getEncryptedPassword());
			ptmt.setInt(3,  cashier.getAdmin());
			ptmt.setInt(4, cashier.getId());
			ptmt.executeUpdate();
			
			System.out.println("Update into cashier executed succesfully at id = " + cashier.getId());
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try {
			String querry = "DELETE FROM nationaltheatre.cashier WHERE id = " + id;
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.executeUpdate();
			System.out.printf("Delete of show: %d was executed succesfully\n", id);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ptmt != null)
					ptmt.close();
				if(connection != null)
					connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public CashierDTO retrieveCashierByCredentials(String username, String password) {
		CashierDTO cashier = new CashierDTO();
		try {
			String querry = "SELECT * FROM nationaltheatre.cashier WHERE username = ? and password = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setString(1, username);
			ptmt.setString(2, password);
			resultSet = ptmt.executeQuery();
			
			while(resultSet.next()) {
//				System.out.println("\nId: " + resultSet.getInt(1)
//					 + " Username: " + resultSet.getString(2) + " Password: " + resultSet.getString(3) + " Is admin: " + resultSet.getInt(4));
				
				cashier.setId(resultSet.getInt(1));
				cashier.setUsername(resultSet.getString(2));
				cashier.setPassword(resultSet.getString(3));
				cashier.setAdmin(resultSet.getInt(4));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ptmt != null){
					ptmt.close();
				}
				if(resultSet != null) {
					resultSet.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return cashier;
	}

}
