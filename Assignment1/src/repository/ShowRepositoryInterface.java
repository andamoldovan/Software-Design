package repository;

import java.util.List;
import database.ShowDTO;

public interface ShowRepositoryInterface {
	
	public ShowDTO retrieve(int id);
	
	List<ShowDTO> retrieveAll();
	
	void create(ShowDTO cashier);
	
	void update(ShowDTO cashier);
	
	void delete(int id);
}
