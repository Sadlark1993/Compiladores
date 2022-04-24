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

    while(!tokensStack.peek().equals("$") && !nTermStack.peek().equals("$")){
      //System.out.println("loop: "+aux); //debug

      
      if(tokensStack.peek().equals( nTermStack.peek())){ //desempilhamento
        tokensStack.pop();
        nTermStack.pop();
        //System.out.println("pop"); // debug
      }else if(nTermStack.peek().equals("<COD>")){
        if(tokensStack.peek().equals("$")){
          op1();
        }else if(tokensStack.peek().equals("escreva")||tokensStack.peek().equals("se")||tokensStack.peek().equals("enquanto")||tokensStack.peek().equals("id")){
          op0();
        }else{
          throw new SintaticaException("Sem combinacao para o nao terminal <COD>. Token na pilha: "+tokensStack.peek()+".");
        }
      }else if(nTermStack.peek().equals("<LINHA>")){
        if(tokensStack.peek().equals("escreva")){
          op3();
        }else if(tokensStack.peek().equals("se")){
          op4();
        }else if(tokensStack.peek().equals("enquanto")){
          op5();
        }else if(tokensStack.peek().equals("id")){
          op2();
        }else{
          throw new SintaticaException("Sem combinacao para o nao terminal <LINHA>. Token na pilha: "+tokensStack.peek()+".");
        }
      }else if(nTermStack.peek().equals("<ATRIB>")){
        if(tokensStack.peek().equals("id")){
          op6();
        }else{
          throw new SintaticaException("Sem combinacao para o nao terminal <ATRIB>");
        }
      }else if(nTermStack.peek().equals("<ATRIB2>")){
        switch(tokensStack.peek()){
          case "apar":
            op7();
            break;
          case "leia":
            op8();
            break;
          case "id":
            op7();
            break;
          case "numero":
            op7();
            break;
          case "string":
            op9();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <ATRIB2>");
        }
      }else if(nTermStack.peek().equals("<LEITURA>")){
        if(tokensStack.peek().equals("leia")){
          op10();
        }else{
          throw new SintaticaException("Sem combinacao para o nao terminal <LEITURA>");
        }
      }else if(nTermStack.peek().equals("<ESCRITA>")){
        if(tokensStack.peek().equals("escreva")){
          op24();
        }else{
          throw new SintaticaException("Sem combinacao para o nao terminal <LEITURA>");
        }
      }else if(nTermStack.peek()=="<EXP>"){
        switch(tokensStack.peek()){
          case "apar":
            op11();
            break;
          case "id":
            op11();
            break;
          case "numero":
            op11();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <EXP>");
        }
      }else if(nTermStack.peek()=="<FATOR>"){
        switch(tokensStack.peek()){
          case "apar":
            op14();
            break;
          case "id":
            op14();
            break;
          case "numero":
            op14();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <FATOR>");
        }
      }else if(nTermStack.peek()=="<FATOR2>"){
        switch(tokensStack.peek()){
          case "mais":
            op16();
            break;
          case "menos":
            op16();
            break;
          case "mult":
            op15();
            break;
          case "div":
            op15();
            break;
          case "fpar":
            op16();
            break;
          case "virgula":
            op16();
            break;
          case "igual":
            op16();
            break;
          case "menor":
            op16();
            break;
          case "maior":
            op16();
            break;
          case "achave":
            op16();
            break;
          case "fcolch":
            op16();
            break;
          case "final":
            op16();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <FATOR2>");

        }
      }else if(nTermStack.peek()=="<OPERANDO>"){
        switch(tokensStack.peek()){
          case "apar":
            op19();
            break;
          case "id":
            op17();
            break;
          case "numero":
            op18();
            break;
          default:
          throw new SintaticaException("Sem combinacao para o nao terminal <FATOR2>");
        }
      }else if(nTermStack.peek()=="<TERMO>"){
        switch(tokensStack.peek()){
          case "mais":
            op12();
            break;
          case "menos":
            op12();
            break;
          case "fpar":
            op13();
            break;
          case "virgula":
            op13();
            break;
          case "igual":
            op13();
            break;
          case "maior":
            op13();
            break;
          case "menor":
            op13();
            break;
          case "achave":
            op13();
            break;
          case "fcolch":
            op13();
            break;
          case "final":
            op13();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <TERMO>");
        }
      }else if(nTermStack.peek()=="<SOMA>"){
        switch(tokensStack.peek()){
          case "mais":
            op20();
            break;
          case "menos":
            op21();
            break;
          default:
          throw new SintaticaException("Sem combinacao para o nao terminal <SOMA>");
        }
      }else if(nTermStack.peek()=="<MULT>"){
        switch(tokensStack.peek()){
          case "mult":
            op23();
            break;
          case "div":
            op22();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <MULT>");

        }
      }else if(nTermStack.peek()=="<TEXT>"){
        switch(tokensStack.peek()){
          case "apar":
            op26();
            break;
          case "id":
            op26();
            break;
          case "numero":
            op26();
            break;
          case "string":
            op25();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <TEXT>");
        }
      }else if(nTermStack.peek()=="<TEXT2>"){
        switch(tokensStack.peek()){
          case "virgula":
            op27();
            break;
          case "final":
            op28();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <TEXT>");
        }
      }else if(nTermStack.peek().equals("<COND>")){
        if(tokensStack.peek().equals("se")){
          op29();
        }else{
          throw new SintaticaException("Sem combinacao para o nao terminal <COND>");
        }
      }else if(nTermStack.peek().equals("<BOOLEXP>")){
        switch(tokensStack.peek()){
          case "apar":
            op30();
            break;
          case "acolch":
            op30();
            break;
          case "id":
            op30();
            break;
          case "numero":
            op30();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <BOOLEXP>");
        }
      }else if(nTermStack.peek()=="<BOOLTERMO>"){
        switch(tokensStack.peek()){
          case "igual":
            op31();
            break;
          case "menor":
            op31();
            break;
          case "maior":
            op31();
            break;
          case "achave":
            op32();
            break;
          case "fcolch":
            op32();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <BOOLEXP>");
        }
      }else if(nTermStack.peek()=="<BOOLFATOR>"){
        switch(tokensStack.peek()){
          case "apar":
            op33();
            break;
          case "acolch":
            op34();
            break;
          case "id":
            op33();
            break;
          case "numero":
            op33();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <BOOLEXP>");
        }
      }else if(nTermStack.peek()=="<BOOLOPERADOR>"){
        switch(tokensStack.peek()){
          case "igual":
            op37();
            break;
          case "menor":
            op36();
            break;
          case "maior":
            op35();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <BOOLOPERADOR>");
        }
      }else if(nTermStack.peek()=="<SENAO>"){
        switch(tokensStack.peek()){
          case "$":
            op41();
            break;
          case "escreva":
            op41();
            break;
          case "se":
            op41();
            break;
          case "senao":
            op40();
            break;
          case "enquanto":
            op41();
            break;
          case "fchave":
            op41();
            break;
          case "id":
            op41();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <SENAO>");
        }
      }else if(nTermStack.peek()=="<LINHAS>"){
        switch(tokensStack.peek()){
          case "escreva":
            op38();
            break;
          case "se":
            op38();
            break;
          case "enquanto":
            op38();
            break;
          case "fchave":
            op39();
            break;
          case "id":
            op38();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <LINHAS>");
        }
      }else if(nTermStack.peek().equals("<LOOP>")){
        if(tokensStack.peek().equals("enquanto")){
          op44();
        }else{
          throw new SintaticaException("Sem combinacao para o nao terminal <LOOP>");
        }
      }else if(nTermStack.peek().equals("<SE>")){
        switch(tokensStack.peek()){
          case "se":
            op43();
            break;
          case "achave":
            op42();
            break;
          default:
            throw new SintaticaException("Sem combinacao para o nao terminal <SE>");
        }
      }else{
        throw new SintaticaException("Erro Sintatico. Termo esperado: "+nTermStack.peek());
      }

      aux++;
    }

    System.out.println("Analise completa. Nenhum erro encontrado.");
    return -1;
  }

  //<COD> ::= <LINHA> <COD>
  private static void op0(){
    nTermStack.push("<LINHA>");
  }

  //<COD> ::= î
  private static void op1(){
    nTermStack.pop();
  }

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