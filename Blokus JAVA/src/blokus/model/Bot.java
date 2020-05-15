/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.model;

import javafx.scene.paint.Color;

/**
 * We can create a bot player.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Bot extends Player {

    private boolean bot;

    public Bot(String aPseudonym, Color color, int idPlayer, Point aStart, boolean bot) {
        super(aPseudonym, color, idPlayer, aStart);
        this.bot = bot;
    }

    @Override
    public boolean isBot() {
        return bot;
    }

}
