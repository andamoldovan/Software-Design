package service;

import java.util.List;

import model.ShowModel;

public interface ShowServiceInterface {
	List<ShowModel> retrieveAll();
	
	ShowModel retrieve(int id);
	
	void create(ShowModel show);
	
	void update(ShowModel show);
	
	void delete(int id);
	
	int retrieveIdByTitle(String title, String date);
	
	ShowModel retrieveShowInfo(String title);
	
	boolean maxTickets(int nrTickets);
}
