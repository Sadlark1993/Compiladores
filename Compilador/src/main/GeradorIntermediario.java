/*
 * Classe do Gerador de Codigo Intermediario.
 */

package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class GeradorIntermediario {
  private static int iVar = 0;
  private static int iLabel = 0;

  /* 
  stack de abre e fecha chaves. Empilhe primeiro os tokens, depois os lexemas. Desempilhe primeiro os lexemas, depois os tokens.
 */
  private static Stack<List<String>> chaveStack = new Stack<List<String>>();
  private static HashMap<Integer, Integer> senaoMap = new HashMap<>();

  public static int gerar(final List<String> lexemasList, final List<String> tokensList, List<String> lexemasInt, List<String> tokensInt, List<String> variaveis, String arquivo) throws Exception {
    String codigo = decVar(lexemasInt, tokensInt, variaveis);
    List<String> tokensExp = new ArrayList<>();
    List<String> lexemasExp = new ArrayList<>();

    try{ //se o i sair do alcance de indices da lista de tokens, ele finaliza o programa e imprime o codigo.
      for (int i = 0; i < tokensList.size(); i++) {

        // resolvendo atribuicao
        /* 
        a := leia; ----> eh igual ao intermediario.
        */
        if (tokensList.get(i).equals("recebe")) { 

          //caso seja uma leitura
          if(tokensList.get(i+1).equals("leia")){
            codigo += lexemasList.get(i-1)+" := leia\n";
            tokensInt.add("id");
            tokensInt.add("recebe");
            tokensInt.add("leia");
            lexemasInt.add(lexemasList.get(i-1));
            lexemasInt.add(":=");
            lexemasInt.add("leia");
          }else{
            tokensExp.clear();
            lexemasExp.clear();
            i--;// iniciar pelo id que vai receber o valor.
            //System.out.println("(atribuicao) Enviando expressao para conversao:");// debug
            // poupulando listas de tokens e lexemas da expressao
            do {
              tokensExp.add(tokensList.get(i));
              lexemasExp.add(lexemasList.get(i));
              System.out.println(lexemasList.get(i));// debug
            } while (!tokensList.get(i++).equals("final")); // <<-----------verificar se vai dar certo
            i--;
            codigo += tresEnd(lexemasExp, tokensExp, lexemasInt, tokensInt);
          }
          //resolvendo condicional
        }else if (tokensList.get(i).equals("se")) {
          /*
          se (a+b)<(c*d){
          ...
          }
          codigo intermediario equivalente:
          
          _var0 := a+b
          _var1 := c*d
          se _var0 > _var1 goto _l1
          goto _L2
          L1:
            ...
          L2:
          */

          //o codigo da condicional ficara temporariamente guardado aqui.
          List<String> condTk = new ArrayList<>();
          List<String> condLex = new ArrayList<>();
          String cond = "se ";
          condTk.add("se");
          condLex.add("se");

          //procurando abertura de parenteses para resolver expressoes matematicas dentro da condicao 
          for(;!tokensList.get(i).equals("achave");i++){
            if(tokensList.get(i).equals("apar")){
              tokensExp.clear();
              lexemasExp.clear();
              tokensExp.add("id");
              tokensExp.add("equals");
              lexemasExp.add("_var"+iVar);
              lexemasExp.add(":=");

              //adicionando variavel que guarda valor da expressao
              cond += "_var"+iVar+" ";
              condTk.add("id");
              condLex.add("_var"+iVar);

              iVar++;

              System.out.println("Processando expressao no codigo condicional");//debug
              for(;!tokensList.get(i).equals("fpar");i++){
                tokensExp.add(tokensList.get(i));
                lexemasExp.add(lexemasList.get(i));
              }
              tokensExp.add("final");
              lexemasExp.add(";");

              codigo += tresEnd(lexemasExp, tokensExp, lexemasInt, tokensInt);

            //adicionar operador booleano, id ou numero ao codigo da condicional temporario
            }else if(tokensList.get(i).equals("maior")||tokensList.get(i).equals("menor")||tokensList.get(i).equals("igual")||tokensList.get(i).equals("id")||tokensList.get(i).equals("numero")){
              cond += lexemasList.get(i)+" ";
              condTk.add(tokensList.get(i));
              condLex.add(lexemasList.get(i));
            }
          }

          /* 
          se _var0 > _var1 goto _l1
          goto _L2
          L1: 
          */

          //inserindo cauda da condicional 'se'.
          cond += "goto _L"+iLabel+"\n"+"goto _L"+(iLabel+1)+"\n"+"_L"+iLabel+":\n";
          condTk.add("goto");
          condTk.add("label");
          condTk.add("goto");
          condTk.add("label");
          condTk.add("inicio_label");
          condLex.add("goto");
          condLex.add("_L"+iLabel);
          condLex.add("goto");
          condLex.add("_L"+(iLabel+1));
          condLex.add("_L"+iLabel+":");

          //System.out.println("condicional inserida no codigo intermediario:\n"+cond);//debug

          codigo += cond;
          tokensInt.addAll(condTk);
          lexemasInt.addAll(condLex);

          /* 
          ...
          _L2:
          */
          //empilhando os valores do fechamento de chaves
          System.out.println("(condicional) empilhando abertura de chave");
          List<String> tokensStackedList = new ArrayList<>();
          List<String> lexemasStackedList = new ArrayList<>();
          tokensStackedList.add("inicio_label");
          lexemasStackedList.add("_L"+(iLabel+1)+":");
          
          chaveStack.push(tokensStackedList);
          chaveStack.push(lexemasStackedList);

          iLabel = iLabel+2;
          // <<------------------------ falta resolver o caso do 'senao'----------------------->>
        }else if(tokensList.get(i).equals("enquanto")){
          System.out.println("enquanto...");
          
          /* 
          enquanto (a*b)>(1+c){
            ...
          } 
          ---->> intermediario:
          _var0 = a*b
          _var1 = 1+c
          _L0:
            se _var0 > _var1 goto _L1
            goto _L2
          _L1:
            ...
            goto _L0
          _L2:
          */

          //aqui ficarah temporariamente armazenado o loop em codigo intermediario
          List<String> loopTk = new ArrayList<>();
          List<String> loopLex = new ArrayList<>();
          String loop = "_L"+iLabel+":\n"+"se ";
          loopTk.add("inicio_label");
          loopTk.add("se");
          loopLex.add("_L"+iLabel+":");
          loopLex.add("se");

          for(;!tokensList.get(i).equals("achave");i++){
            if(tokensList.get(i).equals("apar")){
              tokensExp.clear();
              lexemasExp.clear();
              tokensExp.add("id");
              tokensExp.add("equals");
              lexemasExp.add("_var"+iVar);
              lexemasExp.add(":=");

              //adicionando variavel que guarda valor da expressao
              loop += "_var"+iVar+" ";
              loopTk.add("id");
              loopLex.add("_var"+iVar);

              iVar++;

              System.out.println("Processando expressao no codigo condicional do loop");//debug
              for(;!tokensList.get(i).equals("fpar");i++){
                tokensExp.add(tokensList.get(i));
                lexemasExp.add(lexemasList.get(i));
              }
              tokensExp.add("final");
              tokensExp.add(";");
              
              codigo += tresEnd(lexemasExp, tokensExp, lexemasInt, tokensInt);

            //adicionar operador booleano, id ou numero ao codigo da condicional temporario
            }else if(tokensList.get(i).equals("maior")||tokensList.get(i).equals("menor")||tokensList.get(i).equals("igual")||tokensList.get(i).equals("id")||tokensList.get(i).equals("numero")){
              loop += lexemasList.get(i)+" ";
              loopTk.add(tokensList.get(i));
              loopLex.add(lexemasList.get(i));
            }
          }
          /*
            se _var0 > _var1 goto _L1
            goto _L2
          _L1:
          */
          //Inserindo cauda do codigo intermediario do loop
          loop += "goto _L"+(iLabel+1)+"\ngoto _L"+(iLabel+2)+"\n_L"+(iLabel+1)+":\n";

          loopTk.add("goto");
          loopTk.add("label");
          loopTk.add("goto");
          loopTk.add("label");
          loopTk.add("inicio_label");

          loopLex.add("goto");
          loopLex.add("_L"+(iLabel+1));
          loopLex.add("goto");
          loopLex.add("_L"+(iLabel+2));
          loopLex.add("_L"+(iLabel+1)+":");

          codigo += loop;
          tokensInt.addAll(loopTk);
          lexemasInt.addAll(loopLex);


          /* 
          ...
          goto _L0
          _L2:
          */
          //empilhando os valores do fechamento de chaves
          System.out.println("(loop) empilhando abertura de chave");
          List<String> tokensStackedList = new ArrayList<>();
          List<String> lexemasStackedList = new ArrayList<>();

          tokensStackedList.add("goto");
          tokensStackedList.add("label");
          tokensStackedList.add("inicio_label");

          lexemasStackedList.add("goto");
          lexemasStackedList.add("_L"+iLabel);
          lexemasStackedList.add("_L"+(iLabel+2)+":");
          
          chaveStack.push(tokensStackedList);
          chaveStack.push(lexemasStackedList);

          /*
          escreva a*5, ' vezes eu escrevi ', b;

          ------------>> intermediario:

          _var0 := a*5
          escreva _var0
          escreva ' vezes eu escrevi '
          escreva b
          */
          //resolvendo comando escreva
        }else if(tokensList.get(i).equals("escreva")){

          //um escreva pode conter varios valores separados por virgula.
          for(;!tokensList.get(i).equals("final");i++){

            //cada valor vai para um comando 'escreva'
            for(;!tokensList.get(i).equals("virgula")&&!tokensList.get(i).equals("final");i++){
              if(tokensList.get(i).equals("id")||tokensList.get(i).equals("numero")){
                System.out.println("escrevendo id/numero");//debug
                
                //se o argumento for um numero ou uma variavel
                if(tokensList.get(i+1).equals("virgula")||tokensList.get(i).equals("final")){
                  System.out.println("virgula logo apos objeto");
                  codigo += "escreva "+lexemasList.get(i)+"\n";
                  tokensInt.add("escreva");
                  tokensInt.add(tokensList.get(i));
                  lexemasInt.add("escreva");
                  lexemasInt.add(lexemasList.get(i));

                //se o argumento for uma expressao
                }else{
                  int numVar = iVar;

                  //pouplando listas que irao para o metodo tresEnd
                  tokensExp.clear();
                  lexemasExp.clear();
                  tokensExp.add("id");
                  tokensExp.add("recebe");
                  lexemasExp.add("_var"+iVar);
                  lexemasExp.add(":=");
                  iVar++;//mudei agora

                  for(;!tokensList.get(i).equals("virgula")&&!tokensList.get(i).equals("final");i++){
                    tokensExp.add(tokensList.get(i));
                    lexemasExp.add(lexemasList.get(i));
                    if(tokensList.get(i).equals("final")){
                      System.out.println("(escreva) chegou no ';' no 'for' da 'expressao' ");
                      i--;
                    }
                  }
                  tokensExp.add("final");
                  lexemasExp.add(";");

                  codigo += tresEnd(lexemasExp, tokensExp, lexemasInt, tokensInt);
                  codigo += "escreva _var"+numVar+"\n";
                  tokensInt.add("escreva");
                  tokensInt.add("id");
                  lexemasInt.add("escreva");
                  lexemasInt.add("_var"+numVar);

                }
              }else if(tokensList.get(i).equals("apar")){
                int numVar = iVar;

                //pouplando listas que irao para o metodo tresEnd
                tokensExp.clear();
                lexemasExp.clear();
                tokensExp.add("id");
                tokensExp.add("equals");
                lexemasExp.add("_var"+iVar);
                lexemasExp.add(":=");
                iVar++;//mudei agora

                for(;!tokensList.get(i).equals("virgula")&&!tokensList.get(i).equals("final");i++){
                  tokensExp.add(tokensList.get(i));
                  lexemasExp.add(lexemasList.get(i));
                  if(tokensList.get(i).equals("final")){
                    System.out.println("(escreva) chegou no ';' no 'for' da 'expressao' ");
                    i--;
                  }
                }
                tokensExp.add("final");
                lexemasExp.add(";");

                codigo += tresEnd(lexemasExp, tokensExp, lexemasInt, tokensInt);
                codigo += "escreva _var"+numVar+"\n";
                tokensInt.add("escreva");
                tokensInt.add("id");
                lexemasInt.add("escreva");
                lexemasInt.add("_var"+numVar);
              }else if(tokensList.get(i).equals("string")){
                codigo += "escreva "+lexemasList.get(i)+"\n";
                tokensInt.add("escreva");
                tokensInt.add("string");
                lexemasInt.add("escreva");
                lexemasInt.add(lexemasList.get(i));
              }

              if(tokensList.get(i).equals("final")){
                System.out.println("(escreva) chegou no ';' no 'for' da 'virgula' ");
                i--;
              }
            }//fim do for 'virgula'

            if(tokensList.get(i).equals("final")){
              System.out.println("(escreva) chegou no ';' no 'for' do 'final' ");
              i--;
            }

          }//fim do for que varre o 'escreva' inteiro

        /*
          O bloco a seguir resolve o fechamento de chave. A cada abertura de chave ele empilha o que deve
        ser impresso no fechamento do bloco. Se após o fechamento de chave houver um "senao", o algoritmo
        tentarah enviar o fluxo de probrama para o fim da cadeia de "if-else". Para isso, ele usarah o 
        dicionario "senaoMap", cuja a chave eh o tamanho da pilha de chaves/2, e o conteudo eh o label que
        estarah no final da cadeia de "if-else".
        */
        }else if(tokensList.get(i).equals("fchave")){ //<<---------------ultimo tipo de token
          System.out.println("fechamento de chaves");
          if(tokensList.size()>(i+1)&&tokensList.get(i+1).equals("senao")){
            if(senaoMap.isEmpty()||!senaoMap.containsKey(chaveStack.size()/2)){
              //implementando o 'senao' no codigo enviando o fluxo de execucao para o final da cadeia de blocos.
              senaoMap.put(chaveStack.size()/2, iLabel);//armazenando o indice do label do 'senao'
              codigo += "goto _L"+iLabel+"\n";
              lexemasInt.add("goto");
              lexemasInt.add("_L"+iLabel);
              tokensInt.add("goto");
              tokensInt.add("label");
              iLabel++;//esse label (antes do incremento) serah recuperado depois pelo 'senaoMap'.
            }else{
              codigo += "goto _L"+senaoMap.get(chaveStack.size()/2)+"\n";
              lexemasInt.add("goto");
              lexemasInt.add("_L"+senaoMap.get(chaveStack.size()/2));
              tokensInt.add("goto");
              tokensInt.add("label");
            }

            //fechando chave
            List<String> lexemas = chaveStack.pop();
            for(String aux : lexemas){
              codigo += aux;
              codigo += " ";
              if(Character.isDigit(aux.charAt(aux.length()-1))||aux.charAt(aux.length()-1)==':'){
                codigo += "\n";
              }
            }
            lexemasInt.addAll(lexemas);
            tokensInt.addAll(chaveStack.pop());

            //Se apos o 'senao' nao tiver 'se', havera uma abertura de chave que deve ser empilhada
            if(tokensList.get(i+2).equals("achave")){
              System.out.println("(senao) empilhando abertura de chave");
              List<String> tokensStackedList = new ArrayList<>();
              List<String> lexemasStackedList = new ArrayList<>();
              
              chaveStack.push(tokensStackedList);
              chaveStack.push(lexemasStackedList);
            }

          //se nao tiver 'senao' apos 'fchave'
          }else{
            int chaveIndex = chaveStack.size()/2;
            List<String> lexemas = chaveStack.pop();
            for(String aux : lexemas){
              codigo += aux;
              codigo += " ";
              if(Character.isDigit(aux.charAt(aux.length()-1))||aux.charAt(aux.length()-1)==':'){
                codigo += "\n";
              }
            }
            lexemasInt.addAll(lexemas);
            tokensInt.addAll(chaveStack.pop());

            if(!senaoMap.isEmpty()&&senaoMap.containsKey(chaveIndex)){
              codigo += "_L"+senaoMap.get(chaveIndex)+":\n";
              lexemasInt.add("_L"+senaoMap.remove(chaveIndex)+":");
              tokensInt.add("inicio_label");
            }
          }

        }
      }//fim loop varredor de codigo
    }catch(IndexOutOfBoundsException e){
      System.out.println("Chegou ao fim do codigo."); 
    }

    String[] arquivo2 = arquivo.split("\\.");
    arquivo = arquivo2[0]+"-int."+arquivo2[1];
    File myObj = new File(arquivo);

    try{
      //criando arquivos
      if(myObj.createNewFile()){
          System.out.println("Arquivo de codigo intemediario criado: "+myObj.getName());
      }else{
          System.out.println("Arquivo de codigo intermediario já existe.");
      }
      
      //escrevendo em arquivos
      FileWriter myWriter = new FileWriter(arquivo);
      myWriter.write(codigo);
      myWriter.close();
      System.out.println("Codigo intermediario escrito.");      
    }catch(IOException e){
        System.out.println("Erro na escrita do codigo intermediario: "+e.toString());
    }

    return -1;
  }//fim metodo gerar


  //Declaracao de Variaveis
  public static String decVar(List<String> lexemasInt, List<String> tokensInt, List<String> variaveis) {
    String codigo = "%********inicio da declaracao de variaveis********\n";
    for (String aux : variaveis) {
      lexemasInt.add("int");
      lexemasInt.add(aux);

      tokensInt.add("tipo");
      tokensInt.add("id");

      codigo += "int " + aux + "\n";
    }
    codigo += "%********fim da declaracao de variaveis********\n";
    return codigo;
  }

  /*
   * Conversao para codigo de tres enderecos:
   * var := a+b*c-d/e;
   ****** Processamento: ******
   * _var0 := b*c
   * --> var := a+_var1-d/2;
   * _var1 := d/2
   * --> var := a+_var1-_var2;
   * _var2 := a+_var1
   * --> var := var3-_var2;
   ****** 
   * Codigo intermediario: *******
   * _var0 := b*c
   * _var1 := d/2
   * _var2 := a+_var1
   * var := _var3-_var2
   */
  public static String tresEnd(List<String> lexemasExp, List<String> tokensExp, List<String> lexemasInt,
      List<String> tokensInt) throws Exception {
    int flag = 1;
    String codigo = "";
    List<String> tokensExp2 = new ArrayList<>();
    List<String> lexemasExp2 = new ArrayList<>();
    if (!tokensExp.get(1).equals("recebe")) {
      throw new Exception(
          "Erro na geracao de codigo intermediario, conversao para tres enderecos: segundo token nao eh 'recebe'.");
    }

    // primeiro ele resolve o que estah entre parenteses usando recursividade
    while (tokensExp.size() > 6 && flag == 1) {
      flag = 0;
      for (int i = 0; i < tokensExp.size(); i++) {
        if (tokensExp.get(i).equals("apar")) {
          tokensExp2.clear();
          lexemasExp2.clear();

          // removendo o primero parenteses. O indice dos tokens subsequentes se alterara
          // em -1
          tokensExp.remove(i);
          lexemasExp.remove(i);

          // inserindo "_varX =".
          tokensExp2.add("id");
          tokensExp2.add("recebe");
          lexemasExp2.add("_var" + iVar);
          lexemasExp2.add(":=");
          iVar++;

          // populando as listas com expressoes que estao entre parenteses.
          while (!tokensExp.get(i).equals("fpar")) {
            tokensExp2.add(tokensExp.remove(i));
            lexemasExp2.add(lexemasExp.remove(i));
          }
          tokensExp2.add("final");// o ultimo indice da lista eh ignorado.
          lexemasExp2.add(";");

          // recursividade para resolver o que tem dentro dos parenteses.
          codigo += tresEnd(lexemasExp2, tokensExp2, lexemasInt, tokensInt);

          // inserir a variavel cujo valor eh o resultado do calculo entre parenteses, na
          // expressao no lugar do fpar.
          lexemasExp.set(i, lexemasExp2.get(0));
          tokensExp.set(i, tokensExp2.get(0));

          flag=1;
          break;//forcar parada do for para resetar valor do i. Reiniciar varredura.
        }
      }
    }

    // resolvendo as muitiplicacoes e divisoes
    flag = 1;
    while (tokensExp.size() > 6 && flag == 1) {
      flag = 0;
      for (int i = 0; i < tokensExp.size(); i++) {
        if (tokensExp.get(i).equals("mult") || tokensExp.get(i).equals("div")) {
          // inserindo expressoes de tres enderecos na lista de tokens do codigo
          // intermediario:
          tokensInt.add("id");
          tokensInt.add("recebe");
          tokensInt.add(tokensExp.get(i - 1));
          tokensInt.add(tokensExp.get(i));
          tokensInt.add(tokensExp.get(i + 1));

          // atualizando tokens da expressao:
          tokensExp.set(i, "id");
          tokensExp.remove(i + 1);
          tokensExp.remove(i - 1);

          // inserindo expressoes de tres enderecos na lista de lexemas do codigo
          // intermediario:
          lexemasInt.add("_var" + iVar);
          lexemasInt.add(":=");
          lexemasInt.add(lexemasExp.get(i - 1));
          lexemasInt.add(lexemasExp.get(i));
          lexemasInt.add(lexemasExp.get(i + 1));

          // inserindo codigo na String de retorno
          codigo += "_var" + iVar + " := " + " " + lexemasExp.get(i - 1) + " " + lexemasExp.get(i) + " "
              + lexemasExp.get(i + 1) + "\n";

          // atualizando lexemas da expressao:
          lexemasExp.set(i, "_var" + iVar);
          lexemasExp.remove(i + 1);
          lexemasExp.remove(i - 1);
          iVar++;
          flag = 1;
          break;//forcar parada do for para resetar valor do i. Reiniciar varredura
        }
      }
    }

    // resolvendo as somas
    flag = 1;
    while (tokensExp.size() > 6 && flag == 1) {
      flag = 0;
      for (int i = 0; i < tokensExp.size(); i++) {
        if (tokensExp.get(i).equals("mais") || tokensExp.get(i).equals("menos")) {
          // inserindo expressoes de tres enderecos na lista de tokens do codigo
          // intermediario:
          tokensInt.add("id");
          tokensInt.add("recebe");
          tokensInt.add(tokensExp.get(i - 1));
          tokensInt.add(tokensExp.get(i));
          tokensInt.add(tokensExp.get(i + 1));

          // atualizando tokens da expressao:
          tokensExp.set(i, "id");
          tokensExp.remove(i + 1);
          tokensExp.remove(i - 1);

          // inserindo expressoes de tres enderecos na lista de lexemas do codigo
          // intermediario:
          lexemasInt.add("_var" + iVar);
          lexemasInt.add(":=");
          lexemasInt.add(lexemasExp.get(i - 1));
          lexemasInt.add(lexemasExp.get(i));
          lexemasInt.add(lexemasExp.get(i + 1));

          // inserindo codigo na String de retorno
          codigo += "_var" + iVar + " := " + " " + lexemasExp.get(i - 1) + " " + lexemasExp.get(i) + " "
              + lexemasExp.get(i + 1) + "\n";

          // atualizando lexemas da expressao:
          lexemasExp.set(i, "_var" + iVar);
          lexemasExp.remove(i + 1);
          lexemasExp.remove(i - 1);
          iVar++;
          flag = 1;
          break;//forcar parada do for para resetar valor do i. Reiniciar varredura.
        }
      }

    }

    if (tokensExp.size() <= 6) {
      for (int i = 0; i < tokensExp.size() - 1; i++) {
        codigo += lexemasExp.get(i) + " ";
        tokensInt.add(tokensExp.get(i));
        lexemasInt.add(lexemasExp.get(i));
      }
      codigo += "\n";
    } else{
      System.out.println(lexemasExp.toString());
      throw new Exception(
          "Erro na geracao de codigo intermediario, conversao para tres enderecos: A expressao nao foi reduzida para o tamanho de 6 tokens.\n\n");
    }

    //System.out.println("Uma atribuicao traduzida para codigo intermediario");// debug
    return codigo;
  }
}
