package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Chiffrement de Vigenere
 * @author g49262
 */
public class Vigner {

    private String key;
    private String token;
    private String textEncrypted;
    private int taillePremierSb;

    public Vigner(String token, String key) {

        if (key.length() == 0) {
            throw new IllegalArgumentException("taille de la clé est nulle");
        }
        this.key = key;
        this.token = token;
        encrypt();
    }
    
   

    //Applique le dechiffrement de caesar sur les X parties du token
    private List<StringBuilder> codeCaesar() {
        
        List<StringBuilder> lsb = destructToken(); //Decoupe le texte en parties
        List<StringBuilder> caesarCodes = new ArrayList<>();
        for (int i = 0; i < lsb.size(); i++) {
            caesarCodes.add(new StringBuilder((new Caesar(lsb.get(i).toString(), key.charAt(i))).getEncrypted()));//tout les caractère liés à la chaine

        }
       
        return caesarCodes;
    }

    //Sépare le token en 3 chaines
    private List<StringBuilder> destructToken() {

        List<StringBuilder> tokenDestruct = new ArrayList<>();
        for (int j = 0; j < key.length(); j++) {//0 à 3 pour clé de taille 4 4 StringBuilder
            tokenDestruct.add(new StringBuilder());
            for (int i = j; i < token.length(); i = i + key.length()) { // 1,4,7 
                char c = token.charAt(i);
                tokenDestruct.get(j).append(c);
            }
        }
        taillePremierSb = tokenDestruct.get(0).length();
        return tokenDestruct;
    }

    //Reconstruit le token après que les 3 parties soit passées par caesar
    private void encrypt() {
        
        StringBuilder res = new StringBuilder();
        List<StringBuilder> tokenDestructWithCaesar = codeCaesar();
        for (int i = 0; i < taillePremierSb; i++) {

            for (StringBuilder sb : tokenDestructWithCaesar) {
                try {
                    res.append(sb.toString().charAt(i));
                } catch (StringIndexOutOfBoundsException e) {}
            }
        }
        textEncrypted =  res.toString();
    }

    public String getTextEncrypted() {
        return textEncrypted;
    }

}
