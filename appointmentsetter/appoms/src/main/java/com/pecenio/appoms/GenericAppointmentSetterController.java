package com.pecenio.appoms;
import com.pecenio.appoms.model.AppointmentSetter;
import com.pecenio.appoms.model.Patient;
import com.pecenio.appoms.service.PatientService;
import com.pecenio.appoms.model.Doctor;
import com.pecenio.appoms.service.DoctorService;
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

public class GenericAppointmentSetterController implements Initializable{
	@Setter
	CreateAppointmentSetterController createAppointmentSetterController;

	@Setter
	DeleteAppointmentSetterController deleteAppointmentSetterController ;

	@Setter
	EditAppointmentSetterController editAppointmentSetterController;

	@Setter
	ManageAppointmentSetterController manageAppointmentSetterController;

	@Setter
	Stage stage;

	@Setter
	Scene splashScene;

	@Setter
	Scene manageScene;

	@Setter
	public ListView<AppointmentSetter> lvAppointmentSetters;

	@Setter
	public static AppointmentSetter selectedItem;
	public TextField txtId;
	public ComboBox<Patient> cmbPatient;
	public TextField txtPatientFirstName;
	public TextField txtPatientLastName;
	public ComboBox<Doctor> cmbDoctor;
	public TextField txtDoctorFirstName;
	public TextField txtDoctorLastName;
	public TextField txtSpecialization;
	public TextField txtPreferredDate;
	public TextField txtPreferredTimeRange;
	public TextField txtScheduledDate;
	public TextField txtScheduledTime;
	public TextField txtReason;
	public TextField txtStatus;
	public ComboBox<ParentAppointment> cmbParentAppointment;
	public TextField txtPatientName;
	public TextField txtDoctorName;
	public TextField txtParentAppointmentName;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
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
	protected AppointmentSetter toObject(boolean isEdit){
		AppointmentSetter appointmentSetter= new AppointmentSetter();
		try {
			if(isEdit) {
				appointmentSetter.setId(Integer.parseInt(txtId.getText()));
			}
			Patient patient = cmbPatient.getSelectionModel().getSelectedItem();
			appointmentSetter.setPatientId(Long.valueOf(patient.getId()));
			// appointmentSetter.setPatientName(patient.getName());
			Doctor doctor = cmbDoctor.getSelectionModel().getSelectedItem();
			appointmentSetter.setDoctorId(Long.valueOf(doctor.getId()));
			// appointmentSetter.setDoctorName(doctor.getName());
			appointmentSetter.setSpecialization(txtSpecialization.getText());
			appointmentSetter.setPreferredDate(LocalDate.parse(txtPreferredDate.getText()));
			appointmentSetter.setPreferredTimeRange(txtPreferredTimeRange.getText());
			appointmentSetter.setScheduledDate(LocalDate.parse(txtScheduledDate.getText()));
			appointmentSetter.setScheduledTime(LocalTime.parse(txtScheduledTime.getText()));
			appointmentSetter.setReason(txtReason.getText());
			appointmentSetter.setStatus(txtStatus.getText());
			ParentAppointment parentAppointment = cmbParentAppointment.getSelectionModel().getSelectedItem();
			appointmentSetter.setParentAppointmentId(Long.valueOf(parentAppointment.getId()));
			// appointmentSetter.setParentAppointmentName(parentAppointment.getName());
		}catch (Exception e){
			showErrorDialog("Error" ,e.getMessage());
		}
		return appointmentSetter;
	}
	protected void setFields(String action){
		String formattedDate;
		AppointmentSetter appointmentSetter = GenericAppointmentSetterController.selectedItem;
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
		txtId.setText(Integer.toString(appointmentSetter.getId()));
		Patient patient = PatientService.getService().get(appointmentSetter.getPatientId().intValue());
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
		txtPatientFirstName.setText(appointmentSetter.getPatientFirstName());
		txtPatientLastName.setText(appointmentSetter.getPatientLastName());
		Doctor doctor = DoctorService.getService().get(appointmentSetter.getDoctorId().intValue());
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
		txtDoctorFirstName.setText(appointmentSetter.getDoctorFirstName());
		txtDoctorLastName.setText(appointmentSetter.getDoctorLastName());
		txtSpecialization.setText(appointmentSetter.getSpecialization());
		txtPreferredDate.setText(appointmentSetter.getPreferredDate() != null ? appointmentSetter.getPreferredDate().toString() : "");
		txtPreferredTimeRange.setText(appointmentSetter.getPreferredTimeRange());
		txtScheduledDate.setText(appointmentSetter.getScheduledDate() != null ? appointmentSetter.getScheduledDate().toString() : "");
		txtScheduledTime.setText(appointmentSetter.getScheduledTime() != null ? appointmentSetter.getScheduledTime().toString() : "");
		txtReason.setText(appointmentSetter.getReason());
		txtStatus.setText(appointmentSetter.getStatus());
		ParentAppointment parentAppointment = ParentAppointmentService.getService().get(appointmentSetter.getParentAppointmentId().intValue());
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
		cmbPatient.editableProperty().set(enable);
		txtPatientName.editableProperty().set(enable);
		txtPatientFirstName.editableProperty().set(enable);
		txtPatientLastName.editableProperty().set(enable);
		cmbDoctor.editableProperty().set(enable);
		txtDoctorName.editableProperty().set(enable);
		txtDoctorFirstName.editableProperty().set(enable);
		txtDoctorLastName.editableProperty().set(enable);
		txtSpecialization.editableProperty().set(enable);
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

