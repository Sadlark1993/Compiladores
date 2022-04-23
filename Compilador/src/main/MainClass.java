//um compliador


package main;
import java.util.Stack;

public class MainClass {

    public static void main(String[] args) throws LexicaException {
        String tabelaLexica = AnalisadorLexico.analisar("codigo.txt");
        //System.out.println(tabelaLexica);

        String[] linTabLex = tabelaLexica.split("\n");
        //System.out.println(linTabLex[1]);
        String aux;
        String[] linha = new String[4];
        Stack<String> tokensStack = new Stack<String>();

        //the list of tokens must be stacked in the reverse order. So the first token will be the first to be analized.
        for(int i = linTabLex.length - 1; i >=1;i--){
            aux = linTabLex[i];
            //System.out.println(aux);
            //System.out.println("-------------------------------**");
            if(aux.charAt(0)==';'){
                tokensStack.push("final");
            }else{
                linha = aux.split(";");
                tokensStack.push(linha[1].trim());
            }
            //System.out.println(tokensStack.peek());

        }

        //isso esvazia a pilha kkkkk APAGA!!
        while(!tokensStack.empty()){
            System.out.println(tokensStack.pop());
        }
    }

}
