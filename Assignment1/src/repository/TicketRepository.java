package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBOConnectionFactory;
import database.TicketDTO;

public class TicketRepository implements TicketRepositoryInterface{
	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;
	
	public TicketRepository() {
		
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn;
		conn = DBOConnectionFactory.getInstance().getConnection();
		return conn;
	}

	@Override
	public List<TicketDTO> retrieveAll() {
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		TicketDTO ticket;
		try {
			String querry = "SELECT * FROM nationaltheatre.ticket";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			resultSet = ptmt.executeQuery();
			
			while(resultSet.next()) {
//				System.out.println("\nId: " + resultSet.getInt(1) 
//				 + " Show id: " + resultSet.getInt(2)
//				 + " Row: " + resultSet.getInt(3)
//				 + " Col: " + resultSet.getInt(4));
				ticket = new TicketDTO();
				ticket.setId(resultSet.getInt(1));
				ticket.setShowId(resultSet.getInt(2));
				ticket.setRow(resultSet.getInt(3));
				ticket.setCol(resultSet.getInt(4));
				tickets.add(ticket);
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
		return tickets;
	}

	@Override
	public TicketDTO retrieve(int id) {
		TicketDTO ticket = new TicketDTO();
		try {
			String querry = "SELECT * FROM nationaltheatre.ticket ORDER BY nationaltheatre.ticket.row, nationaltheatre.ticket.col WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			
			while(resultSet.next()) {
//				System.out.println("\nId: " + resultSet.getInt(1) 
//				 + " Show id: " + resultSet.getInt(2)
//				 + " Row: " + resultSet.getInt(3)
//				 + " Col: " + resultSet.getInt(4));
//				
				ticket.setId(resultSet.getInt(1));
				ticket.setShowId(resultSet.getInt(2));
				ticket.setRow(resultSet.getInt(3));
				ticket.setCol(resultSet.getInt(4));
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
		return ticket;
	}

	@Override
	public void create(TicketDTO ticket) {
		try {
			String querry = "INSERT INTO nationaltheatre.ticket(show_id, row, col)" + 
						" SELECT s.id, ?, ? FROM nationaltheatre.show s WHERE s.id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1, ticket.getRow());
			ptmt.setInt(2, ticket.getCol());
			ptmt.setInt(3,  ticket.getShowId());
			ptmt.executeUpdate();
			System.out.println("Insert into ticket executed succesfully");
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
	}

	@Override
	public void update(TicketDTO ticket) {
		try {
			String querry = "UPDATE nationaltheatre.ticket SET show_id = ?, row = ?, col = ? WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1, ticket.getShowId());
			ptmt.setInt(2, ticket.getRow());
			ptmt.setInt(3,  ticket.getCol());
			ptmt.setInt(4, ticket.getId());
			ptmt.executeUpdate();
			
			System.out.println("Table ticket updated succesfully at id: " + ticket.getId());
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
	}

	@Override
	public void delete(int id) {
		try {
			String querry = "DELETE FROM nationaltheatre.ticket WHERE id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1, id);
			ptmt.executeUpdate();
			
			System.out.println("Delete from ticket executed succesfully");
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

	@Override
	public List<TicketDTO> retrieveByShowId(int id) {
		List<TicketDTO> result = new ArrayList<TicketDTO>();
		TicketDTO ticket;
		try {
			String querry = "SELECT ticket.id, ticket.show_id, ticket.row, ticket.col FROM nationaltheatre.ticket" +
							" INNER JOIN nationaltheatre.show ON nationaltheatre.show.id = nationaltheatre.ticket.show_id" +
							" WHERE nationaltheatre.show.id = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(querry);
			ptmt.setInt(1, id);
			resultSet = ptmt.executeQuery();
			
			while(resultSet.next()) {
				ticket = new TicketDTO();
				ticket.setId(resultSet.getInt(1));
				ticket.setShowId(resultSet.getInt(2));
				ticket.setRow(resultSet.getInt(3));
				ticket.setCol(resultSet.getInt(4));
				
				result.add(ticket);
			}
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
		return result;
	}
	
}
