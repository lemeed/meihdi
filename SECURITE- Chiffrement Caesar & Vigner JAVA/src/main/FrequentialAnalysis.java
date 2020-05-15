package main;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.Controller;
public class FrequentialAnalysis{
    
public static void main( String []args){

    System.out.println(args[0]);
    if (args.length == 0 || args.length > 1) {
        System.err.println("Please just select one text");
        System.exit(1);
    }
    StringBuilder token = new StringBuilder();
    try {
        Files.lines(Paths.get(args[0])).forEach(token::append);
        Controller c = new Controller(token);
        c.init();

    }catch(IOException e) {
        System.out.println("Problème lors de la lecture du fichier");
    }
    
   
}
}

