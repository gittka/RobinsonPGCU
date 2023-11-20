package com.robinson.logiqueformelle;

import com.robinson.logiqueformelle.application.LexicalAnalyzer;
import com.robinson.logiqueformelle.application.Symbole;
import com.robinson.logiqueformelle.application.SymbolesPair;
import com.robinson.logiqueformelle.application.Unificator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ApplicationController {
    @FXML
    public AnchorPane Appli;
    @FXML
    public ImageView accueil_icon;
    @FXML
    private TextArea result;

    @FXML
    private TextField term1;

    @FXML
    private TextField term2;
    private  Stage stage;

    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 892, 700);
        stage = new Stage();
        Appli.getScene().getWindow().hide();
        stage.setTitle("Algorithme de Robinson");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void unify(ActionEvent event) {
        String ter1 = term1.getText();
        String ter2 = term2.getText();
        if(ter1.isEmpty() || ter2.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("Veuillez remplir les deux champs");
            alert.showAndWait();
        }else{
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
            Symbole symbole1 = lexicalAnalyzer.analyzeLexem(ter1);
            Symbole symbole2 = lexicalAnalyzer.analyzeLexem(ter2);

            if (symbole1 != null && symbole2 != null) {

                SymbolesPair uP = new SymbolesPair(symbole1, symbole2);
                result.setText("Unification de "+ter1+" et "+ter2+" : \n");
                result.appendText(uP.toString());
                result.appendText("\n");

                Unificator s = new Unificator();
                List<SymbolesPair> uPR = s.unify(uP);

                // check if unification can be processed
                if (uPR == null) {
                    result.setText("Les symboles ne peuvent être unifiés");
                } else {
                    for (SymbolesPair u : uPR) {
                        result.appendText(u.toString());
                        result.appendText("\n");
                    }
                }
            } else {
               result.setText("Syntaxe lexeme incorrecte. Recommenccez.");
            }

        }
    }

    @FXML
    public void change(InputMethodEvent inputMethodEvent) {
       term1.textProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue.isEmpty() && term2.getText().isEmpty()) {
               result.setText("");
           }
       });
       term2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() && term1.getText().isEmpty()) {
                result.setText("");
            }
       });

    }
}
