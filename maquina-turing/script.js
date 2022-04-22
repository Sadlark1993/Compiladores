/*Exercicio:
Faça um programa, que implemente uma Máquina de Turing para reconhecer
uma linguagem L definida por  L = {0n1n | n>=1}.

Exemplos de palavras reconhecidas:

01

0011

000111

00001111
*/

/*
--------------------linguagem: javaScript--------------------
-> Para executar o codigo, abra index.html no seu navegador e pressione 
   cntrl+shift+j, o console vai abrir (geralmente do lado direito).
*/


let vIniciais = [0,1]; //valares possiveis na fita antes do processamento.
let vFinais = ['zero', 'um'];
let estados = ['q0', 'q1', 'q2', 'q3', 'q4'];
let branco = 'B';
let vInicial = 'D'; //valor inicial na fita
let estado = estados[0];
let cabeca = 0;

//definindo conteudo da fita
let fita = ['D', 0, 0, 0, 1, 1, 1, 'B'];

//iniciando o processo ao iniciar o primeiro estado da maquina de Turing:
console.log(q0());

//concatena em Strin e imprime o valor final da fita:
let fita2 = '';
fita.forEach((item) => {
    fita2 += item+', ';
});
console.log(fita2);


//algoritmo da maquina
function q0(){
    if(fita[cabeca] === vInicial){
        cabeca++;
        return q0();
    }else if(fita[cabeca] === 0){
        fita[cabeca] = 'zero';
        cabeca++;
        return q1();
    }else if(fita[cabeca] === 'um'){
        cabeca++;
        return q3();
    }else if(fita[cabeca] === 'B'){
        cabeca++;
        return q4();
    }else{
        return 'Palavra não reconhecida.';
    }
}

function q1(){
    if(fita[cabeca] === 0){
        cabeca++;
        return q1();
    }else if(fita[cabeca] === 1){
        fita[cabeca] = 'um';
        cabeca--;
        return q2();
    }else if(fita[cabeca] === 'um'){
        cabeca++;
        return q1();
    }else{
        return 'Palavra não reconhecida.';
    }
}

function q2(){
    if(fita[cabeca] === 0){
        cabeca--;
        return q2();
    }else if(fita[cabeca] === 'zero'){
        cabeca++;
        return q0();
    }else if(fita[cabeca] === 'um'){
        cabeca--;
        return q2();
    }else{
        return 'Palavra não reconhecida.';
    }
}

function q3(){
    if(fita[cabeca] === 'um'){
        cabeca++;
        return q3();
    }else if(fita[cabeca] === 'B'){
        cabeca--;
        return q4();
    }else{
        return 'Palavra não reconhecida';
    }
}

function q4(){
    return 'Palavra Reconhecida'
}