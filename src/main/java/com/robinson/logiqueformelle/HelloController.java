package com.robinson.logiqueformelle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    public AnchorPane Accueil;
    @FXML
    public Button click;
    @FXML
    private ImageView logo;
    private Stage stage;


    @FXML
    public void begin(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 892, 700);
        stage = new Stage();
        Accueil.getScene().getWindow().hide();
        stage.setTitle("Algorithme de Robinson");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
