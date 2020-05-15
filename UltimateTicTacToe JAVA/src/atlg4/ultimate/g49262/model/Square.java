/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

import java.util.Objects;

/**
 * This class represent a square in the game.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Square implements BoardPart {

    Symbol symbol;

    /**
     * it's the constructor of the square.
     */
    public Square() {
        this.symbol = null;
    }

    @Override
    public Symbol getSymbol() {
        return this.symbol;

    }

    @Override
    public void setSymbol(Symbol sym) {
        System.out.println(sym.getValue());
        if (sym.equals(null)) {
            throw new NullPointerException("There's no symbol to put");
        }
        this.symbol = sym;
    }

    @Override
    public boolean isEmpty() {
        return this.symbol == null;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.symbol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Square other = (Square) obj;
        if (this.symbol != other.symbol) {
            return false;
        }
        return true;
    }

}
