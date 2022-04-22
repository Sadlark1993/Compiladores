nano/* hello world */

.global _start

_start:
	mov R7, #3 @ chamada de sistema: leitura
	mov R0, #0 @ setta o teclado como dispositivo de entrada
	mov R2, #12 @ comprimento da string
	LDR R1, =message
	SWI 0

_write:
	mov R7, #4 @ chamada de sistema: escrever na tela
	mov R0, #1 @ setta o monitor como o dispositivo de saida
	mov R2, #12 @ comprimento da string
	LDR R1, =message
	SWI 0

end:
	MOV R7, #1 @ mudar para o terminal
	SWI 0

.data
message:
	.ascii " "
	