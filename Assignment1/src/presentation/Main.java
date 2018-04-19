package presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import database.CashierDTO;
import file.XMLDocument;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CashierModel;
import model.ShowModel;
import model.TicketModel;
import service.CashierService;
import service.CashierServiceInterface;
import service.GenresEnum;
import service.ShowService;
import service.ShowServiceInterface;
import service.TicketService;
import service.TicketServiceInterface;

//class Main{
//	public static void main(String[] args) {
//		ShowService service = new ShowService();
//		List<ShowModel> show = service.retrieveAll();
//		int i = 1;
//		for(ShowModel s : show) {
//			System.out.println(i + " --> " + s.getTitle());
//			i++;
//		}
//		
//	}
//}

public class Main extends Application{
	
	private Stage window;
	private Scene cashierScene, adminScene, mainScene;
	
	private int cashierUpdateStatus;
	CashierModel cashier;
	

	
	//show
	private ComboBox<String> titleComboBox;
	private ComboBox<String> genre;
	private TextField distributionText;
	private ComboBox<String> day;
	private ComboBox<String> month;
	private ComboBox<String> year;
	private ComboBox<String> hour;
	private ComboBox<String> minute;
	private TextField nrTicketsText;
	private Text fileMessage;
	private Text validationText;
	
	//cashier
	private ComboBox<String> usernameComboBox;
	private TextField passwordText;
	private Button insertBtn;
	private Button updateUsernameBtn;
	private Button updatePasswordBtn;
	private Button removeBtn;
	private Button confirmBtn;
	private Button cancelBtn;
	private Text actionMessage;
	private Text loginMessage;
	
	
	private final CashierServiceInterface cService;
	private final ShowServiceInterface sService;
	
	
	public Main() {
		cService = new CashierService();
		sService = new ShowService();
		cashierUpdateStatus = 0;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		GridPane mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		mainGrid.setPadding(new Insets(25, 25, 25, 25));
		
		
		Text mainSceneTitle = new Text("Welcome!");
		mainSceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		Label usernameLabel = new Label("username");
		Label passwordLabel = new Label("password");
		
		TextField usernameTextField = new TextField();
		TextField passwordTextField = new TextField();
		
		Button loginBtn = new Button("Log in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(loginBtn);
		
		loginMessage = new Text();
		mainGrid.add(loginMessage, 1, 5);
		mainGrid.add(mainSceneTitle, 1, 0, 2, 1);
		mainGrid.add(usernameLabel, 0, 1);
		mainGrid.add(usernameTextField, 1, 1);
		usernameTextField.setPrefSize(200,  10);
		mainGrid.add(passwordLabel, 0, 2);
		mainGrid.add(passwordTextField, 1, 2);
		passwordTextField.setPrefSize(200,  10);
		mainGrid.add(hbBtn, 1, 4);
		
		loginBtn.setOnAction(e -> actionOnLogIn(usernameTextField.getText(), passwordTextField.getText()));

		//cashier scene
		GridPane cashierGrid = new GridPane();
		cashierGrid.setAlignment(Pos.CENTER);
		cashierGrid.setHgap(10);
		cashierGrid.setVgap(10);
		cashierGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Text cashierSceneTitle = new Text("Welcome Cashier!");
		cashierSceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		cashierScene = new Scene(cashierGrid, 600, 500);
		
		cashierGrid.add(cashierSceneTitle, 1,  0, 2, 1);
		CashierScene c = new CashierScene(cashierGrid, usernameTextField.getText(), passwordTextField.getText());
		c.arrangeScene();
		
		//administrator scene
		
		GridPane adminGrid = new GridPane();
		adminGrid.setAlignment(Pos.CENTER);
		adminGrid.setHgap(10);
		adminGrid.setVgap(10);
		adminGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Text adminSceneTitle = new Text("Welcome Administrator!");
		adminSceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		adminScene = new Scene(adminGrid, 800, 400);
		
		adminGrid.add(adminSceneTitle, 2,  0, 3, 1);
		
		Button cashierBtn = new Button("Manage cashiers");
		
		cashierBtn.setOnAction(e -> cashierAdministration());
		adminGrid.add(cashierBtn, 1, 1);
		manageShows(adminGrid);
		
		//log in scene
		mainScene = new Scene(mainGrid, 400, 400);
		window.setScene(mainScene);
		window.show();
	}
	
	private void cashierAdministration() {
		Stage cashierStage = new Stage();
		
		GridPane cashierGrid = new GridPane();
		cashierGrid.setAlignment(Pos.CENTER);
		cashierGrid.setHgap(10);
		cashierGrid.setVgap(10);
		cashierGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Text cashierSceneTitle = new Text("Manage cashiers");
		cashierSceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		cashierGrid.add(cashierSceneTitle, 1,  0, 1, 1);
		
		actionMessage = new Text();
		
		Label usernameLabel = new Label("Cashier username");
		Label passwordLabel = new Label("Cashier password");
		usernameComboBox = new ComboBox<String>();
		passwordText = new TextField();
		passwordText.setPrefSize(300, 10);
		passwordText.setEditable(false);
		
		insertBtn = new Button("Insert cashier");
		insertBtn.setPrefSize(130, 10);
		updateUsernameBtn = new Button("Update username");
		updateUsernameBtn.setPrefSize(130, 10);
		updatePasswordBtn = new Button("Update password");
		updatePasswordBtn.setPrefSize(130, 10);
		removeBtn = new Button("Delete");
		removeBtn.setPrefSize(130, 10);
		confirmBtn = new Button("Comfirm changes");
		confirmBtn.setPrefSize(130, 10);
		cancelBtn = new Button("Cancel changes");
		confirmBtn.setPrefSize(130, 10);
	
		
		cashierGrid.add(usernameLabel, 1, 1);
		cashierGrid.add(usernameComboBox, 2, 1);
		cashierGrid.add(passwordLabel, 1, 2);
		cashierGrid.add(passwordText, 2, 2);
		
		cashierGrid.add(insertBtn, 1, 3);
		cashierGrid.add(updateUsernameBtn, 1, 4);
		cashierGrid.add(updatePasswordBtn, 2, 4);
		cashierGrid.add(removeBtn, 1, 5);
		cashierGrid.add(confirmBtn, 1, 6);
		cashierGrid.add(cancelBtn, 2, 6);
		
		cashierGrid.add(actionMessage, 1, 8, 3, 1);
		
		Scene modifierScene = new Scene(cashierGrid, 600, 400);
		cashierStage.setScene(modifierScene);
		cashierStage.show();
		
		
		List<CashierModel> cashiers = cService.retrieveAll();
		
		for(CashierModel c : cashiers) {
			if(c.getAdmin() == 0)
				usernameComboBox.getItems().add(c.getUsername());
		}
		
		usernameComboBox.setOnAction(e -> arrangeCashiers(usernameComboBox.getSelectionModel().getSelectedItem()));
		usernameComboBox.getSelectionModel().selectFirst();
		
		insertBtn.setOnAction(e -> administratorCashierActions("insert"));
		updateUsernameBtn.setOnAction(e -> administratorCashierActions("update username"));
		updatePasswordBtn.setOnAction(e -> administratorCashierActions("update password"));
		removeBtn.setOnAction(e -> administratorCashierActions("delete"));
		confirmBtn.setOnAction(e -> administratorCashierActions("confirm"));
		cancelBtn.setOnAction(e -> administratorCashierActions("cancel"));
	}
	
	/**
	 * Manages the administrator main scene
	 * @param grid
	 */
	private void manageShows(GridPane grid) {
		
		Label titleLabel = new Label("Show title");
		Label genreLabel = new Label("Show genre");
		Label distributionLabel = new Label("Show Distribution");
		Label dateLabel = new Label("Show Date");
		Label nrTickets = new Label("Number of tickets available");
		

		validationText = new Text();
		fileMessage = new Text();
		
		titleComboBox = new ComboBox<String>();
		genre = new ComboBox<String>();
		distributionText = new TextField();
		
		day = new ComboBox<String>();
		month = new ComboBox<String>();
		year = new ComboBox<String>();
		hour = new ComboBox<String>();
		minute = new ComboBox<String>();
		nrTicketsText = new TextField();
		
		grid.add(titleLabel, 1, 3);
		grid.add(titleComboBox, 2, 3, 5, 1);
		grid.add(genreLabel, 1,  4);
		grid.add(genre, 2, 4, 5, 1);
		grid.add(distributionLabel, 1, 5);
		grid.add(distributionText, 2,  5, 5, 1);
		grid.add(dateLabel, 1,  6);
		grid.add(day, 2, 6);
		grid.add(month, 3, 6);
		grid.add(year, 4, 6);
		grid.add(hour, 5, 6);
		grid.add(minute, 6, 6);
		grid.add(nrTickets, 1,  7);
		grid.add(nrTicketsText, 2,  7);
		grid.add(validationText, 3, 7);
		
		ObservableList<String> list = FXCollections.observableArrayList();
		for(GenresEnum g : EnumSet.allOf(GenresEnum.class)) {
			//genre.getItems().add(g.toString());
			list.add(g.toString());
		}
		genre.getItems().addAll(list);
		
		day.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
							  "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", 
							  "23", "24", "25", "26", "27", "28", "29", "30", "31");
		month.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
		year.getItems().addAll("2018", "2019", "2020");
		hour.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
							   "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24");
		minute.getItems().addAll("00", "15", "30", "45");
		
