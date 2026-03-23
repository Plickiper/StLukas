package com.pecenio.appoms;
import com.pecenio.appoms.model.Appointment;
import com.pecenio.appoms.model.User;
import com.pecenio.appoms.service.UserService;
import com.pecenio.appoms.model.Patient;
import com.pecenio.appoms.service.PatientService;
import com.pecenio.appoms.model.Doctor;
import com.pecenio.appoms.service.DoctorService;
import com.pecenio.appoms.model.Appointment;
import com.pecenio.appoms.service.AppointmentService;
import com.pecenio.appoms.model.ParentAppointment;
import com.pecenio.appoms.service.ParentAppointmentService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
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

public class GenericAppointmentController implements Initializable{
	@Setter
	CreateAppointmentController createAppointmentController;

	@Setter
	DeleteAppointmentController deleteAppointmentController ;

	@Setter
	EditAppointmentController editAppointmentController;

	@Setter
	ManageAppointmentController manageAppointmentController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<Appointment> lvAppointments;

	@Setter
	public static Appointment selectedItem;
	public TextField txtId;
	public ComboBox<User> cmbUser;
	public TextField txtUsername;
	public TextField txtPassword;
	public TextField txtRole;
	public ComboBox<Patient> cmbPatient;
	public TextField txtPatientFirstName;
	public TextField txtPatientLastName;
	public TextField txtPatientPhoneNumber;
	public ComboBox<Doctor> cmbDoctor;
	public TextField txtDoctorFirstName;
	public TextField txtDoctorLastName;
	public TextField txtSpecialization;
	public TextField txtAvailabilityStatus;
	public ComboBox<Appointment> cmbAppointment;
	public TextField txtPreferredDate;
	public TextField txtPreferredTimeRange;
	public TextField txtScheduledDate;
	public TextField txtScheduledTime;
	public TextField txtReason;
	public TextField txtStatus;
	public ComboBox<ParentAppointment> cmbParentAppointment;
	public TextField txtUserName;
	public TextField txtPatientName;
	public TextField txtDoctorName;
	public TextField txtAppointmentName;
	public TextField txtParentAppointmentName;

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
						if (s.equals(user.toString())){
							return user;
						}
					}
				}
				return null;
			}
		};
		cmbUser.setConverter(userConverter);
		Patient[] patients =  (Patient[]) PatientService.getService().getAll();
		cmbPatient.getItems().addAll(patients);
		StringConverter<Patient> patientConverter = new StringConverter<Patient>() {
			@Override
			public String toString(Patient patient) {
			if(patient==null)
				return "";
			else
				return patient.toString();
			}
			@Override
			public Patient fromString(String s) {
				if(!s.isEmpty()){
					for (Patient patient : patients) {
						if (s.equals(patient.toString())){
							return patient;
						}
					}
				}
				return null;
			}
		};
		cmbPatient.setConverter(patientConverter);
		Doctor[] doctors =  (Doctor[]) DoctorService.getService().getAll();
		cmbDoctor.getItems().addAll(doctors);
		StringConverter<Doctor> doctorConverter = new StringConverter<Doctor>() {
			@Override
			public String toString(Doctor doctor) {
			if(doctor==null)
				return "";
			else
				return doctor.toString();
			}
			@Override
			public Doctor fromString(String s) {
				if(!s.isEmpty()){
					for (Doctor doctor : doctors) {
						if (s.equals(doctor.toString())){
							return doctor;
						}
					}
				}
				return null;
			}
		};
		cmbDoctor.setConverter(doctorConverter);
		Appointment[] appointments =  (Appointment[]) AppointmentService.getService().getAll();
		cmbAppointment.getItems().addAll(appointments);
		StringConverter<Appointment> appointmentConverter = new StringConverter<Appointment>() {
			@Override
			public String toString(Appointment appointment) {
			if(appointment==null)
				return "";
			else
				return appointment.toString();
			}
			@Override
			public Appointment fromString(String s) {
				if(!s.isEmpty()){
					for (Appointment appointment : appointments) {
						if (s.equals(appointment.toString())){
							return appointment;
						}
					}
				}
				return null;
			}
		};
		cmbAppointment.setConverter(appointmentConverter);
		ParentAppointment[] parentAppointments =  (ParentAppointment[]) ParentAppointmentService.getService().getAll();
		cmbParentAppointment.getItems().addAll(parentAppointments);
		StringConverter<ParentAppointment> parentAppointmentConverter = new StringConverter<ParentAppointment>() {
			@Override
			public String toString(ParentAppointment parentAppointment) {
			if(parentAppointment==null)
				return "";
			else
				return parentAppointment.toString();
			}
			@Override
			public ParentAppointment fromString(String s) {
				if(!s.isEmpty()){
					for (ParentAppointment parentAppointment : parentAppointments) {
						if (s.equals(parentAppointment.toString())){
							return parentAppointment;
						}
					}
				}
				return null;
			}
		};
		cmbParentAppointment.setConverter(parentAppointmentConverter);
		init();
	}
	protected void init(){
		System.out.println("Invoked from Generic Controller");
	}
	protected Appointment toObject(boolean isEdit){
		Appointment appointment= new Appointment();
		try {
			if(isEdit) {
				appointment.setId(Integer.parseInt(txtId.getText()));
			}
			User user = cmbUser.getSelectionModel().getSelectedItem();
			appointment.setUserId(Long.valueOf(user.getId()));
			// appointment.setUserName(user.getName());
			appointment.setUsername(txtUsername.getText());
			appointment.setPassword(txtPassword.getText());
			appointment.setRole(txtRole.getText());
			Patient patient = cmbPatient.getSelectionModel().getSelectedItem();
			appointment.setPatientId(Long.valueOf(patient.getId()));
			// appointment.setPatientName(patient.getName());
			appointment.setPatientPhoneNumber(txtPatientPhoneNumber.getText());
			Doctor doctor = cmbDoctor.getSelectionModel().getSelectedItem();
			appointment.setDoctorId(Long.valueOf(doctor.getId()));
			// appointment.setDoctorName(doctor.getName());
			appointment.setSpecialization(txtSpecialization.getText());
			appointment.setAvailabilityStatus(txtAvailabilityStatus.getText());
			Appointment innerAppointment = cmbAppointment.getSelectionModel().getSelectedItem();
			appointment.setAppointmentId(Long.valueOf(innerAppointment.getId()));
			// appointment.setAppointmentName(innerAppointment.toString());
			appointment.setPreferredDate(LocalDate.parse(txtPreferredDate.getText()));
			appointment.setPreferredTimeRange(txtPreferredTimeRange.getText());
			appointment.setScheduledDate(LocalDate.parse(txtScheduledDate.getText()));
			appointment.setScheduledTime(LocalTime.parse(txtScheduledTime.getText()));
			appointment.setReason(txtReason.getText());
			appointment.setStatus(txtStatus.getText());
			ParentAppointment parentAppointment = cmbParentAppointment.getSelectionModel().getSelectedItem();
			appointment.setParentAppointmentId(Long.valueOf(parentAppointment.getId()));
			// appointment.setParentAppointmentName(parentAppointment.getName());
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return appointment;
	}
	protected void setFields(String action){
		String formattedDate;
		Appointment appointment = GenericAppointmentController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(appointment.getId()));
		User user = UserService.getService().get(appointment.getUserId().intValue());
		cmbUser.getSelectionModel().select(user);
		if(action.equals("Create") || action.equals("Edit")){
			cmbUser.setVisible(true);
			txtUserName.setVisible(false);
			cmbUser.getSelectionModel().select(user);
		}
		else{
			cmbUser.setVisible(false);
			txtUserName.setVisible(true);
			txtUserName.setText(user.toString());
		}
		txtUsername.setText(appointment.getUsername());
		txtPassword.setText(appointment.getPassword());
		txtRole.setText(appointment.getRole());
		Patient patient = PatientService.getService().get(appointment.getPatientId().intValue());
		cmbPatient.getSelectionModel().select(patient);
		if(action.equals("Create") || action.equals("Edit")){
			cmbPatient.setVisible(true);
			txtPatientName.setVisible(false);
			cmbPatient.getSelectionModel().select(patient);
		}
		else{
			cmbPatient.setVisible(false);
			txtPatientName.setVisible(true);
			txtPatientName.setText(patient.toString());
		}
		txtPatientFirstName.setText(appointment.getPatientFirstName());
		txtPatientLastName.setText(appointment.getPatientLastName());
		txtPatientPhoneNumber.setText(appointment.getPatientPhoneNumber());
		Doctor doctor = DoctorService.getService().get(appointment.getDoctorId().intValue());
		cmbDoctor.getSelectionModel().select(doctor);
		if(action.equals("Create") || action.equals("Edit")){
			cmbDoctor.setVisible(true);
			txtDoctorName.setVisible(false);
			cmbDoctor.getSelectionModel().select(doctor);
		}
		else{
			cmbDoctor.setVisible(false);
			txtDoctorName.setVisible(true);
			txtDoctorName.setText(doctor.toString());
		}
		txtDoctorFirstName.setText(appointment.getDoctorFirstName());
		txtDoctorLastName.setText(appointment.getDoctorLastName());
		txtSpecialization.setText(appointment.getSpecialization());
		txtAvailabilityStatus.setText(appointment.getAvailabilityStatus());
		Appointment innerAppointment = AppointmentService.getService().get(appointment.getAppointmentId().intValue());
		cmbAppointment.getSelectionModel().select(innerAppointment);
		if(action.equals("Create") || action.equals("Edit")){
			cmbAppointment.setVisible(true);
			txtAppointmentName.setVisible(false);
			cmbAppointment.getSelectionModel().select(innerAppointment);
		}
		else{
			cmbAppointment.setVisible(false);
			txtAppointmentName.setVisible(true);
			txtAppointmentName.setText(innerAppointment.toString());
		}
		txtPreferredDate.setText(appointment.getPreferredDate() != null ? appointment.getPreferredDate().toString() : "");
		txtPreferredTimeRange.setText(appointment.getPreferredTimeRange());
		txtScheduledDate.setText(appointment.getScheduledDate() != null ? appointment.getScheduledDate().toString() : "");
		txtScheduledTime.setText(appointment.getScheduledTime() != null ? appointment.getScheduledTime().toString() : "");
		txtReason.setText(appointment.getReason());
		txtStatus.setText(appointment.getStatus());
		ParentAppointment parentAppointment = ParentAppointmentService.getService().get(appointment.getParentAppointmentId().intValue());
		cmbParentAppointment.getSelectionModel().select(parentAppointment);
		if(action.equals("Create") || action.equals("Edit")){
			cmbParentAppointment.setVisible(true);
			txtParentAppointmentName.setVisible(false);
			cmbParentAppointment.getSelectionModel().select(parentAppointment);
		}
		else{
			cmbParentAppointment.setVisible(false);
			txtParentAppointmentName.setVisible(true);
			txtParentAppointmentName.setText(parentAppointment.toString());
		}
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
		cmbPatient.getSelectionModel().clearSelection();
		txtPatientName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbPatient.setVisible(true);
			txtPatientName.setVisible(false);
		}
		else{
			cmbPatient.setVisible(false);
			txtPatientName.setVisible(true);
		}
		//txtPatientFirstName.setText("");
		//txtPatientLastName.setText("");
		//txtPatientPhoneNumber.setText("");
		cmbDoctor.getSelectionModel().clearSelection();
		txtDoctorName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbDoctor.setVisible(true);
			txtDoctorName.setVisible(false);
		}
		else{
			cmbDoctor.setVisible(false);
			txtDoctorName.setVisible(true);
		}
		//txtDoctorFirstName.setText("");
		//txtDoctorLastName.setText("");
		//txtSpecialization.setText("");
		//txtAvailabilityStatus.setText("");
		cmbAppointment.getSelectionModel().clearSelection();
		txtAppointmentName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbAppointment.setVisible(true);
			txtAppointmentName.setVisible(false);
		}
		else{
			cmbAppointment.setVisible(false);
			txtAppointmentName.setVisible(true);
		}
		//txtPreferredDate.setText("");
		//txtPreferredTimeRange.setText("");
		//txtScheduledDate.setText("");
		//txtScheduledTime.setText("");
		//txtReason.setText("");
		//txtStatus.setText("");
		cmbParentAppointment.getSelectionModel().clearSelection();
		txtParentAppointmentName.setText("");
		if(action.equals("Create") || action.equals("Edit")){
			cmbParentAppointment.setVisible(true);
			txtParentAppointmentName.setVisible(false);
		}
		else{
			cmbParentAppointment.setVisible(false);
			txtParentAppointmentName.setVisible(true);
		}
	}

	protected void enableFields(boolean enable){
		cmbUser.editableProperty().set(enable);
		txtUserName.editableProperty().set(enable);
		txtUsername.editableProperty().set(enable);
		txtPassword.editableProperty().set(enable);
		txtRole.editableProperty().set(enable);
		cmbPatient.editableProperty().set(enable);
		txtPatientName.editableProperty().set(enable);
		txtPatientFirstName.editableProperty().set(enable);
		txtPatientLastName.editableProperty().set(enable);
		txtPatientPhoneNumber.editableProperty().set(enable);
		cmbDoctor.editableProperty().set(enable);
		txtDoctorName.editableProperty().set(enable);
		txtDoctorFirstName.editableProperty().set(enable);
		txtDoctorLastName.editableProperty().set(enable);
		txtSpecialization.editableProperty().set(enable);
		txtAvailabilityStatus.editableProperty().set(enable);
		cmbAppointment.editableProperty().set(enable);
		txtAppointmentName.editableProperty().set(enable);
		txtPreferredDate.editableProperty().set(enable);
		txtPreferredTimeRange.editableProperty().set(enable);
		txtScheduledDate.editableProperty().set(enable);
		txtScheduledTime.editableProperty().set(enable);
		txtReason.editableProperty().set(enable);
		txtStatus.editableProperty().set(enable);
		cmbParentAppointment.editableProperty().set(enable);
		txtParentAppointmentName.editableProperty().set(enable);
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

