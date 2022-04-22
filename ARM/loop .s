/* hello world */

.global _start

_start:
	MOV R0, #10
	MOV R1, #1
	B _loop

_decrementar:
	SUBGT R0, R0, R1

_loop:
	CMP R0, R1
	BNE _decrementar

end:
	MOV R7, #1 @ mudar para o terminal
	SWI 0
