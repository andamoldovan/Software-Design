package repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import database.DBOConnectionFactory;
import database.ShowDTO;


public class ShowRepository implements ShowRepositoryInterface{
	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;
	
	public ShowRepository() {
		
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn;
		conn = DBOConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	
	public ShowDTO retrieve(int id) {
		ShowDTO show = new ShowDTO();
		try {
			String querry = "SELECT * FROM nationaltheatre.show WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			
			while(resultSet.next()) {
//				System.out.println("\nId: " + resultSet.getInt(1)
//					 + " Show Name: " + resultSet.getString(2)
//					 + " Genre: " + resultSet.getString(3)
//					 + " Distribution: " + resultSet.getString(4)
//					 + " Date: " + resultSet.getString(5)
//					 + " Number of tickets: " + resultSet.getInt(6));
				
				show.setId(resultSet.getInt(1));
				show.setTitle(resultSet.getString(2));
				show.setGenre(resultSet.getString(3));
				show.setDistribution(resultSet.getString(4));
				show.setDate(resultSet.getString(5));
				show.setNrTickets(resultSet.getInt(6));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
				if(ptmt != null) {
					ptmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return show;
	}
	
	public List<ShowDTO> retrieveAll() {
		List<ShowDTO> shows = new ArrayList<ShowDTO>();
		ShowDTO show;
		try {
//			String querry = "SELECT nationaltheatre.show.id, nationaltheatre.show.title, nationaltheatre.genre.name," + 
//					        " nationaltheatre.show.distribution, nationaltheatre.show.date, nationaltheatre.show.nrTickets " + 
//					        " FROM nationaltheatre.show "  +
//					        " INNER JOIN nationaltheatre.genre ON nationaltheatre.show.genre_id = nationaltheatre.genre.id " +
//					        " ORDER BY nationaltheatre.show.id";
			String querry = "SELECT * FROM nationaltheatre.show";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			resultSet = ptmt.executeQuery();
			while(resultSet.next()) {
//				System.out.println("\nId: " + resultSet.getInt(1)
//				 + " Show Name: " + resultSet.getString(2)
//				 + " Genre: " + resultSet.getString(3)
//				 + " Distribution: " + resultSet.getString(4)
//				 + " Date: " + resultSet.getString(5)
//				 + " Number of tickets: " + resultSet.getInt(6));
				show = new ShowDTO();
				show.setId(resultSet.getInt(1));
				show.setTitle(resultSet.getString(2));
				show.setGenre(resultSet.getString(3));
				show.setDistribution(resultSet.getString(4));
				show.setDate(resultSet.getString(5));
				show.setNrTickets(resultSet.getInt(6));
				shows.add(show);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
				if(ptmt != null) {
					ptmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return shows;
	}
	
	public void create(ShowDTO show) {
		try {
			//String constraintsOf = "SET FOREIGN_KEY_CHECKS=0";
		//	String constraintsOn = "SET FOREIGN_KEY_CHECKS=1";
			String querry = "INSERT INTO nationaltheatre.show(title, genre, distribution, date, nrTickets) VALUES(?, ?, ?, ?, ?)";
			connection = getConnection();
			
			ptmt = connection.prepareStatement(querry);
			ptmt.setString(1,  show.getTitle());
			ptmt.setString(2,  show.getGenre());
			ptmt.setString(3, show.getDistribution());
			ptmt.setString(4, show.getDate());
			ptmt.setInt(5, show.getNrTickets());
			ptmt.executeUpdate();
			System.out.println("Show inserted succesfully\n");
			
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
	
	public void update(ShowDTO show) {
		try {
			String querry = "UPDATE nationaltheatre.show SET title = ?, genre = ?, distribution = ?, date = ?, nrTickets = ? WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setString(1,  show.getTitle());
			ptmt.setString(2, show.getGenre());
			ptmt.setString(3,  show.getDistribution());
			ptmt.setString(4, show.getDate());
			ptmt.setInt(5, show.getNrTickets());
			ptmt.setInt(6, show.getId());
			ptmt.executeUpdate();
			System.out.printf("Show: %d has been updated\n", show.getId());
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
	
	public void delete(int id) {
		try {
			String querry = "DELETE FROM nationaltheatre.show WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1,  id);
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
}
