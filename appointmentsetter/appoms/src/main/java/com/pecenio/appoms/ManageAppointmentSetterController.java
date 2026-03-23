package com.pecenio.appoms;
import com.pecenio.appoms.model.AppointmentSetter;
import com.pecenio.appoms.service.AppointmentSetterService;
import com.pecenio.appoms.GenericAppointmentSetterController;
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

public class ManageAppointmentSetterController extends GenericAppointmentSetterController{
	@Setter
	Stage stage;

	@Setter
	Scene createViewScene;

	@Setter
	Scene editViewScene;

	@Setter
	Scene deleteViewScene;

	public ImageView appointmentSetterImage;
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
	AppointmentSetter selectedItem;

	@FXML
	private ListView<AppointmentSetter> lvAppointmentSetters;

		public void refresh() {
			AppointmentSetter[] appointmentSetters = AppointmentSetterService.getService().getAll();
			lvAppointmentSetters.getItems().clear();
			lvAppointmentSetters.getItems().addAll(appointmentSetters);
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
		GenericAppointmentSetterController.selectedItem = lvAppointmentSetters.getSelectionModel().getSelectedItem();
		if(GenericAppointmentSetterController.selectedItem == null) {
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
			FXMLLoader fxmlLoader = new FXMLLoader(ManageAppointmentSetterJFXApp.class.getResource("create-appo-view.fxml"));
			Parent root = fxmlLoader.load();
			CreateAppointmentSetterController controller = fxmlLoader.getController();
			controller.setStage(stage);
			createViewScene = new Scene(root, 300, 720);
			controller.setManageAppointmentSetterController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Create AppointmentSetter");
		stage.setScene(createViewScene);
		stage.show();
	}
	public void onEdit(ActionEvent actionEvent)  throws Exception {
		if(GenericAppointmentSetterController.selectedItem == null){
			showErrorDialog("Please select an appointmentSetter from the list", "Cannot edit");
		return;
		}
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(editViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageAppointmentSetterJFXApp.class.getResource("edit-appo-view.fxml"));
			Parent root = fxmlLoader.load();
			EditAppointmentSetterController controller = fxmlLoader.getController();
			controller.setStage(stage);
			editViewScene = new Scene(root, 300, 720);
			controller.setManageAppointmentSetterController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Edit AppointmentSetter");
		stage.setScene(editViewScene);
		stage.show();
	}
	public void onDelete(ActionEvent actionEvent)  throws Exception {
		if(GenericAppointmentSetterController.selectedItem == null){
			showErrorDialog("Please select an appointmentSetter from the list", "Cannot delete");
		return;
		}
		Node node = ((Node) (actionEvent.getSource()));
		Scene currentScene = node.getScene();
		Window window = currentScene.getWindow();
		window.hide();
		if(deleteViewScene == null){
			FXMLLoader fxmlLoader = new FXMLLoader(ManageAppointmentSetterJFXApp.class.getResource("delete-appo-view.fxml"));
			Parent root = fxmlLoader.load();
			DeleteAppointmentSetterController controller = fxmlLoader.getController();
			controller.setStage(stage);
			deleteViewScene = new Scene(root, 300, 720);
			controller.setManageAppointmentSetterController(this);
			controller.setManageScene(manageScene);
			controller.setSplashScene(splashScene);
		}
		stage.setTitle("Delete AppointmentSetter");
		stage.setScene(deleteViewScene);
		stage.show();
	}
	public void onClose(ActionEvent actionEvent) {
		super.onClose(actionEvent);
	}
}
