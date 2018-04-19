package presentation;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.CashierModel;
import model.ShowModel;
import model.TicketModel;
import service.CashierService;
import service.CashierServiceInterface;
import service.ShowService;
import service.ShowServiceInterface;
import service.TicketService;
import service.TicketServiceInterface;

public class CashierScene {
	private ComboBox<String> title;
	private TextField day;
	private TextField hour;

	private ComboBox<String> rowNr;
	private ComboBox<String> colNr;
	private TextField nrTickets;
	private Button confirmBtn;
	private Button cancelBtn;
	private Button editReservation;
	private Button cancelReservation;

	private Text action;
	ListView<String> soldTicketsList;
	
	private GridPane cashierGrid;
	
	private ShowServiceInterface service;
	private TicketServiceInterface tService;
	private CashierServiceInterface cService;
	
	private int listRow, listCol;
	
	private String username;
	private String password;
	
	public CashierScene(GridPane cashierGrid, String username, String password) {
		title = new ComboBox<String>();
		day = new TextField();
		hour = new TextField();
		rowNr = new ComboBox<String>();
		colNr = new ComboBox<String>();
		nrTickets = new TextField();
		confirmBtn = new Button("Confirm");
		cancelBtn = new Button("Cancel");
		editReservation = new Button("Edit reservation");
		cancelReservation = new Button("Cancel reservation");

		
		action = new Text();
		soldTicketsList = new ListView<String>();
		
		service = new ShowService();
		tService = new TicketService();
		cService = new CashierService();
		
		listRow = 0; 
		listCol = 0;
		
		this.cashierGrid = cashierGrid;
		this.username = username;
		this.password = password;
	}
	
	protected void arrangeScene() {
		cashierGrid.add(new Label("Title"), 0, 2);
		cashierGrid.add(new Label("Date"), 0, 3);
		cashierGrid.add(new Label("Tickets\n available"), 0, 4);
		cashierGrid.add(new Label("Select"), 0, 5);
		cashierGrid.add(new Label("Row"), 1, 5);
		cashierGrid.add(new Label("Col"), 1, 6);
		
		rowNr.setPrefSize(60, 10);
		colNr.setPrefSize(60, 10);
		cashierGrid.add(title, 1, 2);
		cashierGrid.add(rowNr, 2, 5);
		cashierGrid.add(colNr, 2, 6);
		nrTickets.setPrefSize(10, 10);
		cashierGrid.add(nrTickets, 1, 4);
		nrTickets.setEditable(false);
		
		cashierGrid.add(day, 1, 3);
		day.setPrefSize(70,  10);
		day.setEditable(false);
		cashierGrid.add(hour, 2, 3);
		hour.setPrefSize(70,  10);
		hour.setEditable(false);
		
		cashierGrid.add(confirmBtn, 0, 7);
		cashierGrid.add(cancelBtn, 1, 7);
		cashierGrid.add(editReservation, 2, 9);
		cashierGrid.add(cancelReservation, 2, 10);

		
		confirmBtn.setPrefSize(80,  10);
		cancelBtn.setPrefSize(80,  10);
		editReservation.setPrefSize(120,  10);
		cancelReservation.setPrefSize(120,  10);
		
		cashierGrid.add(new Label("Ticket Sold for the current show"), 3, 8);
		cashierGrid.add(soldTicketsList, 3, 9, 1, 4);
		soldTicketsList.setPrefSize(200,  100);
		cashierGrid.add(action, 3,  5);
		
		rowNr.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10");
		colNr.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10");
		
		rowNr.getSelectionModel().selectFirst();
		colNr.getSelectionModel().selectFirst();
		
		List<ShowModel> shows = service.retrieveAll();
		for(ShowModel s : shows) {
			title.getItems().add(s.getTitle());
		}
		
		title.setOnAction(e -> populateFields(title.getSelectionModel().getSelectedItem()));
		title.getSelectionModel().selectFirst();
		populateFields(title.getSelectionModel().getSelectedItem());
		
		confirmBtn.setOnAction(e -> buttonAction("confirm"));
		cancelBtn.setOnAction(e ->buttonAction("cancel"));
		editReservation.setOnAction(e -> reservationAction("edit"));
		cancelReservation.setOnAction(e -> reservationAction("cancel"));
		soldTicketsList.setOnMouseClicked(EventHandler -> listEvent());
		
	}
	
	
	private void populateFields(String title) {
		List<TicketModel> tickets = new ArrayList<TicketModel>();
		ObservableList<String> sold = FXCollections.<String>observableArrayList();
		
		ShowModel show = service.retrieveShowInfo(title);
		sold.clear();
		soldTicketsList.getItems().clear();
		day.setText(show.getDate().substring(0, 10));
		hour.setText(show.getDate().substring(11, 16));
		nrTickets.setText(Integer.toString(show.getNrTickets()));
		
		tickets = tService.retrieveByShowId(show.getId());
		for(TicketModel t : tickets) {
			sold.add("Row: " + t.getRow() + " Seat: " + t.getCol());
		}
		soldTicketsList.getItems().addAll(sold);
	}
	
	
	private void buttonAction(String command) {
		if(command.equals("confirm")) {
			CashierModel cashier = cService.retrieveCashierByCredentials(username, password);
			if(tService.validateTicket(title.getSelectionModel().getSelectedItem(), Integer.parseInt(nrTickets.getText()), Integer.parseInt(rowNr.getSelectionModel().getSelectedItem()), Integer.parseInt(colNr.getSelectionModel().getSelectedItem()))){
				List<ShowModel> shows = service.retrieveAll();
				ShowModel show = new ShowModel();
				TicketModel ticket = new TicketModel();;
				
				for(ShowModel s: shows) {
					if(s.getTitle().equals(title.getSelectionModel().getSelectedItem()) && s.getNrTickets() == Integer.parseInt(nrTickets.getText())) {
						ticket.setShowId(s.getId());
						ticket.setRow(Integer.parseInt(rowNr.getSelectionModel().getSelectedItem()));
						ticket.setCol(Integer.parseInt(colNr.getSelectionModel().getSelectedItem()));
						
						show.setId(s.getId());
						show.setTitle(s.getTitle());
						show.setDistribution(s.getDistribution());
						show.setDate(s.getDate());
						show.setGenre(s.getGenre());
						show.setNrTickets(s.getNrTickets());
					}
				}
				if(ticket != null) {
					cashier.sellTicket(show);
					if(show.getNrTickets() >= 0) {
						tService.create(ticket);
						show.setNrTickets(show.getNrTickets());
						service.update(show);
						action.setFill(Color.FIREBRICK);
						action.setText("Ticket purchased succesfully");
						nrTickets.setText(Integer.toString(show.getNrTickets()));
						populateFields(show.getTitle());
					}else {
						action.setFill(Color.FIREBRICK);
						action.setText("The show is sold out!");
					}
				}
				
			}else {
				action.setFill(Color.FIREBRICK);
				action.setText("Place is already selected! Please chose another one");
			}
		}else {
			if(command.equals("cancel")) {
				action.setText("");
				rowNr.getSelectionModel().selectFirst();
				colNr.getSelectionModel().selectFirst();
			}
		}
	}
	
