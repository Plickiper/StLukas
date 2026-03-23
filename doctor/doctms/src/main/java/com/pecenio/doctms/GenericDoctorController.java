package com.pecenio.doctms;
import com.pecenio.doctms.model.Doctor;
import com.pecenio.doctms.model.User;
import com.pecenio.doctms.service.UserService;
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

public class GenericDoctorController implements Initializable{
	@Setter
	CreateDoctorController createDoctorController;

	@Setter
	DeleteDoctorController deleteDoctorController ;

	@Setter
	EditDoctorController editDoctorController;

	@Setter
	ManageDoctorController manageDoctorController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<Doctor> lvDoctors;

	@Setter
	public static Doctor selectedItem;
	public TextField txtId;
	public ComboBox<User> cmbUser;
	public TextField txtUsername;
	public TextField txtPassword;
	public TextField txtRole;
	public TextField txtFirstName;
	public TextField txtLastName;
	public TextField txtSpecialization;
	public TextField txtAvailabilityStatus;
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
	protected Doctor toObject(boolean isEdit){
		Doctor doctor= new Doctor();
		try {
			if(isEdit) {
				doctor.setId(Integer.parseInt(txtId.getText()));
			}
			User user = cmbUser.getSelectionModel().getSelectedItem();
			doctor.setUserId(Long.valueOf(user.getId()));
			doctor.setUsername(txtUsername.getText());
			doctor.setPassword(txtPassword.getText());
			doctor.setRole(txtRole.getText());
			doctor.setSpecialization(txtSpecialization.getText());
			doctor.setAvailabilityStatus(txtAvailabilityStatus.getText());
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return doctor;
	}
	protected void setFields(String action){
		String formattedDate;
		Doctor doctor = GenericDoctorController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(doctor.getId()));
		User user = UserService.getService().get(doctor.getUserId().intValue());
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
		txtUsername.setText(doctor.getUsername());
		txtPassword.setText(doctor.getPassword());
		txtRole.setText(doctor.getRole());
		txtFirstName.setText(doctor.getFirstName());
		txtLastName.setText(doctor.getLastName());
		txtSpecialization.setText(doctor.getSpecialization());
		txtAvailabilityStatus.setText(doctor.getAvailabilityStatus());
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
		//txtSpecialization.setText("");
		//txtAvailabilityStatus.setText("");
	}

	protected void enableFields(boolean enable){
		cmbUser.editableProperty().set(enable);
		txtUserName.editableProperty().set(enable);
		txtUsername.editableProperty().set(enable);
		txtPassword.editableProperty().set(enable);
		txtRole.editableProperty().set(enable);
		txtFirstName.editableProperty().set(enable);
		txtLastName.editableProperty().set(enable);
		txtSpecialization.editableProperty().set(enable);
		txtAvailabilityStatus.editableProperty().set(enable);
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

