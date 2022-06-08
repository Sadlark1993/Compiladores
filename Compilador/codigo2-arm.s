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

	@scanf
	ldr r0, =_fmtInt
	ldr r1, =b
	bl scanf

	@escreva
	ldr r0, =_texto1
	bl printf

	@Condicional
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =b
	ldr r2, [r2]
	cmp r1, r2
	bhi _L0
	b _L1
_L0:

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
	b _L2
_L1:

	@Condicional
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =b
	ldr r2, [r2]
	cmp r1, r2
	beq _L3
	b _L4
_L3:

	@escreva
	ldr r0, =_texto3
	bl printf
	b _L2
_L4:

	@atribuicao simples
	ldr r1, =b
	ldr r1, [r1]
	ldr r2, =_var1
	str r1, [r2]

	@escreva
	ldr r1, =_var1
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf
_L2:

	@escreva
	ldr r0, =_texto5
	bl printf

pop {ip, pc}

@Variaveis
.data
	_fmtInt:		.string "%d"
	a:		.word 1
	b:		.word 1
	_texto0:		.string "insira dois valores"
	_texto1:		.string "\no numero maior eh:\n"
	_var0:		.word 1
	_texto3:		.string "nenhum deles"
	_var1:		.word 1
	_texto5:		.string "\n"
