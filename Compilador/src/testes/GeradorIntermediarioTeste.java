package testes;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import org.junit.Test;

import main.GeradorIntermediario;

public class GeradorIntermediarioTeste {
  // testando a traducao de expressoes para codigo de tres enderecos
  @Test
  public static void tresEndTeste() {
    List<String> lexemasExp = new ArrayList<>();
    List<String> tokensExp = new ArrayList<>();
    List<String> lexemasInt = new ArrayList<>();
    List<String> tokensInt = new ArrayList<>();
    String codigo = "";

    // a := b+5*(a+c)-d-e/2;
    // poupulando para testes com parenteses:
    tokensExp.add("id");
    tokensExp.add("recebe");
    tokensExp.add("id");
    tokensExp.add("mais");
    tokensExp.add("numero");
    tokensExp.add("mult");
    tokensExp.add("apar");
    tokensExp.add("id");
    tokensExp.add("mais");
    tokensExp.add("id");
    tokensExp.add("fpar");
    tokensExp.add("menos");
    tokensExp.add("id");
    tokensExp.add("menos");
    tokensExp.add("id");
    tokensExp.add("div");
    tokensExp.add("numero");
    tokensExp.add("final");
    
    lexemasExp.add("a");
    lexemasExp.add(":=");
    lexemasExp.add("b");
    lexemasExp.add("+");
    lexemasExp.add("5"); // a := b+5*(a+c)-d-e/2;
    lexemasExp.add("*");
    lexemasExp.add("(");
    lexemasExp.add("a");
    lexemasExp.add("+");
    lexemasExp.add("c");
    lexemasExp.add(")");
    lexemasExp.add("-");
    lexemasExp.add("d");
    lexemasExp.add("-");
    lexemasExp.add("e");
    lexemasExp.add("/");
    lexemasExp.add("2");
    lexemasExp.add(";");


    // a := b+5*d-e/2; -->teste sem parenteses
    /* tokensExp.add("id");
    tokensExp.add("recebe");
    tokensExp.add("id");
    tokensExp.add("mais");
    tokensExp.add("numero");
    tokensExp.add("mult");
    tokensExp.add("id");
    tokensExp.add("menos");
    tokensExp.add("id");
    tokensExp.add("div");
    tokensExp.add("numero");
    tokensExp.add("final");
    
    lexemasExp.add("a");
    lexemasExp.add(":=");
    lexemasExp.add("b");
    lexemasExp.add("+");
    lexemasExp.add("5"); // a := b+5*d-e/2;
    lexemasExp.add("*");
    lexemasExp.add("d");
    lexemasExp.add("-");
    lexemasExp.add("e");
    lexemasExp.add("/");
    lexemasExp.add("2");
    lexemasExp.add(";"); */
    

    try {
      codigo = GeradorIntermediario.tresEnd(lexemasExp, tokensExp, lexemasInt, tokensInt);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    int i=0;
    for(String aux : lexemasInt){
    i++;
    System.out.print(aux);
    if(i%5==0)
    System.out.print("\n");
    }
    
    //System.out.println(codigo);
  }

  public static void main(String[] args) {
    tresEndTeste();
  }
}
