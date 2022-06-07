package main;

import java.util.List;

public class GeradorFinal {

  public static int gerar(final List<String> lexemasInt, final List<String> tokensInt, List<String> lexemasFinal, List<String> tokensFinal, List<String> variaveis, String arquivo) throws Exception{
    String varData = "@Variaveis\n.data\n";
    varData += "\t_fmtInt:\t\t.string \"%d\"";
    
    //config inicial
    String codigo = ".text\n.align 4\n.global main\n";

    //funcao de divisao (r1 = r1 / r2; r2 = resto)
    codigo += "\n@funcao de divisao\n";
    codigo += "_divisao:\n";
    codigo += "\tsub r1, r1, r2\n";
    codigo += "\tadd r3, r3, #1\n";
    codigo += "\tcmp r1, r2\n";
    codigo += "\tbhs _divisao @branch if(r1<=r2)\n";
    codigo += "\tmov r2, r1\n";
    codigo += "\tmov r1, r3\n";
    codigo += "\tbx lr\n";

    //iniciando main
    codigo += "\nmain:\n\tpush {ip,lr}\n";//iniciando codigo final ARM Assembly

    //varrer toda a lista de tokens
    for(int i=0; i<tokensInt.size(); i++){
      //declarando variaveis do usuario
      if(tokensInt.get(i).equals("tipo")){
        varData += "\t"+lexemasInt.get(i+1)+":\t\t.word 1\n";
      }else if(tokensInt.get(i).equals("recebe")){
        //declarando variaveis geradas pelo codigo
        if(!variaveis.contains(lexemasInt.get(i-1))){
          variaveis.add(lexemasInt.get(i-1));
          varData += "\t"+lexemasInt.get(i-1)+":\t\t.word 1\n";
        }
        if(tokensInt.get(i+1).equals("leia")){
          codigo += "\n@scanf\nldr r0, =fmtInt\nldr r1, ="+lexemasInt.get(i-1)+"\nbl scanf\n";

          //aqui eu verifico todas as possibilidades de atribuicao, seja simples ou de aritmetica
        }else{
          //soma
          if(tokensInt.get(i+2).equals("mais")){
            if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tadd r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tadd r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tadd r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tadd r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }
          }else if(tokensInt.get(i+2).equals("menos")){
            if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tsub r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tsub r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tsub r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tsub r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }
          }if(tokensInt.get(i+2).equals("mult")){
            if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tmul r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tmul r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tmul r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tmul r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }
          }else if(tokensInt.get(i+2).equals("div")){
            if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tmov r3, #0\n";
              codigo += "\tbl _divisao\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("numero")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tmov r3, #0\n";
              codigo += "\tbl _divisao\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tmov r3, #0\n";
              codigo += "\tbl _divisao\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tmov r3, #0\n";
              codigo += "\tbl _divisao\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }
          }else{
            //caso seja atribuicao simples: carregando o valor em R1
            if(tokensInt.get(i+1).equals("id")){
              codigo += "\n\t@atribuicao simples\n";
              codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
            }else if(tokensInt.get(i+1).equals("numero")){
              codigo += "\n\t@atribuicao simples";
              codigo += "\tldr r1, #"+lexemasInt.get(i+1)+"\n";
            }else{
              throw new Exception("Erro na geracao de codigo final: Elemento nao reconhecido na atribuicao simples");
            }

            //armazenando o valor na variavel
            codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
            codigo += "\tstr r1, [r2]\n";
          }
        }
      
      /* *****Intermediario:*****
        se a < 2 goto _L0
        goto _L1
      _L0:
        escreva 'deu certo\n'
      _L1:

        *****Final*****
        cmp r1, r2
        b _L0
        b _L1
      _L0:
        ...
      _L1:
      */

      }else if(tokensInt.get(i).equals("se")){
        codigo += "@\n\tCondicional\n";

        //carregando valores nos registradores R1 e R2
        if(tokensInt.get(i+1).equals("id")){
          codigo += "\tldr r1, ="+lexemasInt.get(i+1)+"\n";
          codigo += "\tldr r1, [r1]\n";
        }else if(tokensInt.get(i+1).equals("numero")){
          codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
        }else{
          throw new Exception("Erro na geracao de codigo finala, condicional: primeiro elemento inesperado");
        }

        if(tokensInt.get(i+3).equals("id")){
          codigo += "\tldr r2, ="+lexemasInt.get(i+3)+"\n";
          codigo += "\tldr r2, [r1]\n";
        }else if(tokensInt.get(i+3).equals("numero")){
          codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
        }else{
          throw new Exception("Erro na geracao de codigo final, condicional: segundo elemento inesperado");
        }

        codigo += "\tcmp r1, r2\n";

        if(tokensInt.get(i+2).equals("maior")){
          codigo += "\tbhi ";
        }else if(tokensInt.get(i+2).equals("menor")){
          codigo += "\tblo ";
        }else if(tokensInt.get(i+2).equals("igual")){
          codigo += "\tbeq ";
        }else{
          throw new Exception("Erro na geracao de codigo final, condicional: operador booleano nao reconhecido.");
        }

        if(tokensInt.get(i+5).equals("label")){
          codigo += lexemasInt.get(i+5)+"\n";
        }else{
          throw new Exception("Erro na geracao de codigo final, condicional: label nao reconhecido.");
        }

        i = i+5; //finalizou a conversao do condicional para codigo final

      }else if(tokensInt.get(i).equals("goto")){
        codigo += "\t b "+lexemasInt.get(++i)+"\n"; //atribui o label ja incrementando o indice
      }else if(tokensInt.get(i).equals("inicio_label")){
        codigo += lexemasInt.get(i)+"\n"; //eh igual, eh so copiar.
      }
    }

    codigo += "\npop {ip, pc}";
    codigo += varData;
    return -1;
  }
}
