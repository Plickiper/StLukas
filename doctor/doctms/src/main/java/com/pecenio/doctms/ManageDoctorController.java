package com.pecenio.doctms;
import com.pecenio.doctms.model.Doctor;
import com.pecenio.doctms.service.DoctorService;
import com.pecenio.doctms.GenericDoctorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Setter;

public class ManageDoctorController extends GenericDoctorController{
	@Setter
	Stage stage;

	@Setter
	Scene createViewScene;

	@Setter
	Scene editViewScene;

	@Setter
	Scene deleteViewScene;

	public ImageView doctorImage;
	@FXML
	public Button btnCreate;

	@FXML
	public Button btnEdit;

	@FXML
	public Button btnDelete;

	@FXML
	public Button btnClose;

	@FXML
	public Button imageButton;
	Doctor selectedItem;

	@FXML
	private ListView<Doctor> lvDoctors;

		public void refresh() {
			Doctor[] doctors = DoctorService.getService().getAll();
			lvDoctors.getItems().clear();
			lvDoctors.getItems().addAll(doctors);
			enableFields(false);
		}

	@Override
	public void init() {
		try {
			refresh();
		}
		catch (Exception e){
			showErrorDialog("Message: ", e.getMessage());
		}
	}

	public void onAction(MouseEvent mouseEvent) {
		GenericDoctorController.selectedItem = lvDoctors.getSelectionModel().getSelectedItem();
		if(GenericDoctorController.selectedItem == null) {
			return;
		}
		setFields("Manage");
	}
	public void onCreate(ActionEvent actionEvent)  throws Exception {
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(createViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageDoctorJFXApp.class.getResource("create-doct-view.fxml"));
			Parent root = fxmlLoader.load();
			CreateDoctorController controller = fxmlLoader.getController();
			controller.setStage(stage);
			createViewScene = new Scene(root, 300, 720);
			controller.setManageDoctorController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Create Doctor");
		stage.setScene(createViewScene);
		stage.show();
	}
	public void onEdit(ActionEvent actionEvent)  throws Exception {
		if(GenericDoctorController.selectedItem == null){
			showErrorDialog("Please select an doctor from the list", "Cannot edit");
		return;
		}
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(editViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageDoctorJFXApp.class.getResource("edit-doct-view.fxml"));
			Parent root = fxmlLoader.load();
			EditDoctorController controller = fxmlLoader.getController();
			controller.setStage(stage);
			editViewScene = new Scene(root, 300, 720);
			controller.setManageDoctorController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Edit Doctor");
		stage.setScene(editViewScene);
		stage.show();
	}
	public void onDelete(ActionEvent actionEvent)  throws Exception {
		if(GenericDoctorController.selectedItem == null){
			showErrorDialog("Please select an doctor from the list", "Cannot delete");
		return;
		}
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(deleteViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageDoctorJFXApp.class.getResource("delete-doct-view.fxml"));
			Parent root = fxmlLoader.load();
			DeleteDoctorController controller = fxmlLoader.getController();
			controller.setStage(stage);
			deleteViewScene = new Scene(root, 300, 720);
			controller.setManageDoctorController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Delete Doctor");
		stage.setScene(deleteViewScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
