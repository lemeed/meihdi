/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Game;
import blokus.model.Model;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class BlokusMain extends Application {

    Stage windows;
    Scene scene1, scene2;

    Scene first;
    VBox layoutt = new VBox(20);
    Stage primaryStage;

    public void startGame(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 1000, 750);

        Button buttonnn = new Button("commencer Partie");

        buttonnn.setOnAction(e -> windows.setScene(scene));
        VBox vboxFirst = new VBox();
        primaryStage.setTitle("Blokus");
        primaryStage.setResizable(true);

        Model g = new Game();
        Menubar menu = new Menubar(g);
        vboxFirst.getChildren().add(menu);
        Scene first = new Scene(vboxFirst, 900, 600);
        Button newGame = new Button("Nouvelle Partie");
        newGame.setOnAction(e -> {
            try {
                startGame(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(BlokusMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        firstScene firstscene = new firstScene(g, primaryStage, newGame);
        vboxFirst.getChildren().addAll(firstscene);
        primaryStage.setScene(first);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            startGame(primaryStage);
        } catch (Exception ex) {
            Logger.getLogger(BlokusMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
