
package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class AnalisadorLexico {
    /*cada linha do codigo fonte sera armazenado aqui, processado e depois 
    substituido pela proxima linha*/
    protected static String linha;
    
    // tabela de tokens: resultado final da analise lexica
    protected static String tabelaTokens; 
    
    protected static int column;
    protected static int row;
    protected static boolean notEnd;
    protected static int linhaLength;
    protected static String lexema;
    protected static int inicioLexema;
    protected static boolean verbose = false; //debug
    
    
    public static String analisar(String nomeArquivo) throws LexicaException{
        File codFonte = new File(nomeArquivo);
        tabelaTokens = "lexema; token; coluna; linha;\n";
        row = 0;
        
        try{
            Scanner leitor = new Scanner(codFonte);
            while(leitor.hasNext()){
                linha = leitor.nextLine();
                linhaLength = linha.length();
                column = 0;
                if(verbose) System.out.println("linhaLength: " + linhaLength); //debug
                while(linhaLength>column){
                    lexema = "";
                    inicioLexema = column;
                    tabelaTokens += q0();
                    
                    if(verbose)
                        System.out.println(tabelaTokens+"; "+column+"; "+row);//debug
                }
                row++;
            }
            leitor.close();
        }catch(FileNotFoundException e){
            throw new LexicaException("FileNotFoundException: "+e.getMessage());
   
        }
        
        return tabelaTokens;
    }
    
    private static String q0() throws LexicaException{
        if(verbose){
            System.out.println("--> q0; Lexema: "+lexema);
        }
        switch(linha.charAt(column)){
            case 'e':
                lexema +='e';
                return q1();
            case 'l':
                lexema +='l';
                return q15();
            case 's':
                lexema +='s';
                return q19();
            case '+':
                return q24();
            case '-':
                return q25();
            case '*':
                return q26();
            case '/':
                lexema += '/';
                return q27();
            case '(':
                return q28();
            case ')':
                return q29();
            case ':':
                return q30();
            case '=':
                return q32();
            case '>':
                return q34();
            case '<':
                return q35();
            case '{':
                return q36();
            case '}':
                return q37();
            case '[':
                return q38();
            case ']':
                return q39();
            case ';':
                return q42();
            case '\'':
                return q43();
            case '%':
                return q46();
            case ',':
                return q47();
            default:
                if(Character.isLetter(linha.charAt(column))){
                    lexema += linha.charAt(column);
                    return q40();
                }else if(Character.isDigit(linha.charAt(column))){
                    lexema += linha.charAt(column);
                    return q41();
                }else if(linha.charAt(column)==' '|| linha.charAt(column)=='\t' || 
                        linha.charAt(column)=='\n'||linha.charAt(column)=='\r'||
                        linha.charAt(column)=='\s'){
                    column++;
                    return "";
                }else{
                    throw new LexicaException("Erro. Caractere nao reconhecido "
                        + "na linha "+row+" e coluna "+column);
                }
        }
    }
    
    private static String q1(){
        if(verbose){
            System.out.println("--> q1; Lexema: "+lexema);
        }
        if(linhaLength>(++column)){
            switch(linha.charAt(column)){
                case 's':
                    lexema += 's';
                    return q2();
                case 'n':
                    lexema += 'n';
                    return q8();
                default:
                    if(Character.isLetter(linha.charAt(column)) || 
                        Character.isDigit(linha.charAt(column))){
                        lexema += linha.charAt(column);
                        return q40();
                    }else{
                        return "e ; id ; "+ String.valueOf(inicioLexema) + " ; "+
                            String.valueOf(row)+";\n";
                    }
            }
        }
        return "e ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q2(){
        if(verbose)
            System.out.println("--> q2; Lexema: "+lexema);

        if(linhaLength>++column){
            if(linha.charAt(column)=='c'){
                lexema += 'c';
                return q3();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }else{
                return "es ; id ; "+ String.valueOf(inicioLexema) + " ; "+
                    String.valueOf(row)+";\n";
            }
        }
        return "es ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q3(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='r'){
                lexema += 'r';
                return q4();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }else{
                return "esc ; id ; "+ String.valueOf(inicioLexema) + " ; "+
                    String.valueOf(row)+";\n";
            }
        }
        
        return "esc ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q4(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='e'){
                lexema += 'e';
                return q5();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }else{
                return "escr ; id ; "+ String.valueOf(inicioLexema) + " ; "+
                    String.valueOf(row)+";\n";
            }
        }
        
        return "escr ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q5(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='v'){
                lexema += 'v';
                return q6();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }else{
                return "escre ; id ; "+ String.valueOf(inicioLexema) + " ; "+
                    String.valueOf(row)+";\n";
            }
        }
        
        return "escre ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q6(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='a'){
                lexema += 'a';
                return q7();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }else{
                return "escrev ; id ; "+ String.valueOf(inicioLexema) + " ; "+
                    String.valueOf(row)+";\n";
            }
        }
        
        return "escrev ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q7(){
        if(linhaLength>++column){
            if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }else{
                return "escreva ; escreva ; "+ String.valueOf(inicioLexema) + " ; "+
                    String.valueOf(row)+";\n";
            }
        }
        
        return "escreva ; escreva ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q8(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='q'){
                lexema += 'q';
                return q9();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }else{
                return "en ; id ; "+ String.valueOf(inicioLexema) + " ; "+
                    String.valueOf(row)+";\n";
            }
        }
        
        return "en ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q9(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='u'){
                lexema += 'u';
                return q10();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "enq ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q10(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='a'){
                lexema += 'a';
                return q11();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "enqu ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q11(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='n'){
                lexema += 'n';
                return q12();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "enqua ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q12(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='t'){
                lexema += 't';
                return q13();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "enquan ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q13(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='o'){
                lexema += 'o';
                return q14();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "enquant ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: enquanto
    private static String q14(){
        if(linhaLength>++column){
            if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "enquanto; enquanto ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q15(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='e'){
                lexema += 'e';
                return q16();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "l ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q16(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='i'){
                lexema += 'i';
                return q17();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "le; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q17(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='a'){
                lexema += 'a';
                return q18();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "lei ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";    
    }
    
    //estado final: leia
    private static String q18(){
        if(linhaLength>++column){
            if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "leia ; leia ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";    }
    
    private static String q19(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='e'){
                lexema += 'e';
                return q20();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "s ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: se
    private static String q20(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='n'){
                lexema += 'n';
                return q21();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "se ; se ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";    }
    
    private static String q21(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='a'){
                lexema += 'a';
                return q22();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "sen ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";    }
    
    private static String q22(){
        if(linhaLength>++column){
            if(linha.charAt(column)=='o'){
                lexema += 'o';
                return q23();
            }else if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "sena ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";    
    }
    
    //estado final: senao
    private static String q23(){
        if(linhaLength>++column){
            if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        
        return "senao; senao ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";    
    }
    
    //estado final: mais
    private static String q24(){
        column++;
        return "+ ; mais ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: menos
    private static String q25(){
        column++;
        return "- ; menos ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: mult
    private static String q26(){
        column++;
        return "* ; mult ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: div
    private static String q27(){
        column++;
        return "/ ; div ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: apar
    private static String q28(){
        column++;
        return "( ; apar ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: fpar
    private static String q29(){
        column++;
        return ") ; fpar ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";    
    }
    
    private static String q30() throws LexicaException{
        if(linhaLength>++column){
            if(linha.charAt(column)=='='){
                lexema += '=';
                return q31();
            }
        }
        throw new LexicaException("Erro. Caractere \""+linha.charAt(column)+
            "\" nao reconhecido na linha "+row+" e coluna "+column+"."+
            " Era esperado \"=\".");
    }
    
    //estado final: recebe
    private static String q31(){
        column++;
        return ":= ; recebe ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q32() throws LexicaException{
        if(linhaLength>++column){
            if(linha.charAt(column)=='='){
                lexema += '=';
                return q33();
            }
        }
        throw new LexicaException("Erro. Caractere \""+linha.charAt(column)+
            "\" nao reconhecido na linha "+row+" e coluna "+column+"."+
            " Era esperado \"=\".");
    }
    
    //estado final: igual
    private static String q33(){
        column++;
        return "== ; igual ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: maior
    private static String q34(){
        column++;
        return "> ; maior ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: menor
    private static String q35(){
        column++;
        return "< ; menor ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: achave
    private static String q36(){
        column++;
        return "{ ; achave ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: fchave
    private static String q37(){
        column++;
        return "} ; fchave ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: acolch
    private static String q38(){
        column++;
        return "[ ; acolch ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: fcolch
    private static String q39(){
        column++;
        return "] ; fcolch ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: id
    private static String q40(){
        if(linhaLength>++column){
            if(Character.isLetter(linha.charAt(column)) || 
                Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q40();
            }
        }
        return lexema+" ; id ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: numero
    private static String q41() throws LexicaException{
        if(linhaLength>++column){
            if(Character.isDigit(linha.charAt(column))){
                lexema += linha.charAt(column);
                return q41();
            }else if(Character.isLetter(linha.charAt(column))){
               throw new LexicaException("Erro. Caractere \""+linha.charAt(column)+
                       "\" nao reconhecido na linha "+row+" e coluna "+column+".");
            }
        }
        
        return lexema+" ; numero ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    
    //estado final: final
    private static String q42(){
        column++;
        return "; ; final ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    private static String q43() throws LexicaException{
        if(linhaLength>++column){
            if(linha.charAt(column)=='\''){
                lexema += '\'';
                return q45();
            }else{
                lexema += linha.charAt(column);
                return q44();
            }
        }
        throw new LexicaException("Erro na linha "+row+" e coluna "+column+"."
            +" Era esperado \"\'\".");
    }
    
    private static String q44() throws LexicaException{
        if(linhaLength>++column){
            if(linha.charAt(column)=='\''){
                lexema += '\'';
                return q45();
            }else{
                lexema += linha.charAt(column);
                return q44();
            }
        }
        throw new LexicaException("Erro na linha "+row+" e coluna "+column+"."
            +" Era esperado \"\'\".");
    }
    
    //estado final: string
    private static String q45(){
        column++;
        return lexema+" ; string ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
    
    //estado final: comentario
    private static String q46(){
        column+=linhaLength;
        return "";
    }
    
    private static String q47(){
        column++;
        return ", ; virgula ; "+ String.valueOf(inicioLexema) + " ; "+String.valueOf(row)+";\n";
    }
}
