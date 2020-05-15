package controller;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.*;

/**
 * @author Meihdi El Amouri
 */
public class Controller{

     
     Scanner sc;
     String text;

    public Controller(StringBuilder text){
        this.sc = new Scanner(System.in);
        this.text= text.toString();
    }

    public void init(){

        System.out.println("Enter an order please");
        String command = sc.nextLine();
        
        String[] commandTable = command.split(" ");
   

        while(commandTable[0] != "exit"){
            try{

            
            switch(commandTable[0].toLowerCase()){

                case "quit":
    
                System.out.println("Thanks to use this program. Goodbye! ");
                System.exit(0);

                case "code":
                    code(commandTable);
                    break;
                case "decode":
                    decode(commandTable); 
                    break;
                default :
                    System.out.println("Cette commande n'existe pas");
                    break;
            }} catch (NumberFormatException ex){
                System.out.println("Veuillez rentrer un nombre entre 1 et 26");
            } catch( ArrayIndexOutOfBoundsException ex){
                System.out.println("Veuillez notifier ce que vous voulez faire ! Voir la doc.");
            } catch( IllegalArgumentException ex){
                System.out.println(ex.getMessage());
            }
            System.out.println("Entrer une commande");
            command = sc.nextLine();
            commandTable = command.split(" ");
        }
        System.exit(0);
        
    }

    private void code(String[] commandTable){
        switch(commandTable[1].toLowerCase()){
            case "vigenere":
            if(!Pattern.matches("^[a-zA-Z]*$", commandTable[2])){
                throw new IllegalArgumentException("votre clé contient un caractère ou un chiffre");
            }
            CipherVigner cv = new CipherVigner(text, commandTable[2],0);
            System.out.println(cv.getEncodeVigenere());
            break;
            case "caesar":
            
            if( Integer.parseInt(commandTable[2]) >26 || Integer.parseInt(commandTable[2])< 1){
                throw new NumberFormatException("");
            }
            CaesarEncrypt ce = new CaesarEncrypt(text, Integer.parseInt(commandTable[2])); 
            System.out.println(ce);
        
        }
    }

    private void decode(String[] commandTable){
        switch(commandTable[1].toLowerCase()){
            case "vigenere":
            CipherVigner cv = new CipherVigner(text);
            decodeVigenere(cv);
            break;
            case "caesar":
            CipherCaesar cs = new CipherCaesar(text);
            System.out.println(cs);
            case "caesarkey":
            if( Integer.parseInt(commandTable[2]) >26 || Integer.parseInt(commandTable[2])< 1){
                throw new NumberFormatException("");
            }
            CaesarEncrypt ce = new CaesarEncrypt(text, -Integer.parseInt(commandTable[2])); 
            System.out.println(ce);
            break;
            
        }
    }

    private void decodeVigenere(CipherVigner aCv){
        sc = new Scanner(System.in);
        CipherVigner cv = aCv;
        int cas = 0;
        String s;
        ResultVigenere rv = null;
        List<ResultVigenere> resultVigenereList = cv.getResultVigeneres();
        do {
            try {

                rv = resultVigenereList.get(cas);
                System.out.println("KEY: " + rv.getKey());
                System.out.println("TOKEN: " + rv.getToken());
                cas++;
                System.out.print("Cette solution est-elle correcte ? (O/N): ");
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Plus d'autres propositions");
                System.exit(1);
            }
            s = sc.next();
        }while (!s.equalsIgnoreCase("O"));
    }
}