package service;

import java.util.List;

import model.CashierModel;

public interface CashierServiceInterface {
	List<CashierModel> retrieveAll();
	
	CashierModel retrieve(int id);
	
	void create(CashierModel cashier);
	
	void update(CashierModel cashier);
	
	void delete(int id);
	
	CashierModel retrieveCashierByCredentials(String username, String password);
	
	boolean validateAdmin(String username, String password);
	
	boolean validateCashier(String username, String password);
}
