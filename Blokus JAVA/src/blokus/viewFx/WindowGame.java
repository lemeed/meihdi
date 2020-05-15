/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Game;
import blokus.model.Model;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class WindowGame extends BorderPane {

    Model game = new Game();

    public WindowGame(Model g, Button b) {
        super();

        Button btn = b;
        BoardGrid bg = new BoardGrid(g);
        PlayersRooste pr = new PlayersRooste(g);
        DownMenu dmenu = new DownMenu(g, pr, btn);
        Menubar menu = new Menubar(g);
        this.setLeft(pr);
        this.setRight(bg);
        this.setBottom(dmenu);
        this.setTop(menu);
    }

}