	private void listEvent(){
		//System.out.println(soldTicketsList.getSelectionModel().getSelectedItem());
		String str = soldTicketsList.getSelectionModel().getSelectedItem();
		char[] c = str.toCharArray();
		if(Character.isDigit(c[6])) {
			listRow = Character.getNumericValue(c[5]) * 10 + Character.getNumericValue(c[6]);
		}else
			listRow = Character.getNumericValue(c[5]);
		
		if(Character.isDigit(c[c.length - 2]))
			listCol = Character.getNumericValue(c[c.length - 2]) * 10 + Character.getNumericValue(c[c.length - 1]);
		else
			listCol = Character.getNumericValue(c[c.length - 1]);
	}
	
	private void reservationAction(String command) {
		List<ShowModel> shows = service.retrieveAll();
		List<TicketModel> tickets = tService.retrieveAll();
		TicketModel ticket;
		if(command.equals("edit")) {
			for(TicketModel t : tickets) {
				ticket = new TicketModel();
				if(t.getRow() == listRow && t.getCol() == listCol) {
					ticket.setId(t.getId());
					for(ShowModel s : shows) {
						if(s.getTitle().equals(title.getSelectionModel().getSelectedItem()) && s.getDate().equals(day.getText() + " " + hour.getText())) {
							ticket.setShowId(s.getId());
							ticket.setRow(Integer.parseInt(rowNr.getSelectionModel().getSelectedItem()));
							ticket.setCol(Integer.parseInt(colNr.getSelectionModel().getSelectedItem()));
							if(tService.validateTicket(title.getSelectionModel().getSelectedItem(), Integer.parseInt(nrTickets.getText()), Integer.parseInt(rowNr.getSelectionModel().getSelectedItem()), Integer.parseInt(colNr.getSelectionModel().getSelectedItem()))){
								tService.update(ticket);
								populateFields(s.getTitle());
								action.setFill(Color.FIREBRICK);
								action.setText("Reservation modified");
							}else {
								action.setFill(Color.FIREBRICK);
								action.setText("Place is already selected! Please chose another one");
							}
						}
					}
					
				}
			}
		}
		if(command.equals("cancel")) {
			for(TicketModel t : tickets) {
				if(t.getRow() == listRow && t.getCol() == listCol) {
					tService.delete(t.getId());
					populateFields(title.getSelectionModel().getSelectedItem());
					action.setFill(Color.FIREBRICK);
					action.setText("Reservation canceled");
				}
			}
		}
	}
	
	protected boolean logOut() {
		return true;
	}
}
