/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.view;

import atlg4.ultimate.g49262.model.Game;
import atlg4.ultimate.g49262.model.GameModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author g49262
 */
public class Welcome extends GridPane {

    private final Label joueur1 = new Label("joueur1");
    private final Label joueur2 = new Label("joueur2");

    private final TextField j1 = new TextField();
    private final TextField j2 = new TextField();

    private final Button start = new Button("Start the game! ");

    public Welcome(GameModel game, Stage primaryStage, Button btn) {
        super.setAlignment(Pos.CENTER);
        super.setPadding(new Insets(20));
        super.setHgap(10);
        super.setVgap(15);
        VBox vbox = new VBox();

        initPlayer();

        Scene scene = new Scene(vbox, 1000, 750);
        this.add(start, 0, 10);
        
        start.setOnAction((event) -> {
            Game g = new Game();
            UTTController utt = new UTTController(g);
            
            primaryStage.setScene(scene);
        });
    }

    private void initPlayer() {
        add(joueur1, 1, 1);
        add(joueur2, 1, 2);

        add(j1, 2, 1);
        add(j2, 2, 2);
    }

}
