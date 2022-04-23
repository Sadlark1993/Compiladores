package main;

import java.util.Stack;

public class AnalisadorSintatico {
  private static Stack<String> tokensStack;
  private static Stack<String> nTermStack;

  public static int analisar(Stack<String> tokensStackExterno) throws SintaticaException{
    tokensStack = tokensStackExterno;

    nTermStack = new Stack<String>();
    nTermStack.push("$");
    nTermStack.push("<COD>");
    int aux = 0;

    while(tokensStack.peek() != "$" && nTermStack.peek() != "$"){
      if(nTermStack.peek() == "<COD>"){
        //to do
      }else if(nTermStack.peek()=="$"){
        return aux;
      }else if(tokensStack.peek() == "$"){
        throw new SintaticaException("Fim inesperado do codigo");
      }else{
        throw new SintaticaException("Não houve nenhuma mudança nas pilhas");
      }
      aux++;
    }

    
    return -1;
  }

  //<COD> ::= <LINHA> <COD>
  private static void op0(){
    nTermStack.push("<LINHA>");
  }

  /*<COD> ::= î
  private static void op1(){
    nTermStack.pop(); //we don't need it
  }
  */

  //<LINHA> ::= <ATRIB>
  private static void op2(){
    nTermStack.pop(); //pop <LINHA>
    nTermStack.push("<ATRIB>");
  }

  //<LINHA> ::= <ESCRITA>
  private static void op3(){
    nTermStack.pop(); //pop <LINHA>
    nTermStack.push("<ESCRITA>");
  }

  //<LINHA> ::= <COND>
  private static void op4(){
    nTermStack.pop(); //pop <LINHA>
    nTermStack.push("<COND>");
  }

  //<LINHA> ::= <LOOP>
  private static void op5(){
    nTermStack.pop(); //pop <LINHA>
    nTermStack.push("<LOOP>");
  }

  //<ATRIB> ::= ID ":=" <ATRIB2> FINAL
  private static void op6(){
    nTermStack.pop(); //pop <ATRIB>
    nTermStack.push("final");
    nTermStack.push("<ATRIB2>");
    nTermStack.push("recebe");
    nTermStack.push("id");
  }

  //<ATRIB2> ::= <EXP>
  private static void op7(){
    nTermStack.pop(); //pop <ATRIB2>
    nTermStack.push("<EXP>");
  }

  //<ATRIB2> ::= <LEITURA>
  private static void op8(){
    nTermStack.pop(); //pop <ATRIB2>
    nTermStack.push("<LEITURA>");
  }

  //<ATRIB2> ::= TEXTO
  private static void op9(){
    nTermStack.pop(); //pop <ATRIB2>
    nTermStack.push("string");
  }

  //<LEITURA> ::= "leia"
  private static void op10(){
    nTermStack.pop(); //pop <LEITURA>
    nTermStack.push("leia");
  }

  //<EXP> ::= <FATOR> <TERMO>
  private static void op11(){
    nTermStack.pop(); //pop <EXP>
    nTermStack.push("<TERMO>");
    nTermStack.push("<FATOR>");
  }

  //<TERMO> ::= <SOMA> <FATOR> <TERMO>
  private static void op12(){
    nTermStack.push("<FATOR>");
    nTermStack.push("<SOMA>");
  }

  //<TERMO> ::= î
  private static void op13(){
    nTermStack.pop(); //pop <TERMO>
  }

  //<FATOR> ::= <OPERANDO> <FATOR2>
  private static void op14(){
    nTermStack.pop(); //pop <FATOR>
    nTermStack.push("<FATOR2>");
    nTermStack.push("<OPERANDO>");
  }

  //<FATOR2> ::= <MULT> <OPERANDO> <FATOR2>
  private static void op15(){
    nTermStack.push("<OPERANDO>");
    nTermStack.push("<MULT>");
  }

  //<FATOR2> ::= î
  private static void op16(){
    nTermStack.pop(); //pop <FATOR2>
  }

  //<OPERANDO> ::= ID
  private static void op17(){
    nTermStack.pop(); //pop <OPERANDO>
    nTermStack.push("id");
  }

  //<OPERANDO> ::= NUMERO
  private static void op18(){
    nTermStack.pop(); //pop <OPERANDO>
    nTermStack.push("numero");
  }

  //<OPERANDO> ::= "(" <EXP> ")"
  private static void op19(){
    nTermStack.pop(); //pop <OPERANDO>
    nTermStack.push("fpar");
    nTermStack.push("<EXP>");
    nTermStack.push("apar");
  }

  //<SOMA> ::= "+"
  private static void op20(){
    nTermStack.pop(); //pop <SOMA>
    nTermStack.push("mais");
  }

  //<SOMA> ::= "-"
  private static void op21(){
    nTermStack.pop(); //pop <SOMA>
    nTermStack.push("menos");
  }

