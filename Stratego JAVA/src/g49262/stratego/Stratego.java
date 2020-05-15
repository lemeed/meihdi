package g49262.stratego;

import g49262.stratego.controller.Controller;
import g49262.stratego.model.Game;
import g49262.stratego.model.Model;
import g49262.stratego.view.View;

/**
 *
 * @author Meihdi El Amouri
 */
public class Stratego {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Model game = new Game();
        View view = new View();
        Controller controll = new Controller(game, view);

        controll.startGame();
    }

}
