package test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.CashierModel;
import model.ShowModel;
import service.CashierService;
import service.CashierServiceInterface;
import service.ShowService;
import service.ShowServiceInterface;
import service.TicketService;
import service.TicketServiceInterface;

public class TicketModelTest {
	
	ShowServiceInterface service;
	CashierServiceInterface cService;
	
	@Before
	public void setUp() {
		service = new ShowService();
		cService = new CashierService();
	}
	
	@Test
	public void testTicketExceedinLimit() {
		ShowModel show = service.retrieveShowInfo("Carmen");
		CashierModel cashier = new CashierModel();
		cashier.setUsername("anda");
		cashier.setPassword("anda");
		
		cashier.sellTicket(show);
		Assert.assertEquals(show.getNrTickets(), 0);
		cashier.sellTicket(show);
		Assert.assertEquals(show.getNrTickets(), 0);
	}
}
