package service;

import java.util.ArrayList;
import java.util.List;

import database.ShowDTO;
import model.ShowModel;
import repository.ShowRepository;
import repository.ShowRepositoryInterface;

public class ShowService implements ShowServiceInterface{
	private final ShowRepositoryInterface repository;
	
	public ShowService() {
		repository = new ShowRepository();
	}

	@Override
	public List<ShowModel> retrieveAll() {
		List<ShowDTO> shows = repository.retrieveAll();
		List<ShowModel> result = new ArrayList<ShowModel>();
		
		for(ShowDTO item : shows) {
			ShowModel model = new ShowModel();
			model.setId(item.getId());
			model.setTitle(item.getTitle());
			model.setGenre(item.getGenre());
			model.setDistribution(item.getDistribution());
			model.setDate(item.getDate());
			model.setNrTickets(item.getNrTickets());
			result.add(model);
		}
		return result;
	}

	@Override
	public ShowModel retrieve(int id) {
		ShowDTO item = repository.retrieve(id);
		ShowModel model = new ShowModel();
		
		model.setId(item.getId());
		model.setTitle(item.getTitle());
		model.setGenre(item.getGenre());
		model.setDistribution(item.getDistribution());
		model.setDate(item.getDate());
		model.setNrTickets(item.getNrTickets());
		
		return model;
	}

	@Override
	public void create(ShowModel show) {
		ShowDTO s = new ShowDTO();
		s.setId(show.getId());
		s.setTitle(show.getTitle());
		s.setGenre(show.getGenre());
		s.setDistribution(show.getDistribution());
		s.setDate(show.getDate());
		s.setNrTickets(show.getNrTickets());
		
		repository.create(s);
		
	}

	@Override
	public void update(ShowModel show) {
		ShowDTO s = new ShowDTO();
		s.setId(show.getId());
		s.setTitle(show.getTitle());
		s.setGenre(show.getGenre());
		s.setDistribution(show.getDistribution());
		s.setDate(show.getDate());
		s.setNrTickets(show.getNrTickets());
		
		repository.update(s);
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}
	
	@Override
	public int retrieveIdByTitle(String title, String date) {
		List<ShowDTO> shows = repository.retrieveAll();
		for(ShowDTO s : shows) {
			if(s.getTitle().equals(title) && s.getDate().equals(date))
				return s.getId();
		}
		return 0;
	}
	
	@Override
	public ShowModel retrieveShowInfo(String title) {
		List<ShowDTO> shows = repository.retrieveAll();
		ShowModel show = new ShowModel();
		for(ShowDTO s : shows) {
			if(s.getTitle().equals(title)) {
				show.setDistribution(s.getDistribution());
				show.setDate(s.getDate());
				show.setGenre(s.getGenre());
				show.setId(s.getId());
				show.setNrTickets(s.getNrTickets());
			}
		}
		return show;
	}
	
	@Override
	public boolean maxTickets(int nrTickets) {
		if(nrTickets > 100) return false;
		else return true;
	}
}
