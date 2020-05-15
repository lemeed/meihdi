/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class User {
    private String pseudonyme;

    private String passwordHash;
    
    Score score; 

    public User(String pseudo, String pswd) {
        this.pseudonyme = pseudo;
        this.passwordHash = pswd;
        this.score= new Score(0, 0, 0);
    }

    /**
     * 
     * @return 
     */
    public String getPseudonyme() {
        return pseudonyme;
    }

    /**
     * 
     * @return 
     */
    public String getPasswordHash() {
        return passwordHash;
    }
}
