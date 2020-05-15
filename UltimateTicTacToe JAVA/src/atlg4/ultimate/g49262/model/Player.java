/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

/**
 * This class represent a player in the game.
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Player  {
       private Symbol symbol;
       private String name;
       private Boolean joker;

    /**
     *The constructor of the class.
     * @param symbol of player.
     */
    public Player(String aName,Symbol symbol) {
        this.name = aName;
        this.symbol = symbol;
        this.joker = true;
    }

    public Boolean getJoker() {
        return joker;
    }

    public void setJoker(Boolean joker) {
        this.joker = joker;
    }

    
  

    /**
     *Allows to get the symbol.
     * @return the symbol of player.
     */
    public Symbol getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    
    
    
    
    

}
