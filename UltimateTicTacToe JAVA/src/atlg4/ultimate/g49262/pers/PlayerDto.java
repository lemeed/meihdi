/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.pers;

import atlg4.ultimate.g49262.exception.DTOException;

/**
 *
 * @author Meihdi El Amouri
 */
public class PlayerDto extends EntityDto<Integer> {

    private String pseudo;
    private int nbWin;
    private int nbExAequo;
    private int nbDefeat;

    public PlayerDto( String pseudo, int nbWin, int nbExAequo, int nbDefeat) throws DTOException {
        

        
        this.pseudo = pseudo;
        this.nbWin = nbWin;
        this.nbExAequo = nbExAequo;
        this.nbDefeat = nbDefeat;
    }

    public PlayerDto(String pseudo) throws DTOException {
        System.out.println(pseudo);
        if (pseudo == null) {
            throw new DTOException("the name is required!");

        }
        this.pseudo = pseudo;
        
        this.nbWin = 0;
        
        this.nbExAequo = 0;
        
        this.nbDefeat = 0;
        
    }

    /**
     * Allows to get pseudo of player.
     * @return the pseudo of player.
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Allows to get the number of win.
     * @return win of player.
     */
    public int getNbWin() {
        return nbWin;
    }

    /**
     * Allows to get the nb of draw of player.
     * @return the nb of draw.
     */
    public int getNbExAequo() {
        return nbExAequo;
    }

    /**
     * Allows to get the number of defeat.
     * @return 
     */
    public int getNbDefeat() {
        return nbDefeat;
    }

    /**
     * Allows to set pseudo to player.
     * @param pseudo the pseudo.
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Allows to set the number of win.
     * @param nbWin the number of win.
     */
    public void setNbWin(int nbWin) {
        this.nbWin = nbWin;
    }

    /**
     * Allows to set the number of draw.
     * @param nbExAequo the number of draw.
     */
    public void setNbExAequo(int nbExAequo) {
        this.nbExAequo = nbExAequo;
    }

    /**
     * Allows to set the number of defeat. 
     * @param nbDefeat the number of defeat.
     */
    public void setNbDefeat(int nbDefeat) {
        this.nbDefeat = nbDefeat;
    }
    
    

}
