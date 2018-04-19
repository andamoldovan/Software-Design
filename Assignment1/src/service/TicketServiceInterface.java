package service;

import java.util.List;
import model.TicketModel;

public interface TicketServiceInterface {
	List<TicketModel> retrieveAll();
	
	List<TicketModel> retrieveByShowId(int id);
	
	TicketModel retrieve(int id);
	
	void create(TicketModel show);
	
	void update(TicketModel show);
	
	void delete(int id);
	
	boolean validateTicket(String title, int nrTickets, int row, int col);
}
