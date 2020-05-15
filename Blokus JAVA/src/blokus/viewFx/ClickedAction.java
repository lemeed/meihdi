/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.BlokusException;
import blokus.model.Model;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class ClickedAction implements EventHandler<MouseEvent> {

    private Model g;
    private SquareGrid sg;

    /**
     * Constructor of a clicked action for the game
     *
     * @param g
     * @param sg
     */
    public ClickedAction(Model g, SquareGrid sg) {
        this.g = g;
        this.sg = sg;

    }

    /**
     * Overridded handle of a mouse event
     *
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {

            try {
                g.selectPosition(sg.getXforSquare(), sg.getYforSquare());
            } catch (BlokusException ex) {
                Logger.getLogger(ClickedAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.isSecondaryButtonDown() && g.getPreviousPiece() != null) {

            g.turnPreviousPiece();
        }

        event.consume();
    }

}
