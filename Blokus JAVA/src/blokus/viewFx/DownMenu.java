/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Model;
import blokus.util.Observer;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class DownMenu extends HBox {

    private PlayersRooste playerRoos;

    public DownMenu(Model g, PlayersRooste playerRooste, Button b) {
        super();

        this.playerRoos = playerRooste;
        this.getChildren().addAll(b, button2(g), button3(g), button4(g));
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setPadding(new Insets(2));
        this.setSpacing(20);
    }

    /**
     * Create the pass button
     *
     * @param g
     * @return a button of the down menu
     */
    private Button button2(Model g) {
        Button button2 = new Button("Je passe");
        button2.setOnAction((ActionEvent event) -> {
            g.incrementPass();
            g.setHistorique(" Joueur "+ g.getCurrentPlayer().getIdPlayer() +" Passe son tour");
            g.nextPLayer();
            

        });
        return button2;
    }

    /**
     * create the turn button.
     *
     * @param g the current game
     * @return a button of the down menu
     */
    private Button button3(Model g) {
        Button button3 = new Button("Tourner");
        button3.setOnAction((ActionEvent event) -> {
            g.turnPreviousPiece();
        });
        return button3;
    }

    /**
     * create the return button.
     *
     * @param g the current game
     * @return a button of the down menu
     */
    private Button button4(Model g) {
        Button button4 = new Button("Retourner");
        button4.setOnAction((ActionEvent event) -> {
            g.turnPreviousPieceMirror();

        });
        return button4;
    }

}
