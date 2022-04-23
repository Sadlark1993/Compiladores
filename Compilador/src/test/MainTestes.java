
package test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainTestes {
    public static void main(String[] args){
        File codFonte = new File("codigo.txt");
        String linha = "";
    
        try{
           Scanner leitor = new Scanner(codFonte);
           while(leitor.hasNext()){
               linha += leitor.nextLine();
           }
           leitor.close();
        }catch(FileNotFoundException e){
            
            System.out.println("deu errado");
        
        }
        
        System.out.println(linha.charAt(3));
        if(linha.charAt(4)=='\t'){
            System.out.println("eh igual");// <--
        }else{
            System.out.println("nao eh igual");
        }
        
        System.out.println("coisa: \s coisa");
        
        if(Character.isLetter(linha.charAt(6))){
            System.out.println("It's letter.");
        }else if(Character.isDigit(linha.charAt(6))){
            System.out.println("It's digit.");
        }else{
            System.out.println("banana");
        }

             
        //testanto string
        /*
        String a = "cachorro";
        switch (a.charAt(1)){
            case 'a':
                System.out.println("a letra a"); //imprimiu isso
                break;
            case 'b':
                System.out.println("a letra b");
                break;
            default:
                System.out.println("deu errado");
        }
        */

        /*
        String coisa = "coisa";
        coisa += 's';
        System.out.println(coisa);
        */
    }
}
