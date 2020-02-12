
package mainPackage;

import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Automato {
    public static void main(String args[])throws IOException {
        boolean verbose = true;
        String fileName = "C:\\Users\\Claudio\\Documents\\NetBeansProjects\\AutomatoFinito\\src\\main\\java\\mainPackage\\automato.txt";
        File arquivo = new File(fileName);
        if(!arquivo.exists()){
            arquivo.createNewFile();
        }
        FileReader fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);
        
        Vector<String> texto = new Vector<String>();
        
        do{
            String line = br.readLine();
            texto.addElement(line);
        }while(br.ready());
        
        if(verbose){
            for(int i = 0; i<texto.size(); i++){
                System.out.println(texto.get(i));
            }
        }
        
        String aux = texto.get(0);
        aux = 
    }
    
}
