/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

/**
 *This class represent the score of player.
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Score {
    private int numberOfWins; 
    private int numberOfDefeats;
    private int numberOfEqualGames;

    /**
     * This is the constructor of the class.
     * @param numberOfWins 
     * @param numberOfDefeats
     * @param numberOfEqualGames 
     */
    public Score(int numberOfWins, int numberOfDefeats, int numberOfEqualGames) {
        this.numberOfWins = numberOfWins;
        this.numberOfDefeats = numberOfDefeats;
        this.numberOfEqualGames = numberOfEqualGames;
    }
    
    /**
     * allows to get the number of wins.
     * @return the number of wins.
     */
    public int getNumberOfWins(){
        return numberOfWins;
    }
    
    /**
     * Allows to get the number of defeats.
     * @return the number of defeats.
     */
    public int getNumberOfDefeats() {
        return numberOfDefeats;
    }

    /**
     * Allows to get the number of raw.
     * @return the number of raw.
     */
    public int getNumberOfEqualGames() {
        return numberOfEqualGames;
    }
    
    /**
     * Allows to add a win.
     */
    public void addWin(){
        this.numberOfWins++;
    }
    
    /**
     * Allows to add defeat.
     */
    public void addDefeat(){
        this.numberOfDefeats++;
    }
    
    /**
     * Allows to add raw.
     */
    public void addEqualGame(){
        this.numberOfEqualGames++;
    }
    
}
