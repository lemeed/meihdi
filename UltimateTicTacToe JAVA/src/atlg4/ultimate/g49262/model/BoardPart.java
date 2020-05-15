/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

/**
 * The interface for the minitictactoe and square.
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public interface BoardPart {
    
    /**
     * Allows to get the symbol.
     * @return the symbol of my boardPart.
     */
    public Symbol getSymbol();
    
    /**
     * Allows to put a symbol in a square or a tictactoe.
     * @param sym 
     */
    public void setSymbol(Symbol sym);
    
    /**
     * Allows to know if the boardPart is empty.
     * @return true if it's empty.
     */
    public boolean isEmpty();
}
