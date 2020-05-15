package model;

import ressources.FrWord;

public class ResultVigenere {

    private String token;
    private String key;
    private int occurences;

    public ResultVigenere(String token, String key) {
        this.token = token;
        this.key = key;
        this.occurences = FrWord.getOccurence(new StringBuilder(token));
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOccurences() {
        return occurences;
    }

    public void setOccurences(int occurences) {
        this.occurences = occurences;
    }
}
