//um compliador


package main;


public class MainClass {

    public static void main(String[] args) {
        
        try{
            System.out.println(AnalisadorLexico.analisar("codigo.txt"));
        }catch(LexicaException e){
            System.out.println(e.getMessage());
        }
              
        //testanto string
        /*
        String a = "cachorro";
        switch (a.charAt(1)){
            case 'a':
                System.out.println("a letra a"); //imprimiu isso
                break;
            case 'b':
                System.out.println("a letra b");
                break;
            default:
                System.out.println("deu errado");
        }
        */

        /*
        String coisa = "coisa";
        coisa += 's';
        System.out.println(coisa);
        */
    }

}
