/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Model;
import blokus.model.Piece;
import blokus.model.Player;
import blokus.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * the List of piece of player and info.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class ListPieces extends VBox implements Observer {

    private Player player;
    private GridPane grid = new GridPane();
    private int playerId;

    /**
     * the contructor of ListPieces.
     *
     * @param playerId
     * @param g
     */
    public ListPieces(int playerId, Model g) {
        super();
        player = g.getPlayersInGame().get(playerId);
        this.playerId = playerId;
        Label pInfo = new Label(infoGame(g));
        this.getChildren().add(pInfo);
        g.registerObserver(this);
        fillPiece(g);
        grid.setHgap(5);
        this.getChildren().add(grid);
    }

    /**
     * allows to print the info of PLayer.
     *
     * @param g
     * @return the info of player.
     */
    private String infoGame(Model g) {

        String textPlayer = new String();
        textPlayer += player.getName();
        textPlayer += "   Score:  ";
        textPlayer += Double.toString(player.getScore());
        textPlayer += "  Pieces:  ";
        textPlayer += player.getStock().getStock().size();
        return textPlayer;
    }

    /**
     * place the piece in the grid.
     *
     * @param g
     */
    private void fillPiece(Model g) {
        for (int i = 0; i < player.getStock().getStock().size(); i++) {
            Piece piece = player.getStock().getStock().get(i);
            PieceGrid pg = new PieceGrid(piece, g, player);
            grid.add(pg, i % 8, i / 8);

        }
    }

    @Override
    public void update(Model g) {
        grid.getChildren().clear();
        int i = 0;
        for (int j = 0; j < 22; j++) {
            if (j < player.getStock().getStock().size()) {
                Piece p = player.getStock().getStock().get(i);
                PieceGrid pg = new PieceGrid(p, g, player);
                i++;
                grid.add(pg, j % 8, j / 8);
            } else {
                grid.add(new PieceGrid(), j % 8, j / 8);
            }
            
        }
        
            Label l = (Label) getChildren().get(0);
            

        
        l.setText(infoGame(g));
   
    }

}
