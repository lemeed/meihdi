/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class PlayersRooste extends VBox {

    private ListPieces list1;
    private ListPieces list2;
    private ListPieces list3;
    private ListPieces list4;

    /**
     * Constructor of PlayerRooste.
     *
     * @param g
     */
    public PlayersRooste(Model g) {
        super();
        this.list1 = new ListPieces(0, g);
        this.list2 = new ListPieces(1, g);
        this.list3 = new ListPieces(2, g);
        this.list4 = new ListPieces(3, g);
        GridPane grid = new GridPane();
        grid.add(list1, 0, 0);
        grid.add(list2, 0, 1);
        grid.add(list3, 0, 2);
        grid.add(list4, 0, 3);
        grid.setPadding(new Insets(1));
        grid.setAlignment(Pos.TOP_LEFT);
        this.getChildren().add(grid);
    }

}
