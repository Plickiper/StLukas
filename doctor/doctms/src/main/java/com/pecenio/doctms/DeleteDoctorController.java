package com.pecenio.doctms;
import com.pecenio.doctms.model.Doctor;
import com.pecenio.doctms.service.DoctorService;
import com.pecenio.doctms.model.User;
import com.pecenio.doctms.service.UserService;
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

public class DeleteDoctorController extends GenericDoctorController {
	public ImageView imgDoctor;
	@Override
	public void init() {
		setFields("Delete");
		enableFields(false);
	}
	public void onSubmit(ActionEvent actionEvent) {
		try {
			Doctor doctor = toObject(true);
			DoctorService.getService().delete(doctor.getId());
			Node node = ((Node) (actionEvent.getSource()));
			Window window = node.getScene().getWindow();
			window.hide();
			stage.setTitle("Manage Doctor");
			stage.setScene(manageScene);
			stage.show();
		}
		catch (Exception e){
			showErrorDialog("Error encountered creating doctor", e.getMessage());
		}
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
