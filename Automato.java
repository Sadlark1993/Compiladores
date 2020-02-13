/*Automato Finito*/
package mainPackage;

import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Automato {
    public static void main(String args[])throws IOException {
        String[] instruction = {"1", "2", "1", "2"};
        boolean verbose = true;  //when true, some data will be printed for debuging process
        String fileName = "D:\\documentos\\Engenharia_da_computacao\\Compiladores\\NetBeansProjects\\AutomatoFinito\\src\\main\\java\\mainPackage\\automato.txt";
        File arquivo = new File(fileName);
        if(!arquivo.exists()){
            arquivo.createNewFile();
        }
        FileReader fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);
        
        Vector<String> texto = new Vector<String>(); //resizable list, to add values at will ('-')
        
        do{
            String line = br.readLine();
            texto.addElement(line);
        }while(br.ready());
        
        if(verbose){    //for debug
            for(int i = 0; i<texto.size(); i++){
                System.out.println(texto.get(i));
            }
        }
        
        /*Converting a String in a vector with strings that represent the
        process list*/
        String aux = texto.get(0);
        aux = aux.replaceAll(" ", "");
        String[] processos = aux.split(";");
        
        if(verbose){
            System.out.println("");
            for(int i = 0; i<processos.length; i++){
                System.out.println(processos[i]);
            }
        }
        
        aux = texto.get(1);
        aux = aux.replaceAll(" ","");
        String[] states = aux.split(";");
        
        if(verbose){
            System.out.println("");
            for(int i = 0; i<states.length; i++){
                System.out.println(states[i]);
            }
        }
        
        aux = texto.get(2);
        String initialValue = aux.trim();
        
        if(verbose){
            System.out.println("\n"+ initialValue);
        }
        
        aux = texto.get(3);
        String finalValue = aux.trim();
        
        if(verbose){
            System.out.println("\n"+ finalValue);
            System.out.println("\nTransitionfunction:");
        }
        
        //Here, the transition instruction is built:
        String[] aux2;
        
        Map<String,String> transitionFunction = new HashMap<>();
        for(int i=4; i<texto.size(); i++){
            String[] aux3 = new String[2];
            aux = texto.get(i);
            aux = aux.replace(" ", "");
            aux2 = aux.split(";");
            aux3[0] = aux2[0];
            aux3[1] = aux2[1];
            transitionFunction.put(aux3[0]+aux3[1], aux2[2]);
            if(verbose){
                System.out.println(aux3[0]+ " "+ aux3[1]+ " "+ transitionFunction.get(aux3[0]+aux3[1]));
                //System.out.println(transitionFunction.get(aux3));
            }
        }
        String[] aux4 = new String[2];
        if(verbose){
            aux4[0] = "B";
            aux4[1] = "1";
            System.out.println("teste: "+aux4[0]+ " "+ aux4[1]+ " "+ transitionFunction.get(aux4[0]+aux4[1]));
            }
        String currentState = initialValue, process, nextState;
        System.out.println("\n\n***Iniciando execução***");
        for(int i = 0; i<instruction.length;i++){
            aux4[0] = currentState;
            aux4[1] = instruction[i];
            nextState = transitionFunction.get(aux4[0]+aux4[1]);
            System.out.println(aux4[0]+", "+aux4[1]+"-->"+nextState);
            currentState = nextState;
            if(currentState.equals(finalValue)){
                System.out.println("Success!! FinalValue achived!");
                break;
            }
        }
        System.out.println("end of program (:");  
    }
}
