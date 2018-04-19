package repository;

import java.util.List;
import database.TicketDTO;

public interface TicketRepositoryInterface {
	
	List<TicketDTO> retrieveAll();
	
	TicketDTO retrieve(int id);
	
	List<TicketDTO> retrieveByShowId(int id);
	
	void create(TicketDTO ticket);
	
	void update(TicketDTO ticket);
	
	void delete(int id);

}
