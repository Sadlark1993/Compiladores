//um compliador


package main;
import java.util.Stack;
import javax.swing.JOptionPane;

public class MainClass {

    public static void main(String[] args) throws LexicaException, SintaticaException, Exception {
        String arquivo = JOptionPane.showInputDialog(null, "Insira o nome do arquivo com a exensao.");

        //coletando opcao
        EnumMenu opcao = EnumMenu.apenas_o_log;
        opcao = (EnumMenu) JOptionPane.showInputDialog(null, "Escolha uma opção:", "Menu", JOptionPane.QUESTION_MESSAGE, null, EnumMenu.values(), EnumMenu.values()[0]);
        //if(opcao == null) throw new Exception("Nenhuma opcao escolhida.");

        String tabelaLexica = AnalisadorLexico.analisar(arquivo);
        if(opcao == EnumMenu.todas_as_listagens){ 
            System.out.println("********** TABELA LEXICA **********");
            System.out.println(tabelaLexica);
            System.out.println("************************************\n");
        }

        String[] linTabLex = tabelaLexica.split("\n");
        //System.out.println(linTabLex[1]);
        String aux;
        String[] linha = new String[4];
        Stack<String> tokensStack = new Stack<String>();

        tokensStack.push("$");
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

        if(opcao == EnumMenu.todas_as_listagens || opcao == EnumMenu.a_lista_de_tokens){
            Stack copyTokensStack = (Stack) tokensStack.clone();
            System.out.println("********** Lista de Tokens **********");
            while(copyTokensStack.peek()!="$"){
                System.out.println(copyTokensStack.pop());
            }
            System.out.println("*********************************\n");
        }

        int result = AnalisadorSintatico.analisar(tokensStack);
        if(result == -1) System.out.println("$");
        else {
            //System.out.println(result); //debug: numeracao do token na pilha

            String[] linha2 = linTabLex[result].split(";");
            System.out.println("linha "+(Integer.parseInt(linha2[linha2.length-1].trim())+1)+", coluna "+(Integer.parseInt(linha2[linha2.length-2].trim())+1)+".");
        }



        /*debug
        //isso esvazia a pilha kkkkk APAGA!!
        while(!tokensStack.empty()){
            System.out.println(tokensStack.pop());
        }
        */
    }

}
