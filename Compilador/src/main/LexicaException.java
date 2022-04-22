/*
    Excecao que sera lancada quando tentar ler uma linha inexistente do arquivo.
 */
package main;


public class LexicaException extends Exception{
    public LexicaException(){
        super("Erro. Nao ha mais linhas no arquivo.");
    }
    public LexicaException(String msg){
        super(msg);
    }
}
