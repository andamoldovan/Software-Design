package service;

import java.util.ArrayList;
import java.util.List;

import database.CashierDTO;
import model.CashierModel;
import repository.CashierRepository;
import repository.CashierRepositoryInterface;

public class CashierService implements CashierServiceInterface{

	private final CashierRepositoryInterface repository;
	
	public CashierService() {
		repository = new CashierRepository();
	}
	
	@Override
	public List<CashierModel> retrieveAll() {
		List<CashierModel> result = new ArrayList<CashierModel>();
		List<CashierDTO> cashiers = repository.retrieveAll();
		
		for(CashierDTO item : cashiers) {
			CashierModel model = new CashierModel();
			model.setId(item.getId());
			model.setUsername(item.getUsername());
			model.setPassword(item.getPassword());
			model.setAdmin(item.getAdmin());
			result.add(model);
		}
		return result;
	}

	@Override
	public CashierModel retrieve(int id) {
		CashierModel model = new CashierModel();
		CashierDTO item = repository.retrieve(id);
		
		model.setId(item.getId());
		model.setUsername(item.getUsername());
		model.setPassword(item.getPassword());
		model.setAdmin(item.getAdmin());
		return model;
	}

	@Override
	public void create(CashierModel cashier) {
		CashierDTO c = new CashierDTO();
		
		c.setId(cashier.getId());
		c.setUsername(cashier.getUsername());
		c.setPassword(cashier.getPassword());
		c.setAdmin(cashier.getAdmin());
		
		repository.create(c);
	}

	@Override
	public void update(CashierModel cashier) {
		CashierDTO c = new CashierDTO();
		
		c.setId(cashier.getId());
		c.setUsername(cashier.getUsername());
		c.setPassword(cashier.getPassword());
		c.setAdmin(cashier.getAdmin());
		
		repository.update(c);
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public CashierModel retrieveCashierByCredentials(String username, String password) {
		CashierModel model = new CashierModel();
		CashierDTO item = repository.retrieveCashierByCredentials(username, password);
		
		model.setId(item.getId());
		model.setUsername(item.getUsername());
		model.setPassword(item.getPassword());
		model.setAdmin(item.getAdmin());
		return model;
	}

	@Override
	public boolean validateAdmin(String username, String password) {
		List<CashierDTO> users = repository.retrieveAll();
		CashierDTO cash;
		for(CashierDTO u : users) {
			cash = new CashierDTO();
			cash.setUsername(username);
			cash.setPassword(password);
			if(u.getUsername().equals(cash.getUsername()) && u.getPassword().equals(cash.getEncryptedPassword())) {
				if(u.getAdmin() == 1) return true;
			}
		}
		return false;
	}

	@Override
	public boolean validateCashier(String username, String password) {
		List<CashierDTO> users = repository.retrieveAll();
		CashierDTO cash;
		for(CashierDTO u : users) {
			cash = new CashierDTO();
			cash.setUsername(username);
			cash.setPassword(password);
			if(u.getUsername().equals(cash.getUsername()) && u.getPassword().equals(cash.getEncryptedPassword())) {
				if(u.getAdmin() == 0) return true;
			}
		}
		return false;
	}

}
