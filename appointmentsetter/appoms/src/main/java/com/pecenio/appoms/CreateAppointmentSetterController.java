package com.pecenio.appoms;
import com.pecenio.appoms.model.AppointmentSetter;
import com.pecenio.appoms.service.AppointmentSetterService;
import com.pecenio.appoms.model.Patient;
import com.pecenio.appoms.service.PatientService;
import com.pecenio.appoms.model.Doctor;
import com.pecenio.appoms.service.DoctorService;
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

public class CreateAppointmentSetterController extends GenericAppointmentSetterController {
	public ImageView imgAppointmentSetter;
	@Override
	public void init() {
		clearFields("Edit");
		enableFields(true);
	}
	public void onSubmit(ActionEvent actionEvent) {
		try {
			AppointmentSetter appointmentSetter = toObject(false);
			AppointmentSetter newAppointmentSetter = AppointmentSetterService.getService().create(appointmentSetter);
			manageAppointmentSetterController.refresh();
			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();
			stage.setTitle("Manage AppointmentSetter");
			stage.setScene(manageScene);
			stage.show();
		}
		catch (Exception e){
			showErrorDialog("Error encountered creating appointmentSetter", e.getMessage());
		}
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
