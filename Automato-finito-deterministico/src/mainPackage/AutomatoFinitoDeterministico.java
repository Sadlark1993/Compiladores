/*
    Tarefa de Compiladores, Curso de Engenharia da Computacao, Instituto Federal.
    Aluno: Claudio Zimmermann Jr.

    O codigo abaixo implementa um programa para executar um automato finito deterministico, 
cuja a tabela de transicao eh lida de um arquivo de texto, e a entrada eh inserida
pelo usuario.
    A tabela devera estar no seguinte formato, sem linhas vagas antes ou depois da tabela:

--> Primeira linha: estado inicial do automato;
--> Segunda linha: estado final do automato;
--> Demais linhas: a tabela de transicao.

    Cada linha da tabela de transicao devera estar no seguinte formato:
estadoAtual; termoDeTransicao; proximoEstado.

*******************************************************************************
==> O automato que esta no txt, eh aquele exemplo da maquina de vendas, o produto dela custa
2,50 e ela aceita moedas de 2, 1 e 0,5.
*******************************************************************************
*/


package mainPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class AutomatoFinitoDeterministico {


    public static void main(String[] args) {
        File tabela = new File("tabela.txt");
        List<String> linhas = new ArrayList<>();
        List<String> inputs = new ArrayList<>();
        try{
            Scanner leitor = new Scanner(tabela);
            while(leitor.hasNext()){
                linhas.add(leitor.nextLine());
            }
            leitor.close();
        }catch(FileNotFoundException e){
            System.out.println("Erro na leitura do arquivo. "+e.getMessage());
        }
        
        String input = JOptionPane.showInputDialog(null, "Insira as entradas, separadas por ponto e vírgula.",
                "Coleta de dados", JOptionPane.INFORMATION_MESSAGE);
        
        //o minimo de linhas para a tabela ser valida: estado inicial + estado final + transicao
        if(linhas.size()>2){
            //coletando estados inicial e final
            String estado = linhas.get(0);
            String eFinal = linhas.get(1);
            
            /*separa a String em varias strings usando ';' como separador, 
            depois converte o array resultante em lista encadeada*/
            inputs = Arrays.asList(input.split(";"));
            
            /* // --> testando:
            String[] linha = linhas.get(2).split(";");
            for(String item : linha){
                System.out.println(item.trim());
            }
            */
            int index = 0;
            boolean aux;
            try{
                while(index < inputs.size()){
                    aux = false;
                    for(int i=2; i<linhas.size();i++){
                        String[] linha = linhas.get(i).split(";");

                        //comparando o estado e o valor (tabela e input)
                        //Se esse if nao for executado nenhuma vez no for, a palavra sera rejeitada
                        if(linha[0].trim().equals(estado.trim()) && linha[1].trim().equals(inputs.get(index).trim())){
                            estado = linha[2];
                            index++;
                            aux = true;
                            break;
                        }
                    }
                    if(!aux){
                        throw new Exception("Entrada Rejeitada. O termo "+inputs.get(index)
                                +" nao foi reconhecido dentro do estado "+estado+".");
                    }

                }
                
                //verifica se o estado atual eh igual ao estado final
                if(estado.trim().equals(eFinal.trim())){
                    System.out.println("Entrada aceita.");
                }else{
                    System.out.println("Entrada rejeitada. ");
                }
                
            }catch(NullPointerException e){
                System.out.println("Entrada Rejeitada. A transicao foi finalizada em um estado intermediario.");
            }catch(Exception j){
                System.out.println(j.getMessage());
            }
            
        }
    }
}