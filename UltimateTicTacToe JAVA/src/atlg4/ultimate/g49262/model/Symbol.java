/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

/**
 * This class represent the symbol of player.
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public enum Symbol {
    X("X"),
    O("O");

    public String value;

    /**
     * Allows to transform the enum in string.
     * @param value 
     */
    private Symbol(String value) {
        this.value = value;
    }

    /**
     * Allows to get the value of symbol.
     * @return the value.
     */
    public String getValue() {
        return value;
    }
}
