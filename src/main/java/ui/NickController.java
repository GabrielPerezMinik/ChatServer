package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class NickController implements Initializable {

	@FXML
	private TextField TextNick;

	@FXML
	private VBox View;

	@FXML
	private Label errorLabel;

	public NickController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Nick.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	void onEnviarNick(ActionEvent event) {

	}

}
