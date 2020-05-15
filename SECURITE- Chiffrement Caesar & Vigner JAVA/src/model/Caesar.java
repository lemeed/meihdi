package model;

import java.text.Normalizer;

/**
 * Chiffrement de caesar
 * @author g49262
 */
public class Caesar {

    public static int LOWER_BOUND = 'A';
    public static int HUPPER_BOUND = 'Z';
    private final String text;
    private String encrypted;
    private final int key;

    public Caesar(String text, char c) {

        this.text = Normalizer.normalize(text, Normalizer.Form.NFD).toUpperCase();
        this.key = Math.abs(LOWER_BOUND - c);
        calculEncrypted();
    }

        public Caesar(String test , char c, Boolean ok){
        this.text = Normalizer.normalize(test, Normalizer.Form.NFD).toUpperCase();
        this.key = Math.abs(LOWER_BOUND + c);
        System.out.println(this.key);
        calculEncrypted();
    }
    private void calculEncrypted() {
        int c;
        char a;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            a = text.charAt(i);
            if (isOk(a)) {
                c = a - LOWER_BOUND ;
                c = LOWER_BOUND + ((c + key) % (HUPPER_BOUND - LOWER_BOUND + 1));
                res.append((char) c);
            }

        }
        encrypted =  res.toString();
    }



    public String getEncrypted() {
        return encrypted;
    }
    
    

    private boolean isOk(char c) {
        return LOWER_BOUND <= c && c <= HUPPER_BOUND;
    }

    @Override
    public String toString() {
        return encrypted;
    }
    
    

}
