package service;

import java.util.ArrayList;
import java.util.List;

import database.ShowDTO;
import database.TicketDTO;
import model.TicketModel;
import repository.ShowRepository;
import repository.ShowRepositoryInterface;
import repository.TicketRepository;
import repository.TicketRepositoryInterface;

public class TicketService implements TicketServiceInterface{
private final TicketRepositoryInterface repository;
private final ShowRepositoryInterface sRepository;

	public TicketService() {
		repository = new TicketRepository();
		sRepository = new ShowRepository();
	}

	@Override
	public List<TicketModel> retrieveAll() {
		List<TicketDTO> tickets = repository.retrieveAll();
		List<TicketModel> result = new ArrayList<TicketModel>();
		
		for(TicketDTO item : tickets) {
			TicketModel model = new TicketModel();
			model.setId(item.getId());
			model.setShowId(item.getShowId());
			model.setRow(item.getRow());
			model.setCol(item.getCol());
			result.add(model);
		}
		return result;
	}

	@Override
	public TicketModel retrieve(int id) {
		TicketDTO item = repository.retrieve(id);
		TicketModel model = new TicketModel();
		
		model.setId(item.getId());
		model.setShowId(item.getShowId());
		model.setRow(item.getRow());
		model.setCol(item.getCol());
		return model;
	}

	@Override
	public void create(TicketModel show) {
		TicketDTO s = new TicketDTO();
		s.setId(show.getId());
		s.setShowId(show.getShowId());
		s.setRow(show.getRow());
		s.setCol(show.getCol());
		repository.create(s);
		
	}

	@Override
	public void update(TicketModel show) {
		TicketDTO s = new TicketDTO();
		s.setId(show.getId());
		s.setShowId(show.getShowId());
		s.setRow(show.getRow());
		s.setCol(show.getCol());
		
		repository.update(s);
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public List<TicketModel> retrieveByShowId(int id) {
		List<TicketDTO> tickets = repository.retrieveByShowId(id);
		List<TicketModel> result = new ArrayList<TicketModel>();
		
		for(TicketDTO item : tickets) {
			TicketModel model = new TicketModel();
			model.setId(item.getId());
			model.setShowId(item.getShowId());
			model.setRow(item.getRow());
			model.setCol(item.getCol());
			result.add(model);
		}
		return result;
	}
	
	@Override
	public boolean validateTicket(String title, int nrTickets, int row, int col) {
		List<ShowDTO> shows = sRepository.retrieveAll();
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		
		for(ShowDTO s: shows) {
			if(s.getTitle().equals(title) && s.getNrTickets() == nrTickets) {
				tickets = repository.retrieveByShowId(s.getId());
				break;
			}
		}
		if(tickets.isEmpty() == false) {
			for(TicketDTO t : tickets) {
				if(t.getRow() == row && t.getCol() == col)
					return false;
			}
		}
		return true;
	}

}
