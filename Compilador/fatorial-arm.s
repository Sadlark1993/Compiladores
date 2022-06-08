.text
.align 4
.global main

@funcao de divisao
_divisao:
	sub r1, r1, r2
	add r3, r3, #1
	cmp r1, r2
	bhs _divisao @branch if(r1<=r2)
	mov r2, r1
	mov r1, r3
	bx lr

main:
	push {ip,lr}

	@escreva
	ldr r0, =_texto0
	bl printf

	@scanf
	ldr r0, =_fmtInt
	ldr r1, =a
	bl scanf

	@calcula dois numeros e guarda em b
	ldr r1, =a
	ldr r1, [r1]
	mov r2, #1
	sub r1, r1, r2
	ldr r2, =b
	str r1, [r2]
_L0:

	@Condicional
	ldr r1, =b
	ldr r1, [r1]
	mov r2, #0
	cmp r1, r2
	bhi _L1
	b _L2
_L1:

	@calcula dois numeros e guarda em a
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =b
	ldr r2, [r2]
	mul r1, r1, r2
	ldr r2, =a
	str r1, [r2]

	@calcula dois numeros e guarda em b
	ldr r1, =b
	ldr r1, [r1]
	mov r2, #1
	sub r1, r1, r2
	ldr r2, =b
	str r1, [r2]
	b _L0
_L2:

	@escreva
	ldr r0, =_texto1
	bl printf

	@atribuicao simples
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =_var0
	str r1, [r2]

	@escreva
	ldr r1, =_var0
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto3
	bl printf

pop {ip, pc}

@Variaveis
.data
	_fmtInt:		.string "%d"
	a:		.word 1
	b:		.word 1
	_texto0:		.string "insira o valor para o fatorial:\n"
	_texto1:		.string "resultado: "
	_var0:		.word 1
	_texto3:		.string "\n"
