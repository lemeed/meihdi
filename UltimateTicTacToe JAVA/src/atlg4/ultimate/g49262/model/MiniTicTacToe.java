/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

import atlg4.composant.g49262.view.MyTicTacToe;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent a MiniTicTacToe
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MiniTicTacToe implements BoardPart, TicTacToe {

    Symbol sym;
    List<Square> squares;
    Symbol winner;
    MyTicTacToe tt;

    public MiniTicTacToe() {
        this.sym = null;
        this.squares = new ArrayList<>(8);
        initList();

    }

    private void initList() {
        for (int i = 0; i <= 8; i++) {
            this.squares.add(new Square());
        }
    }

    @Override
    public Symbol getSymbol() {
        return this.sym;
    }

    @Override
    public void setSymbol(Symbol aSym) {
        if (sym == null) {
            throw new NullPointerException("there's no symbol to put");
        }
        this.sym = aSym;
    }

    @Override
    public boolean isEmpty() {
        return this.sym == null;
    }

    /**
     * Allows to get the squares in the mini tic tac toe.
     * @return the squares.
     */
    public List<Square> getSquares() {
        return squares;
    }

    
    @Override
    public boolean isFinished() {
        return checkDiag() || checkColumn() ||checkRow() ||isFull();
    }

    @Override
    public void playpart(int partIndex, Symbol sym) {
        if (canPlayPart(partIndex) && !isFinished()) {
            System.out.println("pas bon 2");
            squares.get(partIndex).setSymbol(sym);
        }

    }
    
    public void playPartJoker(int partIndex , Symbol sym){
        squares.get(partIndex).setSymbol(sym);
    }

    @Override
    public Symbol getWinner() {
        return sym;
    }

    @Override
    public boolean canPlayPart(int partIndex) {
        return squares.get(partIndex).isEmpty();

    }
    
    private boolean checkDiagonalesfirst(){
        for (int i = 0 ; i<= 8 ; i+=4){
            if (squares.get(i).symbol == null){
                return false;
            }
            
        }
        return true;
    }
    
    private boolean checkDiagonalesSec(){
        for (int i = 2 ; i<= 6 ; i+=2){
            if (squares.get(i).symbol == null){
                return false;
            }
            
        }
        return true;
    }

    /**
     * Allows to check if they are a winner in the diagonals.
     * @return true if there is a winner.
     */
    @Override
    public boolean checkDiag() {
        boolean valid = false;
         if ( checkDiagonalesfirst() ||checkDiagonalesSec()){
              
            if (squares.get(0).symbol == squares.get(4).symbol && squares.get(4).symbol == squares.get(8).symbol) {
                this.winner = squares.get(0).getSymbol();
                this.sym = squares.get(0).getSymbol();
                valid = true;
            } else if (squares.get(2).symbol == squares.get(4).symbol && squares.get(4).symbol == squares.get(6).symbol) {
                this.winner = squares.get(2).getSymbol();
                this.sym = squares.get(2).getSymbol();
                valid = true;
            } 
         }
           
        

        return valid;
    }
    
    
        
    /**
     * Allows to check if there is a winner in the row.
     * @return true if there is a winner.
     */

    @Override
    public boolean checkRow() {
        boolean valid = false;

        
        for (int i = 0; i <= 6; i += 3) {
            if (squares.get(i).getSymbol() != null ){
                if (squares.get(i).symbol == squares.get(i + 1).symbol && squares.get(i + 1).symbol == squares.get(i + 2).symbol) {
                this.winner = squares.get(i).getSymbol();
                this.sym = squares.get(i).getSymbol();
                valid = true;
                break;
            }
            }
            
        }
        return valid;
    }

    /**
     * Allows to know if there is a winner in the column.
     * @return true if there is a winner.
     */
    @Override
    public boolean checkColumn() {
        boolean valid = false;

        for (int i = 0; i <= 2; i++) {
            if(squares.get(i).getSymbol() != null){
                System.out.println("salut");
                if (squares.get(i).symbol == squares.get(i + 3).symbol && squares.get(i + 3).symbol == squares.get(i + 6).symbol) {
                    this.sym = squares.get(i).getSymbol();
                this.winner = squares.get(i).getSymbol();
                valid = true;
                break;
            }
            }
            
        }
        return valid;
    }

    @Override
    public boolean isFull() {
        boolean valid = true;

        for (int i = 0; i < squares.size(); i++) {
            if (this.squares.get(i).isEmpty()) {
                valid = false;
            }
        }

        return valid;
    }
}
