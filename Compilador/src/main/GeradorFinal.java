package main;

import java.util.List;

public class GeradorFinal {

  public static int gerar(final List<String> lexemasInt, final List<String> tokensInt, List<String> lexemasFinal, List<String> tokensFinal, List<String> variaveis, String arquivo){
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
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tadd r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tadd r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
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
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tsub r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tsub r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
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
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tmul r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tmul r1, r1, r2\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
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
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, #"+lexemasInt.get(i+3)+"\n";
              codigo += "\tmov r3, #0\n";
              codigo += "\tbl _divisao\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("numero")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, #"+lexemasInt.get(i+1)+"\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tmov r3, #0\n";
              codigo += "\tbl _divisao\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }else if(tokensInt.get(i+1).equals("id")&&tokensInt.get(i+3).equals("id")){
              codigo += "\n\t@soma-dois-numeros e guarda em "+lexemasInt.get(i-1)+"\n";
              codigo += "\tmov r1, ="+lexemasInt.get(i+1)+"\n";
              codigo += "\tldr r1, [r1]\n";
              codigo += "\tmov r2, ="+lexemasInt.get(i+3)+"\n";
              codigo += "\tldr r2, [r2]\n";
              codigo += "\tmov r3, #0\n";
              codigo += "\tbl _divisao\n";
              codigo += "\tldr r2, ="+lexemasInt.get(i-1)+"\n";
              codigo += "\tstr r1, [r2]\n";
            }
          }
        }
      }
    }

    codigo += "\npop {ip, pc}";
    codigo += varData;
    return -1;
  }
}