  //<MULT> ::= "/"
  private static void op22(){
    nTermStack.pop(); //pop <MULT>
    nTermStack.push("div");
  }

  //<MULT> ::= "*"
  private static void op23(){
    nTermStack.pop(); //pop <MULT>
    nTermStack.push("mult");
  }

  //<ESCRITA> ::= "escreva" <TEXT> FINAL
  private static void op24(){
    nTermStack.pop(); //pop <ESCRITA>
    nTermStack.push("final");
    nTermStack.push("<TEXT>");
    nTermStack.push("escreva");
  }

  //<TEXT> ::= TEXTO <TEXT2>
  private static void op25(){
    nTermStack.pop(); //pop <TEXT>
    nTermStack.push("<TEXT2>");
    nTermStack.push("string");
  }

  //<TEXT> ::= <EXP> <TEXT2>
  private static void op26(){
    nTermStack.pop(); //pop <TEXT>
    nTermStack.push("<TEXT2>");
    nTermStack.push("<EXP>");
  }

  //<TEXT2> ::= "," <TEXT>
  private static void op27(){
    nTermStack.pop(); //pop <TEXT2>
    nTermStack.push("<TEXT>");
    nTermStack.push("virgula");
  }

  //<TEXT2> ::= î
  private static void op28(){
    nTermStack.pop(); //pop <TEXT2>
  }

  //<COND> ::= "se" <BOOLEXP> "{" <LINHAS> "}" <SENAO>
  private static void op29(){
    nTermStack.pop(); //pop <COND>
    nTermStack.push("<SENAO>");
    nTermStack.push("fchave");
    nTermStack.push("<LINHAS>");
    nTermStack.push("achave");
    nTermStack.push("<BOOLEXP>");
    nTermStack.push("se");
  }

  //<BOOLEXP> ::= <BOOLFATOR> <BOOLTERMO>
  private static void op30(){
    nTermStack.pop(); //pop <BOOLEXP>
    nTermStack.push("<BOOLTERMO>");
    nTermStack.push("<BOOLFATOR>");
  }

  //<BOOLTERMO> ::= <BOOLOPERADOR> <BOOLFATOR> <BOOLTERMO>
  private static void op31(){
    nTermStack.push("<BOOLFATOR>");
    nTermStack.push("<BOOLOPERADOR>");
  }

  //<BOOLTERMO> ::= î
  private static void op32(){
    nTermStack.pop(); //pop <BOOLTERMO>
  }

  //<BOOLFATOR> ::= <EXP>
  private static void op33(){
    nTermStack.pop(); //pop <BOOLFATOR>
    nTermStack.push("<EXP>");
  }

  //<BOOLFATOR> ::= "[" <BOOLEXP> "]"
  private static void op34(){
    nTermStack.pop(); //pop <BOOLFATOR>
    nTermStack.push("fcolch");
    nTermStack.push("<BOOLEXP>");
    nTermStack.push("acolch");
  }

  //<BOOLOPERADOR> ::= ">"
  private static void op35(){
    nTermStack.pop(); //pop <BOOLOPERADOR>
    nTermStack.push("maior");
  }

  //<BOOLOPERADOR> ::= "<"
  private static void op36(){
    nTermStack.pop(); //pop <BOOLOPERADOR>
    nTermStack.push("menor");
  }

  //<BOOLOPERADOR> ::= "=="
  private static void op37(){
    nTermStack.pop(); //pop <BOOLOPERADOR>
    nTermStack.push("igual");
  }

  //<LINHAS> ::= <LINHA> <LINHAS>
  private static void op38(){
    nTermStack.push("<LINHA>");
  }

  //<LINHAS> ::= î
  private static void op39(){
    nTermStack.pop(); //pop <LINHAS>
  }

  //<SENAO> ::= "senao" <SE>
  private static void op40(){
    nTermStack.pop(); //pop <SENAO>
    nTermStack.push("<SE>");
    nTermStack.push("senao");
  }

  //<SENAO> ::= î
  private static void op41(){
    nTermStack.pop(); //pop <SENAO>
  }

  //<SE> ::= "{" <LINHAS> "}" <SENAO>
  private static void op42(){
    nTermStack.pop(); //pop <SE>
    nTermStack.push("<SENAO>");
    nTermStack.push("fchave");
    nTermStack.push("<LINHAS>");
    nTermStack.push("achave");
  }

  //<SE> ::= <COND>
  private static void op43(){
    nTermStack.pop(); //pop <SE>
    nTermStack.push("<COND>");
  }

  //<LOOP> ::= "enquanto" <BOOLEXP> "{" <LINHAS> "}"
  private static void op44(){
    nTermStack.pop(); //pop <LOOP>
    nTermStack.push("fchave");
    nTermStack.push("<LINHAS>");
    nTermStack.push("achave");
    nTermStack.push("<BOOLEXP>");
    nTermStack.push("enquanto");
  }

}
