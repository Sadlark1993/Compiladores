package main;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AnalisadorSemantico {
  //private static HashMap<String, Object> variavel;
  private static int linha = 0;
  private static List<String> tipoList = new ArrayList<>();

  public static int analisar(List<String> lexemasList, List<String> tokensList, HashMap<String, String> variaveis){
    String token, lexema, valorVar;
    while(linha<tokensList.size()){
      lexema = lexemasList.get(linha);
      token = tokensList.get(linha);
      //System.out.println(lexema);

      //verificando a variavel
      if(token.equals("id")){

        //populando/atualizando dicionario de variaveis
        if(tokensList.get(linha+1).equals("recebe")){

          //para atribuicao simples
          if(tokensList.get(linha+3).equals("final")){
            //System.out.println("simples"+tokensList.get(linha)+tokensList.get(linha+1)+tokensList.get(linha+2));//debug

            if(tokensList.get(linha+2).equals("numero")){
            tipoList.add("numero"); 
            variaveis.put(lexema, lexemasList.get(linha+2));
            }else if(tokensList.get(linha+2).equals("string")){
              tipoList.add("string");
              variaveis.put(lexema, lexemasList.get(linha+2));

            //caso seja uma outra var, verifica se ela eh nula
            }else if(tokensList.get(linha+2).equals("id")){
              if(!variaveis.containsKey(lexemasList.get(linha+2))||variaveis.get(lexemasList.get(linha+2)).equals(null)){
                System.out.println("Erro semantico. Variavel nula.");
                return linha+2;
              }else{
                variaveis.put(lexema, variaveis.get(lexemasList.get(linha+2)));

                //get index of the var, to set the type of the new var
                int index = 0;
                for(String key : variaveis.keySet()){
                  if(key.equals(lexemasList.get(linha+2)))
                    break;
                  index++;
                }

                tipoList.add(tipoList.get(index));
              }
            
            }else if(tokensList.get(linha+2).equals("leia")){
              variaveis.put(lexema, "leia");
              tipoList.add("leia");
            }else{
              System.out.println("Erro semantico. Tipo nao reconhecido.");
              return linha+2;
            }
            System.out.println(lexema+" = "+variaveis.get(lexema));//debug
          }else{ //para atribuicoes de expressoes
            valorVar = "";
            boolean flag = false;
            //vai escrever toda a expressao no indice da variavel, ate chegar em ';'
            for(int aux = linha+2; !tokensList.get(aux).equals("final"); aux++){
              if(tokensList.get(aux).equals("id")){
                
                if(variaveis.get(lexemasList.get(aux)).equals("leia")){
                  valorVar = "leia";
                  flag = true;
                }else{
                  valorVar+=variaveis.get(lexemasList.get(aux));
                }

              /*se houver leitura do usuario, nao havera analise semantica no valor da var
              ele vai dar um 'break' no fim do bloco*/
              }else if(tokensList.get(aux).equals("leia")){
                valorVar = "leia";
                tipoList.add("leia");
                flag = true;
              }else{
                valorVar += lexemasList.get(aux);
              }
              
              if(valorVar.equals("leia")) break;
            }

            /*se o valor da variavel nao depender de leitura do teclado, ele sera pre calculado.
            isso ajuda a evitar divisao por 0*/
            if(!flag){

              //System.out.println(tokensList.get(linha)+" "+tokensList.get(linha+1)+" "+tokensList.get(linha+2)+" "+tokensList.get(linha+3));
              //System.out.println(valorVar);//debur
              valorVar = String.valueOf(Evaluation.eval(valorVar));
              if(valorVar.equals("Infinity")){
                System.out.println("Erro semantico. Divisao por zero.");
                return linha;
              }
            }
            variaveis.put(lexema, valorVar);
            System.out.println(lexema+" = "+valorVar);//debug
            
          }
        }

      //verificar divisao por zero
      }else if(token.equals("div")){
      
        //a := b/0;
        if(tokensList.get(linha+1).equals("numero")&&Integer.parseInt(lexemasList.get(linha+1)) == 0){
          System.out.println("Erro semantico. Divisao por zero.");
          return linha;
        
        //c := 0; a := b/c; 
        }else if(tokensList.get(linha+1).equals("id")&& Integer.parseInt(variaveis.get(lexemasList.get(linha+1)))  == 0){
          System.out.println("Erro semantico. Divisao por zero.");
          return linha;
        }
      }
      linha++;
    }
  
    return -1;
  }
}
