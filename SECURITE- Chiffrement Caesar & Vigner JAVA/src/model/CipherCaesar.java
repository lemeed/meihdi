package model;

import ressources.LetterFrequency;
import java.text.Normalizer;
import java.util.*;

public class CipherCaesar {

    
    private String token;
    private int tries;
    private int stepDecrypt;
    private char stepCharacter;

    /**
     *
     * @param token
     */
    public CipherCaesar(String token) {

        this.token = convertToNoAccent(token);
        this.token = this.token.toUpperCase();
        tries = 0;
    }

    public char getStepCharacter() {
        return stepCharacter;
    }

    public void setStepCharacter(char stepCharacter) {
        this.stepCharacter = stepCharacter;
    }

    public void incrementeTries() {
        
        tries++;
    }

    /**
     *
     * @return
     */
    private String decode() {

        Map<Integer, LetterFrequency> tmp = tabCharacter();
        Map<Double, Character> valuesEnum = new TreeMap<>(Collections.reverseOrder());
        int step = 0;
        StringBuilder newToken = new StringBuilder();
        for (LetterFrequency c : LetterFrequency.values()) {
            valuesEnum.put(c.getFrequency(), c.getCharacter());
        }
        step = getStep(tmp, valuesEnum);
        char[] chars = token.toCharArray();
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            if (valuesEnum.containsValue(chars[i])) {
                ints[i] = (int) chars[i] - step;
                if (ints[i] < Caesar.LOWER_BOUND-1) {
                    ints[i] = ints[i] + 26;
                }
                if (ints[i] > Caesar.HUPPER_BOUND) {
                    ints[i] = ints[i] - 26;
                }
                if (ints[i] == 55) {
                    ints[i] = 27;
                }
            } else {
                ints[i] = (int) chars[i];
            }
        }
        for (int i = 0; i < ints.length; i++) {
            chars[i] = (char) ints[i];
            newToken.append(chars[i]);
        }
        stepCharacter = (char) ((stepDecrypt%26) + Caesar.LOWER_BOUND);
        return newToken.toString();
    }

    /**
     *
     * @return
     */
    private int getStep(Map<Integer, LetterFrequency> tmp, Map<Double, Character> valuesEnum) {

        LetterFrequency cToken = (LetterFrequency) tmp.values().toArray()[0];
        int asciiToken = (int) cToken.getCharacter();
        Character cEnum = (Character) valuesEnum.values().toArray()[tries];
        int asciiEnum = (int) cEnum;
        
        return stepDecrypt = asciiToken-asciiEnum;
    }

    public int getStepDecrypt() {
//        return stepDecrypt;
        return stepDecrypt > 0 ? Caesar.LOWER_BOUND - stepDecrypt : stepDecrypt +Caesar.HUPPER_BOUND;
    }

    /**
     *
     * @return
     */
    private Map<Integer, LetterFrequency> tabCharacter() {

        Map<Integer, LetterFrequency> tmp = new TreeMap<>(Collections.reverseOrder());
        LetterFrequency tab[] = LetterFrequency.values();
        for (int i = 0; i < tab.length; i++) {
            LetterFrequency c = tab[i];
            int count = countIteration(c.getCharacter());
            if (count != 0) {
                tmp.put(count, c);
            }
        }
        return tmp;
    }

    /**
     *
     * @param c
     * @return
     */
    private int countIteration(char c) {

        return (int) token.chars().filter((e) -> e == c).count();
    }

    private static String convertToNoAccent(String string) {

        StringBuilder sb = new StringBuilder(string.length());
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        for (char c : string.toCharArray()) {
            if (c <= '\u007F') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return decode();
    }
}