		Button insertBtn = new Button("Add show");
		insertBtn.setPrefSize(100, 10);
		Button updateBtn = new Button("Update show");
		updateBtn.setPrefSize(100, 10);
		Button deleteBtn = new Button("Delete show");
		deleteBtn.setPrefSize(100, 10);
		Button exportBtn = new Button("Export Tickets");
		exportBtn.setPrefSize(100, 10);
		
		grid.add(insertBtn, 1, 8);
		grid.add(updateBtn, 2, 8);
		grid.add(deleteBtn, 3, 8);
		grid.add(exportBtn, 1, 9);
		
		grid.add(fileMessage, 2, 9);
		//functionality
		List<ShowModel> shows = sService.retrieveAll();
		
		for(ShowModel s : shows) {
			titleComboBox.getItems().add(s.getTitle());
		}
		
		titleComboBox.setOnAction(e -> arrangeShows(titleComboBox.getSelectionModel().getSelectedItem()));
		titleComboBox.setEditable(true);
		titleComboBox.getSelectionModel().selectFirst();
		arrangeShows(titleComboBox.getSelectionModel().getSelectedItem());
		
		//button actions
		insertBtn.setOnAction(e -> administratorShowActions("insert"));
		updateBtn.setOnAction(e -> administratorShowActions("update"));
		deleteBtn.setOnAction(e -> administratorShowActions("delete"));
		exportBtn.setOnAction(e -> exportFileAction());
		
	}
	
	/**
	 * Check username and password on login
	 * @param username
	 * @param password
	 * @return Nothing Appropriate screen will be launched
	 */
	private void actionOnLogIn(String username, String password) {
		if(cService.validateAdmin(username, password) == true) {
			window.setScene(adminScene);
		}else {
			if(cService.validateCashier(username, password) == true) {
				window.setScene(cashierScene);
			}else {
				loginMessage.setFill(Color.CRIMSON);
				loginMessage.setText("Incorrect Usename or Password!");
				System.out.println("incorrect");
			}
		}
	}
	
	/**
	 * Arrange shows in the administrator window
	 * @param title
	 */
	private void arrangeShows(String title) {
		List<ShowModel> shows = sService.retrieveAll();
//		ShowModel show = sService.retrieveShowInfo(title);
//		if(show != null) {
//			genre.getSelectionModel().select(show.getGenre());
//			distributionText.setText(show.getDistribution());
//			day.getSelectionModel().select(show.getDate().substring(0, 2));
//			month.getSelectionModel().select(show.getDate().substring(3, 5));
//			year.getSelectionModel().select(show.getDate().substring(6, 10));
//			hour.getSelectionModel().select(show.getDate().substring(11, 13));
//			minute.getSelectionModel().select(show.getDate().substring(14, 16));
//			nrTicketsText.setText(Integer.toString(show.getNrTickets()));
//		}
		for(ShowModel s : shows) {
			if(s.getTitle().equals(title)) {
				genre.getSelectionModel().select(s.getGenre());
				distributionText.setText(s.getDistribution());
				day.getSelectionModel().select(s.getDate().substring(0, 2));
				month.getSelectionModel().select(s.getDate().substring(3, 5));
				year.getSelectionModel().select(s.getDate().substring(6, 10));
				hour.getSelectionModel().select(s.getDate().substring(11, 13));
				minute.getSelectionModel().select(s.getDate().substring(14, 16));
				nrTicketsText.setText(Integer.toString(s.getNrTickets()));
			}
		}
			
	}
	
	
	private void administratorShowActions(String command) {
		ShowModel model = new ShowModel();
		String date = null;
		
		
		if(command.equals("delete")) {
			List<ShowModel> list = sService.retrieveAll();
			for(ShowModel s : list) {
				if(s.getTitle().equals(titleComboBox.getSelectionModel().getSelectedItem())) {
					int id = s.getId();
					sService.delete(id);
				}
			}
		}else
			if(command.equals("insert")){
				date = day.getSelectionModel().getSelectedItem() + "." + month.getSelectionModel().getSelectedItem() + "." +
					   year.getSelectionModel().getSelectedItem() + " " + hour.getSelectionModel().getSelectedItem() + ":" +
					   minute.getSelectionModel().getSelectedItem();
				
				model.setTitle(titleComboBox.getSelectionModel().getSelectedItem());
				model.setGenre(genre.getSelectionModel().getSelectedItem());
				model.setDistribution(distributionText.getText());
				model.setDate(date);
				model.setNrTickets(Integer.parseInt(nrTicketsText.getText()));
				
				if(model.getTitle() == null || model.getDistribution() == null || model.getNrTickets() == 0) {
					validationText.setFill(Color.CRIMSON);
					validationText.setText("Incorect data!");
				}else
					if(sService.maxTickets(Integer.parseInt(nrTicketsText.getText())) == true) {
						validationText.setText("");
						sService.create(model);
					}else {
						validationText.setFill(Color.CRIMSON);
						validationText.setText("Incorect data!");
					}
			}else 
				if(command.equals("update")) {
					date = day.getSelectionModel().getSelectedItem() + "." + month.getSelectionModel().getSelectedItem() + "." +
							   year.getSelectionModel().getSelectedItem() + " " + hour.getSelectionModel().getSelectedItem() + ":" +
							   minute.getSelectionModel().getSelectedItem();
						
						model.setTitle(titleComboBox.getSelectionModel().getSelectedItem());
						model.setGenre(genre.getSelectionModel().getSelectedItem());
						model.setDistribution(distributionText.getText());
						model.setDate(date);
						model.setNrTickets(Integer.parseInt(nrTicketsText.getText()));
						
						List<ShowModel> shows = sService.retrieveAll();
						for(ShowModel s : shows) {
							if(s.getTitle().equals(model.getTitle()))
								model.setId(s.getId());
								if(model.getTitle() == null || model.getDistribution() == null || model.getNrTickets() == 0) {
									validationText.setFill(Color.CRIMSON);
									validationText.setText("Incorect data!");
								}else
									sService.update(model);
						}
				}
	}
		
	
	private void arrangeCashiers(String username) {
		List<CashierModel> cashiers = cService.retrieveAll();
		
		for(CashierModel c : cashiers) {
			if(c.getUsername().equals(username) && c.getAdmin() == 0) {
				passwordText.setText(c.getPassword());
			}
		}
	}
	
	/**
	 * CRUD operations on cashiers performed by administrators
	 * parameter 1 is a flag for confirm button
	 * 0 -> not attached to a button press
	 * 1 -> insert was pressed
	 * 2 -> update username was pressed
	 * 3 -> update password was pressed
	 * 4 -> delete was pressed
	 * @param command
	 */
	private void administratorCashierActions(String command) {
		List<CashierModel> cashiers = new ArrayList<CashierModel>();
		
		if(command.equals("insert")) {
			usernameComboBox.setEditable(true);
			passwordText.setEditable(true);
			passwordText.setText("");
			
			updateUsernameBtn.setDisable(true);
			updatePasswordBtn.setDisable(true);
			removeBtn.setDisable(true);
			cashierUpdateStatus = 1;
			
		}
		if(command.equals("update username")) {
			usernameComboBox.setEditable(true);
			passwordText.setEditable(false);
			
			insertBtn.setDisable(true);
			removeBtn.setDisable(true);
			updateUsernameBtn.setDisable(false);
			updatePasswordBtn.setDisable(true);
			cashierUpdateStatus = 2;
			
		}
		if(command.equals("update password")) {
			usernameComboBox.setEditable(false);
			passwordText.setEditable(true);
			passwordText.setText("");
			
			insertBtn.setDisable(true);
			removeBtn.setDisable(true);
			updateUsernameBtn.setDisable(true);
			updatePasswordBtn.setDisable(false);
			cashierUpdateStatus = 3;
			
		}
		if(command.equals("delete")) {
			usernameComboBox.setEditable(false);
			passwordText.setEditable(false);
			
			insertBtn.setDisable(true);
			updateUsernameBtn.setDisable(true);
			updatePasswordBtn.setDisable(true);
			
			actionMessage.setFill(Color.FIREBRICK);
			actionMessage.setText("Are you sure you want to delete the cashier?");
			cashierUpdateStatus = 4;
		}
		if(command.equals("confirm")) {
			if(cashierUpdateStatus == 1) {
				cashier = new CashierModel();
				cashier.setUsername(usernameComboBox.getSelectionModel().getSelectedItem());
				cashier.setPassword(passwordText.getText());
				cService.create(cashier);
				
				actionMessage.setFill(Color.FIREBRICK);
				actionMessage.setText("Cashier inserted");
			
			}
			if(cashierUpdateStatus == 2) {
				cashiers = cService.retrieveAll();
				cashier = new CashierModel();
				cashier.setUsername(usernameComboBox.getSelectionModel().getSelectedItem());
				cashier.setPassword(passwordText.getText());
				for(CashierModel c : cashiers) {
					//System.out.println("c: " + c.getEncriptedPassword() + " -- > cahsier: " + cashier.getPassword());
					if(c.getEncriptedPassword().equals(cashier.getPassword())) {
						System.out.println("true");
						cashier.setId(c.getId());
						cashier.setAdmin(0);
						cService.update(cashier);
					}
				}
				
				actionMessage.setFill(Color.FIREBRICK);
				actionMessage.setText("Cashier username updated");
			}
			if(cashierUpdateStatus == 3) {
				cashiers = cService.retrieveAll();
				cashier = new CashierModel();
				cashier.setUsername(usernameComboBox.getSelectionModel().getSelectedItem());
				cashier.setPassword(passwordText.getText());
				for(CashierModel c : cashiers) {
					if(c.getUsername().equals(usernameComboBox.getSelectionModel().getSelectedItem())) {
						cashier.setId(c.getId());
						cashier.setAdmin(0);
						cService.update(cashier);
					}
				}
				actionMessage.setFill(Color.FIREBRICK);
				actionMessage.setText("Cashier password updated");
			}
			if(cashierUpdateStatus == 4) {
				cashiers = cService.retrieveAll();
				cashier = new CashierModel();
				cashier.setUsername(usernameComboBox.getSelectionModel().getSelectedItem());
				cashier.setPassword(passwordText.getText());
				for(CashierModel c : cashiers) {
					if(c.getUsername().equals(usernameComboBox.getSelectionModel().getSelectedItem())) {
						cService.delete(c.getId());
					}
				}
				actionMessage.setFill(Color.FIREBRICK);
				actionMessage.setText("Cashier deleted");
			}
			usernameComboBox.setEditable(false);
			passwordText.setEditable(false);
			insertBtn.setDisable(false);
			removeBtn.setDisable(false);
			updateUsernameBtn.setDisable(false);
			updatePasswordBtn.setDisable(false);
			
			cashierUpdateStatus = 0;
			
			//update combo boxes 
			cashiers = cService.retrieveAll();
			usernameComboBox.getItems().clear();
			for(CashierModel c : cashiers) {
				if(c.getAdmin() == 0)
					usernameComboBox.getItems().add(c.getUsername());
			}
			usernameComboBox.getSelectionModel().selectFirst();
			arrangeCashiers(usernameComboBox.getSelectionModel().getSelectedItem());
		}
		if(command.equals("cancel")) {
			cashierUpdateStatus = 0;
			usernameComboBox.setEditable(false);
			passwordText.setEditable(false);
			insertBtn.setDisable(false);
			removeBtn.setDisable(false);
			updateUsernameBtn.setDisable(false);
			updatePasswordBtn.setDisable(false);
			
			usernameComboBox.getSelectionModel().selectFirst();
		}
	}
	
	
	private void exportFileAction() {
		String date = day.getSelectionModel().getSelectedItem() + "." + month.getSelectionModel().getSelectedItem() + "." +
				   year.getSelectionModel().getSelectedItem() + " " + hour.getSelectionModel().getSelectedItem() + ":" +
				   minute.getSelectionModel().getSelectedItem();
		
		ShowModel show = new ShowModel();
		show.setTitle(titleComboBox.getSelectionModel().getSelectedItem());
		show.setDate(date);
		XMLDocument doc = XMLDocument.createDocument(show);
		
		fileMessage.setFill(Color.CRIMSON);
		fileMessage.setText("File exported");
	}
	
}


	
