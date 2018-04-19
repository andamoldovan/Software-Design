package test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.Assert;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import model.CashierModel;
import repository.CashierRepository;
import service.CashierService;
import service.CashierServiceInterface;

public class CashierMondelTest {
//	@Mock
//	private CashierServiceInterface service;
//	@Mock
//	private List<CashierModel> list;
//	@Mock
//	private CashierModel cashier;
//	@Mock
//	private CashierModel mockCashier;
//	
//	private String pass;
//	@Mock private String u, p, res;
	CashierServiceInterface service;
	@Before
	public void setUp() {
		//cashier = mock(CashierRepository.class);
		service = new CashierService();
		
	}
	
	@Test
	public void testCashierPassword() {
		CashierModel cashier = new CashierModel();
		cashier.setUsername("anda");
		cashier.setPassword("anda");
		
		List<CashierModel> list = service.retrieveAll();
		for(CashierModel c : list) {
			if(c.getUsername().equals(cashier.getUsername()))
				Assert.assertEquals(c.getPassword(), cashier.getEncriptedPassword());
		}
		
		
	}
//	@Before
//	public void setUp() {
//		list = mock(ArrayList.class);
//		//cashier = mock(CashierModel.class);
//		service = mock(CashierService.class);
//		//mockCashier = mock(CashierModel.class);
//		when(service.retrieveAll()).thenReturn(list);
//		//when(cashier.getPassword()).thenReturn(anyString());
//		//when(service.retrieveCashierByCredentials(anyString(), anyString())).thenReturn(mockCashier);
//	}
//	
//	@Test
//	public void testListNotNull() {
//		assertNotNull(service.retrieveAll());
//	}
//	
//	
//	@Before
//	public void testSetUp() {
//		cashier = mock(CashierModel.class);
//		mockCashier = mock(CashierModel.class);
//		when(cashier.getPassword()).thenReturn(pass);
//		//when(service.retrieveCashierByCredentials(u, p)).thenReturn(mockCashier);
//		//when(service.retrieveCashierByCredentials(u, p).getPassword()).thenReturn(res);
//		when(service.retrieve(12)).thenReturn(mockCashier);
//		when(service.retrieve(12).getPassword()).thenReturn(u);
//		
//;	}
//	@Test
//	public void testPasswordEncryption() {
//		CashierModel c = new CashierModel();
//		c.setPassword("anda");
//		c.setUsername("anda");
//		//System.out.println(service.retrieveCashierByCredentials("anda", "anda"));
//		assertNotNull(service.retrieve(12));
//		System.out.println(mockCashier.getEncriptedPassword());
//		//assertSame(c.getEncriptedPassword(), service.retrieveCashierByCredentials("anda", "anda").getPassword());
//		
//	}
}
