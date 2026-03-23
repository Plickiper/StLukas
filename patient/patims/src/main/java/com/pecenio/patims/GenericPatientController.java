package com.pecenio.patims;
import com.pecenio.patims.model.Patient;
import com.pecenio.patims.model.User;
import com.pecenio.patims.service.UserService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.Window;
import lombok.Setter;
import javafx.util.StringConverter;
import java.net.URL;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Locale;

public class GenericPatientController implements Initializable{
	@Setter
	CreatePatientController createPatientController;

	@Setter
	DeletePatientController deletePatientController ;

	@Setter
	EditPatientController editPatientController;

	@Setter
	ManagePatientController managePatientController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<Patient> lvPatients;

	@Setter
	public static Patient selectedItem;
	public TextField txtId;
	public ComboBox<User> cmbUser;
	public TextField txtUsername;
	public TextField txtPassword;
	public TextField txtRole;
	public TextField txtFirstName;
	public TextField txtLastName;
	public TextField txtPhoneNumber;
	public TextField txtUserName;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		User[] users =  (User[]) UserService.getService().getAll();
		cmbUser.getItems().addAll(users);
		StringConverter<User> userConverter = new StringConverter<User>() {
			@Override
			public String toString(User user) {
			if(user==null)
				return "";
			else
				return user.toString();
			}
			@Override
			public User fromString(String s) {
				if(!s.isEmpty()){
					for (User user : users) {
						if (s.equals(user.getName())){
							return user;
						}
					}
				}
				return null;
			}
		};
		cmbUser.setConverter(userConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected Patient toObject(boolean isEdit){
		Patient patient= new Patient();
		try {
			if(isEdit) {
				patient.setId(Integer.parseInt(txtId.getText()));
			}
			User user = cmbUser.getSelectionModel().getSelectedItem();
			patient.setUserId(Long.valueOf(user.getId()));
			patient.setUsername(txtUsername.getText());
			patient.setPassword(txtPassword.getText());
			patient.setRole(txtRole.getText());
			patient.setPhoneNumber(txtPhoneNumber.getText());
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return patient;
	}
	protected void setFields(String action){
		String formattedDate;
		Patient patient = GenericPatientController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(patient.getId()));
		User user = UserService.getService().get(patient.getUserId().intValue());
		cmbUser.getSelectionModel().select(user);
		if(action.equals("Create") || action.equals("Edit")){
			cmbUser.setVisible(true);
			txtUserName.setVisible(false);
			cmbUser.getSelectionModel().select(user);
		}
		else{
			cmbUser.setVisible(false);
			txtUserName.setVisible(true);
			txtUserName.setText(user.getName());
		}
		txtUsername.setText(patient.getUsername());
		txtPassword.setText(patient.getPassword());
		txtRole.setText(patient.getRole());
		txtFirstName.setText(patient.getFirstName());
		txtLastName.setText(patient.getLastName());
		txtPhoneNumber.setText(patient.getPhoneNumber());
	}

	protected void clearFields(String action){
		txtId.setText("");
		cmbUser.getSelectionModel().clearSelection();
		txtUserName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbUser.setVisible(true);
			txtUserName.setVisible(false);
		}
		else{
			cmbUser.setVisible(false);
			txtUserName.setVisible(true);
		}
		//txtUsername.setText("");
		//txtPassword.setText("");
		//txtRole.setText("");
		//txtFirstName.setText("");
		//txtLastName.setText("");
		//txtPhoneNumber.setText("");
	}

	protected void enableFields(boolean enable){
		cmbUser.editableProperty().set(enable);
		txtUserName.editableProperty().set(enable);
		txtUsername.editableProperty().set(enable);
		txtPassword.editableProperty().set(enable);
		txtRole.editableProperty().set(enable);
		txtFirstName.editableProperty().set(enable);
		txtLastName.editableProperty().set(enable);
		txtPhoneNumber.editableProperty().set(enable);
	}

	public int getId(){
		return Integer.parseInt(txtId.getText());
	}

	protected void showErrorDialog(String message, String expandedMessage){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(expandedMessage)));
		alert.showAndWait();
	}
	public void onBack(ActionEvent actionEvent) {
		Node node = ((Node) (actionEvent.getSource()));
		Window window = node.getScene().getWindow();
		window.hide();
		stage.setScene(manageScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and loose changes? " , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			Platform.exit();
		}
	}
	LocalDate toLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = instant.atZone( z );
		return zdt.toLocalDate();
	}
	protected Date toDate(LocalDate ld){
		ZoneId z = ZoneId.of("Singapore");
		ZonedDateTime zdt = ld.atStartOfDay(z);
		Instant instant  = zdt.toInstant();
		return Date.from(instant);
	}
}

