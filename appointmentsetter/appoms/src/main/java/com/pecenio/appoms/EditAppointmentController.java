package com.pecenio.appoms;
import com.pecenio.appoms.model.Appointment;
import com.pecenio.appoms.service.AppointmentService;
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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import java.net.URL;
import java.util.ResourceBundle;
import lombok.Setter;

public class EditAppointmentController extends GenericAppointmentController {
	public ImageView imgAppointment;
	@Override
	public void init() {
		setFields("Edit");
		enableFields(true);
	}
	public void onSubmit(ActionEvent actionEvent) {
		try {
			Appointment appointment = toObject(true);
			Appointment newAppointment = AppointmentService.getService().update(appointment);
			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();
			stage.setTitle("Manage Appointment");
			stage.setScene(manageScene);
			stage.show();
		}
		catch (Exception e){
			showErrorDialog("Error encountered creating appointment", e.getMessage());
		}
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
