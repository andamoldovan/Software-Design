package repository;

import java.util.List;
import database.CashierDTO;

public interface CashierRepositoryInterface {
	
	public CashierDTO retrieve(int id);
	
	List<CashierDTO> retrieveAll();
	
	void create(CashierDTO cashier);
	
	void update(CashierDTO cashier);
	
	void delete(int id);
	
	CashierDTO retrieveCashierByCredentials(String username, String password);
}
