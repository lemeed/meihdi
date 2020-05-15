/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Model;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class EnteredAction implements EventHandler<MouseEvent> {

    private Model g;
    private SquareGrid sg;

    /**
     * Constructor of the entered action for the mouse
     *
     * @param g
     * @param sg
     */
    public EnteredAction(Model g, SquareGrid sg) {
        this.g = g;
        this.sg = sg;

    }

    /**
     * Overrided handle of a mouse event
     *
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        if (!g.getPlayersInGame().isEmpty()) {
            g.setCurrentPos(sg.getXforSquare(), sg.getYforSquare());

        }
        event.consume();
    }

}
