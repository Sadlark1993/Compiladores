/*
************Analisador Semantico do Compilador**************
*Notifica os seguintes erros semanticos:
-->Erro de tipo de variavel: a := 2 + 'palavra';
-->Erro de divisao por zero: a:=5; b:=25; c = 1/(a*a-b);
-->Erro de uso de variaveis nulas (NullPointerException);
-->Erro de escopo (declarar uma variavel dentro de um bloco e tentar usa-la fora dele).
-->O uso de palavras reservadas indevidamente ja eh verificado pelo
    analisador sintatico.

*/

package main;

import java.util.HashMap;
import java.util.List;

import externalClasses.Evaluation;

import java.util.ArrayList;

public class AnalisadorSemantico {
  //private static HashMap<String, Object> variavel;
  private static int linha = 0;
  private static List<String> tipoList = new ArrayList<>();

  public static int analisar(final List<String> lexemasList, final List<String> tokensList, HashMap<String, String> variaveis){
    String token, lexema, valorVar;
    while(linha<tokensList.size()){
      lexema = lexemasList.get(linha);
      token = tokensList.get(linha);
      //System.out.println(lexema);

      //verificando a variavel
      if(token.equals("id")){

        /*verificando atribuicoes de valores a variaveis. Corrigindo o erro de divisao por zero,
        armazenando o valor e o tipo da variavel.*/
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
            //System.out.println(lexema+" = "+variaveis.get(lexema));//debug-----
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
              valorVar = String.valueOf(Evaluation.eval(valorVar));
              if(valorVar.equals("Infinity")){
                System.out.println("Erro semantico. Divisao por zero.");
                return linha;
              }
            }
            variaveis.put(lexema, valorVar);
            //System.out.println(lexema+" = "+valorVar);//debug+++   
          }
        }
      
      //verificar se nao tem divisao por zero nas expressoes das condicionais e escrita
      }else if(tokensList.get(linha).equals("se")||tokensList.get(linha).equals("enquanto")||tokensList.get(linha).equals("escreva")){

        //vai executar ate encontrar o fim da condicional ou da excrita. Procurando por div.
        for(int aux = linha+1;!tokensList.get(aux).equals("final")&&!tokensList.get(aux).equals("achave");aux++){
          if(tokensList.get(aux).equals("div")){

            //se o sinal de divisao for seguido de abre parenteses, devera verificar se a expressao dentro de parenteses nao da zero.
            if(tokensList.get(aux+1).equals("apar")){
              valorVar = "";

              //armazena toda a expressao em valorVar para calcular por .eval.
              for(aux=aux+2;!tokensList.get(aux).equals("fpar");aux++){

                //se for variavel, pega o valor armazenado nela.
                if(tokensList.get(aux).equals("id"))
                  valorVar+=variaveis.get(lexemasList.get(aux));
                else
                  valorVar+=lexemasList.get(aux);
              }
              
              //verifica se valorVar da zero.
              if(Evaluation.eval(valorVar)==0){
                System.out.println("Erro semantico. Divisao por zero.");
                return linha;
              }
            }else{
              //se for o numero zero:
              if(tokensList.get(aux+1).equals("numero")&&Evaluation.eval(lexemasList.get(aux+1)) == 0){
                System.out.println("Erro semantico. Divisao por zero.");
                return linha;

              //se for uma variavel com zero dentro:
              }else if(Evaluation.eval(variaveis.get(lexemasList.get(aux+1)))==0){
                System.out.println("Erro semantico. Divisao por zero.");
                return linha;
              }
               
            }
          
          //verifica se alguma variavel na condicional e no 'escreva' nao tem valor nulo
          }else if(tokensList.get(aux).equals("id")){
            if(!variaveis.containsKey(lexemasList.get(aux))||variaveis.get(lexemasList.get(aux)).equals(null)){
              System.out.println("Erro semantico. Variavel com valor nulo.");
              return aux;

            }
          }


        }

      /*Se o analisador encontrar uma abertura de chave, ele entra em outro escopo. Para isso, ele chama 
      novamente o metodo AnalisadorSemantico.analisar, para analisar aquele bloco de codigo, sem criar
      nenhuma variavel nova no escopo externo.*/
      }else if(tokensList.get(linha).equals("achave")){
        linha++;
        HashMap<String, String> variaveisEscopo = new HashMap<>();
        variaveisEscopo.putAll(variaveis);;
        int resultado = analisar(lexemasList, tokensList, variaveisEscopo);

        //atualiza o valor das variaveis desse escopo que foram modificados pelo escopo filho.
        for(String key:variaveisEscopo.keySet()){
          if(variaveis.containsKey(key)){
            variaveis.put(key, variaveisEscopo.get(key));
          }
        }

        //se encontrou um erro no bloco, ele reporta a classe main.
        if(resultado != -1)
          return linha;

      //se quando abre chave o metodo chama a si mesmo, quando fecha chave ele tem que retornar.
      }else if(tokensList.get(linha).equals("fchave"))
        return -1;
      linha++;
    }
    return -1;//valor padrao caso nao tenha encontrado erro.
  }
}
