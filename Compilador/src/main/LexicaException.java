/*
    Excecao que sera lancada quando tentar ler uma linha inexistente do arquivo.
 */
package main;


public class LexicaException extends Exception{
    public LexicaException(){
        super("Erro lexico.");
    }
    public LexicaException(String msg){
        super(msg);
    }
}
