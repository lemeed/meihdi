package model;

import java.util.*;


/**
 * Dechiffrement
 * @author g49262
 */
public class CipherVigner {

    private String key = "";
    private final String text;
    private final Map<String, List<Integer>> map;
    private final Map<Integer, Integer> mapPgcd;
    private List<String> keyFound;
    private List<ResultVigenere> resultVigeneres;
    private StringBuilder resultFinalEncode;

    public CipherVigner(String text) {
        // this.text = text;
        this.text = text.replaceAll("[^a-zA-Z0-9]", "");
        map = new TreeMap<>(Collections.reverseOrder());
        mapPgcd = new TreeMap<>();
        keyFound = new ArrayList<>();
        resultVigeneres = new ArrayList<>();
        // key= null;
        init();
    }

    public CipherVigner(String aText, String aKey,int decode) {
        this(aText);

        this.key = aKey;
        
        this.resultFinalEncode = encode();
        
        
        //init();
    }

    
    public List<ResultVigenere> getResultVigeneres() {
       
         decrypt();
         
        return resultVigeneres;
        
    }


    private void init(){
        
        calculPosition();
        eliminerLesUniques();
        calculPgcd();
    }

    public Collection<Integer> getKey(){
        return mapPgcd.keySet();
    }

    public StringBuilder getEncodeVigenere(){
        return resultFinalEncode;
    }
    
    private StringBuilder encode(){
     
        StringBuilder resultFinal =new StringBuilder();
        int i = 0;
        while( i<text.length()){
            
            for(int j = 0 ; j<key.length(); j++){               
                String s = Character.toString(key.charAt(j));
                Caesar c = new Caesar(s , text.charAt(i));              
                resultFinal.append(c.getEncrypted());
                i++;
                  if (i== text.length() ){
                    break;
                  }
            
            }
        }
        return resultFinal;
    }
    
    
    private List<StringBuilder> decrypt() {
        List<StringBuilder> tokenDestruct = null;
        List<StringBuilder> resultFinal = new ArrayList<>();
        //Destruction
        int taillePremierSb = 0;
        for (Integer e : mapPgcd.keySet()) {
            
            tokenDestruct = new ArrayList<>();
            for (int j = 0; j < e; j++) {//0 à 3 pour clé de taille 4 4 StringBuilder
                tokenDestruct.add(new StringBuilder());
                for (int i = j; i < text.length(); i = i + e) { // 1,4,7
                    char c = text.charAt(i);
                    
                    tokenDestruct.get(j).append(c);
                }
            }
            taillePremierSb = tokenDestruct.get(0).length();
            //Caesar decode
            List<StringBuilder> caesarCodes = new ArrayList<>();
            StringBuilder keyFound = new StringBuilder();
            CipherCaesar cc = null;
            for (StringBuilder sb : tokenDestruct) {
                cc = new CipherCaesar(sb.toString());
                caesarCodes.add( new StringBuilder(  cc.toString() ) );
                keyFound.append(cc.getStepCharacter());
            }

            //Reconstruction
            StringBuilder res = new StringBuilder();
            List<StringBuilder> tokenDestructWithCaesar = caesarCodes;

            for (int i = 0; i < taillePremierSb; i++) {

                for (StringBuilder sb : tokenDestructWithCaesar) {
                    try {
                        res.append(sb.toString().charAt(i));
                    } catch (StringIndexOutOfBoundsException ex) {}
                }
            }
            if(key==""){
                resultFinal.add(res);
            resultVigeneres.add(new ResultVigenere(res.toString(), keyFound.toString()));
            this.keyFound.add(keyFound.toString());
            }else if(e == key.length()){
                resultFinal.add(res);
                resultVigeneres.add(new ResultVigenere(res.toString(), key));
                this.keyFound.add(key);
                break;
            }
        

        }
        resultVigeneres.sort(Comparator.comparingInt(ResultVigenere::getOccurences)
                .reversed());
        return resultFinal;
    }


    

    private void calculPosition() {
        String buf;
        for (int v = 0; v < text.length() - 2; v++) {
            buf = text.substring(v, v + 3);//On a bien le premier trigramme
            if (map.containsKey(buf)) {
                map.get(buf).add(v);//on ajoute la première position
            } else {
                map.put(buf, new ArrayList<>());
                map.get(buf).add(v); // on ajoute la première position
            }
        }
    }

    //Calcule le pgcd
    private void calculPgcd() {
        map.forEach((t, u) -> {
            int tmp;
            for (int i = 0; i < u.size() - 1; i++) {
                tmp = pgcd(u.get(i), u.get(i + 1));

                    if (mapPgcd.containsKey(tmp)) {
                        mapPgcd.replace(tmp, mapPgcd.get(tmp) + 1);
                    } else {
                        mapPgcd.put(tmp, 0);
                    }
            }
        });
        Map<Integer, Integer> tmp = new TreeMap<>(mapPgcd);
        tmp.forEach((t, u) -> {
            if (u == 0) {
                mapPgcd.remove(t);
            }
        });

    }

    private void eliminerLesUniques() {
        Map<String, List<Integer>> tmp = new TreeMap<>(map);
        tmp.forEach((k, v) -> {
            if (v.size() == 1) {
                map.remove(k);
            }
        });
    }

    private int pgcd(int a, int b) {
        int t;
        int r;
        if (b == 0) {
            return a;
        }
        if (a == 0) {
            return b;
        }
        if (b > a) {
            t = a;
            a = b;
            b = t;
        }
        do {
            r = a % b;
            a = b;
            b = r;
        } while (r != 0);
        return a;

    }

}
