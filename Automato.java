/*Automato Finito*/
package automato;

import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class Automato {
    public static void main(String args[])throws IOException {
        boolean verbose = true;  //Quando verdadeiro, alguns prints serao ativados para debug e teste
        String fileName = "C:\\Users\\Aluno.DESKTOP-Q9Q32RU\\Documents\\NetBeansProjects\\Automato\\src\\automato\\automato.txt";
        File arquivo = new File(fileName);
        if(!arquivo.exists()){
            arquivo.createNewFile();
        }
        FileReader fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);
        
        Vector<String> texto = new Vector<String>(); //lista encadeada, para adicionar quantos elementos quiser
        
        do{
            String line = br.readLine();
            texto.addElement(line);
        }while(br.ready());
        
        if(verbose){    //para debug
            for(int i = 0; i<texto.size(); i++){
                System.out.println(texto.get(i));
            }
        }
        
        /*Abaixo, eu precisava converter uma linha (string) em um vetor com os 
        caracteres que representam os dados (sem espaços ou vírgulas)*/
        int j=0;
        String aux = texto.get(0);
        aux = aux.replaceAll(" ","");
        String[] processos = aux.split(";");
        
        if(verbose){
            System.out.println("");
            for(int i = 0; i<processos.length; i++){
                System.out.println(processos[i]);
            }
        }
        
        aux = texto.get(1);
        
        
    }
    
}
