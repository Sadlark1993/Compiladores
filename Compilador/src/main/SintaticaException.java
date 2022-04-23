/*
    Excecao que sera lancada quando tentar ler uma linha inexistente do arquivo.
 */
package main;


public class SintaticaException extends Exception{
    public SintaticaException(){
        super("Erro sintático.");
    }
    public SintaticaException(String msg){
        super(msg);
    }
}
