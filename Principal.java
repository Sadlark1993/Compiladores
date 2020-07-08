
package analisadorlexico;

import java.io.IOException;

public class Principal {
    public static void main(String args[]) throws IOException{
        AnalisadorLexico analisador = new AnalisadorLexico("C:\\Users\\Junior\\Documents\\NetBeansProjects\\AnalisadorLexico\\texto.txt");
        
        String tabela = analisador.gerarTabela();
        System.out.println("\n");
        System.out.println(tabela);

    }
}
