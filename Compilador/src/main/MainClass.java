//um compliador


package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        HashMap<String, String> variaveis = new HashMap<>();
        List<String> tokensList = new ArrayList<>();
        List<String> lexemasList = new ArrayList<>();

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

        for(int i = 1; i<linTabLex.length ;i++){
            aux = linTabLex[i];
            if(aux.charAt(0)==';'){
                tokensList.add("final");
                lexemasList.add(";");
            }else{
                linha = aux.split(";");
                tokensList.add(linha[1].trim());
                lexemasList.add(linha[0].trim());
            }
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
            throw new SintaticaException("linha "+(Integer.parseInt(linha2[linha2.length-1].trim())+1)+", coluna "+(Integer.parseInt(linha2[linha2.length-2].trim())+1)+".");
        }

        result = AnalisadorSemantico.analisar(lexemasList, tokensList, variaveis);
        if(result == -1) System.out.println("Nenhum erro semantico encontrado.");
        else{
            result++;
            String[] linha2 = linTabLex[result].split(";");
            throw new Exception("linha "+(Integer.parseInt(linha2[linha2.length-1].trim())+1)+", coluna "+(Integer.parseInt(linha2[linha2.length-2].trim())+1)+".");
        }

        //popula a lista de variaveis
        List<String> var = new ArrayList<>();
        for(String key : variaveis.keySet() ){
            var.add(key);
        }

        //aqui ficarao armazenados a lista de lexemas e a lista de tokens.
        List<String> lexemasInt = new ArrayList<>();
        List<String> tokensInt = new ArrayList<>();

        GeradorIntermediario.gerar(lexemasList, tokensList, lexemasInt, tokensInt, var, arquivo);

        //System.out.println("\n\n*****Lista de Tokens do Codigo Intermediario*****");
        for(String token : tokensInt) System.out.println(token); //debug: lista de tokens do codigo intermediario

        GeradorFinal.gerar(lexemasInt, tokensInt, var, arquivo);

    }//fim do metodo main

}
