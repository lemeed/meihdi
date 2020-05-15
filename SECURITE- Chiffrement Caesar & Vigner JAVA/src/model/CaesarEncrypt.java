package model;

import java.text.Normalizer;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author g49262
 */
public class CaesarEncrypt {

    static final int LOWER_BOUNDS = 65;
    static final int HUPPER_BOUNDS = 90;
    static final int GAP = HUPPER_BOUNDS - LOWER_BOUNDS + 1;
    public String text;
    public int key;
    public String textConvert;
    public char charConvert;
    
    public CaesarEncrypt(String text , int aKey){
        this.text = text;
        this.key = aKey;
        this.textConvert = convertString(text, key);
    }


    
    public static char convert(char c, int step) {

        if (!isInBounds(c)) {
            throw new IllegalArgumentException("pas convertible");
        }
        //step %= GAP;
        int res = c + step;
        if (res > HUPPER_BOUNDS) {
            res = LOWER_BOUNDS + (res - HUPPER_BOUNDS - 1);
        }
        if (res < LOWER_BOUNDS) {
            res = HUPPER_BOUNDS -  (LOWER_BOUNDS - res)+1 ;
        }

        return (char) res;
    }

    public char getCharConver(){
        return this.charConvert;
    }
    public static String convertString(String txt, int step) {

        StringBuilder sb = new StringBuilder();
        txt = Normalizer.normalize(txt, Normalizer.Form.NFD);
        for (char c : txt.toUpperCase().toCharArray()) {
            if (isInBounds(c)) {
                sb.append(convert(c, step));
            }
        }
        return sb.toString();
    }

    public static Map<Character, Integer> counting(String txt) {

        Map<Character, Integer> mp = new TreeMap<>();
        for (char c : txt.toCharArray()) {
            if (isInBounds(c)) {
                mp.putIfAbsent(c, 0);
                if (mp.containsKey(c)) {
                    mp.replace(c, mp.get(c) + 1);
                }
            }

        }

        return mp;
    }

    public static int getStep(Map<Character, Integer> mp) {

        mp.values().stream().sorted(Collections.reverseOrder());
        mp.forEach((t, u) -> {
        });
        return 0;

    }

    public static boolean isInBounds(char c) {
        return c >= LOWER_BOUNDS && c <= HUPPER_BOUNDS;
    }

    @Override
    public String toString(){
        return this.textConvert;
    }
}
