/*
 * Classe do Gerador de Codigo Intermediario.
 */

package main;

import java.util.List;

public class GeradorIntermediario {
  

/* Conversao para codigo de tres enderecos:
var := a+b*c-d/e;
******Processamento: ******
_var0 := b*c
--> var := a+_var1-d/2;
_var1 := d/2
--> var := a+_var1-_var2;
_var2 := a+_var1
--> var := var3-_var2;

******Codigo intermediario: *******
_var0 := b*c
_var1 := d/2
_var2 := a+_var1
var := _var3-_var2
*/

  public static void tresEnd(List<String> lexemasExp, List<String> tokensExp, List <String> lexemasInt, List<String> tokensInt) throws Exception{
    int iVar = 0;
    int flag = 1;
    if(!tokensExp.get(1).equals("recebe")){
      throw new Exception("Erro na geracao de codigo intermediario, conversao para tres enderecos: segundo token nao eh 'recebe'.");
    }


    //primeiro ele resolve as multiplicacoes e divisoes
    while(tokensExp.size()>6 && flag==1){
      flag = 0;
      for(int i=0; i<tokensExp.size(); i++){
        if(tokensExp.get(i).equals("mult")||tokensExp.get(i).equals("div")){
          //inserindo expressoes de tres enderecos na lista de tokens do codigo intermediario:
          tokensInt.add("id");
          tokensInt.add("recebe");
          tokensInt.add(tokensExp.get(i-1));
          tokensInt.add(tokensExp.get(i));
          tokensInt.add(tokensExp.get(i+1));

          // atualizando tokens da expressao:
          tokensExp.set(i, "id");
          tokensExp.remove(i+1);
          tokensExp.remove(i-1);

          //inserindo expressoes de tres enderecos na lista de lexemas do codigo intermediario:
          lexemasInt.add("_var"+iVar);
          lexemasInt.add(":=");
          lexemasInt.add(lexemasExp.get(i-1));
          lexemasInt.add(lexemasExp.get(i));
          lexemasInt.add(lexemasExp.get(i+1));

          //atualizando lexemas da expressao:
          lexemasExp.set(i, "_var"+iVar);
          lexemasExp.remove(i+1);
          lexemasExp.remove(i-1);
          iVar++;
          flag = 1;
        }
      }
    }

    //resolvendo as somas
    flag=1;
    while(tokensExp.size()>6 && flag == 1){
      flag=0;
      for(int i = 0; i<tokensExp.size(); i++){
        if(tokensExp.get(i).equals("mais")||tokensExp.get(i).equals("menos")){
          //inserindo expressoes de tres enderecos na lista de tokens do codigo intermediario:
          tokensInt.add("id");
          tokensInt.add("recebe");
          tokensInt.add(tokensExp.get(i-1));
          tokensInt.add(tokensExp.get(i));
          tokensInt.add(tokensExp.get(i+1));

          // atualizando tokens da expressao:
          tokensExp.set(i, "id");
          tokensExp.remove(i+1);
          tokensExp.remove(i-1);

          //inserindo expressoes de tres enderecos na lista de lexemas do codigo intermediario:
          lexemasInt.add("_var"+iVar);
          lexemasInt.add(":=");
          lexemasInt.add(lexemasExp.get(i-1));
          lexemasInt.add(lexemasExp.get(i));
          lexemasInt.add(lexemasExp.get(i+1));

          //atualizando lexemas da expressao:
          lexemasExp.set(i, "_var"+iVar);
          lexemasExp.remove(i+1);
          lexemasExp.remove(i-1);
          iVar++;
          flag = 1;
        }
      }

    }

    if(tokensExp.size()<=6){
      for(int i=0;i<tokensExp.size()-1;i++){
        tokensInt.add(tokensExp.get(i));
        lexemasInt.add(lexemasExp.get(i));
      }
    }else throw new Exception("Erro na geracao de codigo intermediario, conversao para tres enderecos: A expressao nao foi reduzida para o tamanho de 6 tokens.");

    System.out.println("Uma atribuicao traduzida para codigo intermediario");//debug
  }
}
