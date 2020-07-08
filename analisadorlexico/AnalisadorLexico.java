/*Lexica da linguagem:

TOKEN           LEXEMA

ENTRADA         leia
SAIDA           escreva
TIPO            inteiro
ID              [a-z]([a-z]|[0-9])*
FIM_LINHA       ;
RECEBE          =
ARITMETICA      +,-,*,:
LOGICA          &,|
FINAL           fim
VALOR           ([0-9])*
PARENTESES       (,)               */


package analisadorlexico;

import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AnalisadorLexico{
    
    boolean verbose = false;
    //coluna e linha definirao a posicao de leitura atual no arquivo.
    protected int numColuna = 0; 
    protected int numLinha = 0;
    
    /*para conseguir apontar o inicio de cada lexema, o numero de caracteres que
    o compoe serao contados. */
    protected int letras = 0;
    
    /*o arquivo sera lido e quardado numa string, aux guardara o indice de leitura
    dessa string.*/
    protected int aux = 0;
    
    //indica o fim do processo de escrita da tabela de tokens.
    boolean fim = false;
    
    protected String nomeArquivo, tabela = "TOKEN\t\tLEXEMA\t\t\tLINHA\tCOLUNA\n\n", lexema;
    
    //construtor:
    public AnalisadorLexico(String nome){
        nomeArquivo = nome;
    }
    public String gerarTabela() throws IOException {
        
        File arquivo = new File(nomeArquivo);
        FileReader fr = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(fr);
        String codigo = br.readLine();
        do{
            codigo = codigo.concat("\n"+br.readLine());
        }while(br.ready());
        char caracteres[] = codigo.toCharArray();
        aux = 0;
        
        while(!fim){
            q0(caracteres);
        }
        
        return tabela;
    }
    public void q0(char caracteres[]){
        if(verbose)
            System.out.println("entrou q0;");
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            return;
        }
        aux++;
        numColuna++;
        letras++;
        //Character.isAlphabetic('a');
        switch (caracter) {
            case 'l':
                q1(caracteres);
                break;
            case 'e':
                q5(caracteres);
                break;
            case 'p':
                q12(caracteres);
                break;
            case 'f':
                q16(caracteres);
                break;
            case 'i':
                q19(caracteres);
                break;
            case ';':
                q26(caracteres);
                break;
            case '=':
                q27(caracteres);
                break;
            case '+':
                q28(caracteres);
                break;
            case '-':
                q28(caracteres);
                break;
            case '*':
                q28(caracteres);
            case ':':
                q28(caracteres);
                break;
            case '&':
                q29(caracteres);
                break;
            case '|':
                q29(caracteres);
            case ' ':
                letras = 0;
                break;
            case '\n':
                letras = 0;
                numLinha++;
                numColuna =0;
                break;
            case '\t':
                letras = 0;
                numLinha++;
                break;
            case '\r':
                letras = 0;
                numLinha++;
                numColuna = 0;
                break;
            case '(':
                q32(caracteres);
                break;
            case ')':
                q32(caracteres);
                break;
            default:
                if(Character.isDigit(caracter)){
                    q31(caracteres);
                }else if(Character.isAlphabetic(caracter)){
                    q30(caracteres);
                }else{
                    System.out.println("Erro! Caractere "+ caracter+ " inesperado na linha "+ numLinha+" coluna "+numColuna);
                    throw new RuntimeException("CARACTERE INESPERADO");
                }
                break;
        }
    }
    public void q1(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'e'){
            aux++;
            numColuna++;
            letras++;
            q2(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q2(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'i'){
            aux++;
            numColuna++;
            letras++;
            q3(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q3(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'a'){
            aux++;
            numColuna++;
            letras++;
            q4(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q4(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ENTRADA\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ENTRADA\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
        
    }
    public void q5(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 's'){
            aux++;
            numColuna++;
            letras++;
            q6(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q6(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'c'){
            aux++;
            numColuna++;
            letras++;
            q7(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q7(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'r'){
            aux++;
            numColuna++;
            letras++;
            q8(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q8(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'e'){
            aux++;
            numColuna++;
            letras++;
            q9(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q9(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'v'){
            aux++;
            numColuna++;
            letras++;
            q10(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q10(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'a'){
            aux++;
            numColuna++;
            letras++;
            q11(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q11(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("SAIDA\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("SAIDA\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q12(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'r'){
            aux++;
            numColuna++;
            letras++;
            q13(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q13(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'o'){
            aux++;
            numColuna++;
            letras++;
            q14(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q14(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'g'){
            aux++;
            numColuna++;
            letras++;
            q15(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q15(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("INICIO\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("INICIO\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q16(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'i'){
            aux++;
            numColuna++;
            letras++;
            q17(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q17(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'm'){
            aux++;
            numColuna++;
            letras++;
            q18(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q18(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("FINAL\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("FINAL\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q19(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'n'){
            aux++;
            numColuna++;
            letras++;
            q20(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q20(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 't'){
            aux++;
            numColuna++;
            letras++;
            q21(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q21(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'e'){
            aux++;
            numColuna++;
            letras++;
            q22(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q22(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'i'){
            aux++;
            numColuna++;
            letras++;
            q23(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q23(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'r'){
            aux++;
            numColuna++;
            letras++;
            q24(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q24(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(caracter == 'o'){
            aux++;
            numColuna++;
            letras++;
            q25(caracteres);
        }else if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q25(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("TIPO\t\t"+lexema+"\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("TIPO\t\t"+lexema+"\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q26(char caracteres[]){
        lexema = " ";
        for(int i=letras;i>0;i--){
            lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
        }
        tabela = tabela.concat("FIM_LINHA\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
        letras = 0;
    }
    public void q27(char caracteres[]){
        lexema = " ";
        for(int i=letras;i>0;i--){
            lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
        }
        tabela = tabela.concat("RECEBE\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
        letras=0;
    }
    public void q28(char caracteres[]){
        lexema = " ";
        for(int i=letras;i>0;i--){
            lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
        }
        tabela = tabela.concat("ARITMETICA\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
        letras=0;
    }
    public void q29(char caracteres[]){
        lexema = " ";
        for(int i=letras;i>0;i--){
            lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
        }
        tabela = tabela.concat("LOGICA\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
        letras=0;
    }
    public void q30(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(Character.isAlphabetic(caracter)||Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q30(caracteres);
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("ID\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q31(char caracteres[]){
        char caracter = ' ';
        try{
            caracter = caracteres[aux];
        }catch(ArrayIndexOutOfBoundsException e){
            fim = true;
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("VALOR\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
            return;
        }
        if(Character.isDigit(caracter)){
            aux++;
            numColuna++;
            letras++;
            q31(caracteres);
        }else if(Character.isAlphabetic(caracter)){
            System.out.println("Erro! Caractere "+ caracter+ " inesperado na linha "+ numLinha+" coluna "+numColuna);
            throw new RuntimeException("CARACTERE INESPERADO");
        }else{
            lexema = " ";
            for(int i=letras;i>0;i--){
                lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
            }
            tabela = tabela.concat("VALOR\t\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
            letras = 0;
        }
    }
    public void q32(char caracteres[]){
        lexema = " ";
        for(int i=letras;i>0;i--){
            lexema = lexema.concat(String.valueOf(caracteres[aux-i]));
        }
        tabela = tabela.concat("PARENTESES\t"+lexema+"\t\t\t"+String.valueOf(numLinha)+"\t"+String.valueOf(numColuna-letras)+"\n");
        letras=0;
    }
}