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

	@scanf
	ldr r0, =_fmtInt
	ldr r1, =c
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

	@Condicional
	ldr r1, =b
	ldr r1, [r1]
	ldr r2, =c
	ldr r2, [r2]
	cmp r1, r2
	bhi _L2
	b _L3
_L2:

	@escreva
	ldr r1, =a
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto3
	bl printf

	@escreva
	ldr r1, =b
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto5
	bl printf

	@escreva
	ldr r1, =c
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto7
	bl printf
	b _L4
_L3:

	@Condicional
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =c
	ldr r2, [r2]
	cmp r1, r2
	bhi _L5
	b _L6
_L5:

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
	ldr r0, =_texto9
	bl printf

	@atribuicao simples
	ldr r1, =c
	ldr r1, [r1]
	ldr r2, =_var1
	str r1, [r2]

	@escreva
	ldr r1, =_var1
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto11
	bl printf

	@atribuicao simples
	ldr r1, =b
	ldr r1, [r1]
	ldr r2, =_var2
	str r1, [r2]

	@escreva
	ldr r1, =_var2
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto13
	bl printf
	b _L4
_L6:

	@atribuicao simples
	ldr r1, =c
	ldr r1, [r1]
	ldr r2, =_var3
	str r1, [r2]

	@escreva
	ldr r1, =_var3
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto15
	bl printf

	@atribuicao simples
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =_var4
	str r1, [r2]

	@escreva
	ldr r1, =_var4
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto17
	bl printf

	@atribuicao simples
	ldr r1, =b
	ldr r1, [r1]
	ldr r2, =_var5
	str r1, [r2]

	@escreva
	ldr r1, =_var5
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto19
	bl printf
_L4:
	b _L7
_L1:

	@Condicional
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =c
	ldr r2, [r2]
	cmp r1, r2
	bhi _L8
	b _L9
_L8:

	@escreva
	ldr r1, =b
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto21
	bl printf

	@escreva
	ldr r1, =a
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto23
	bl printf

	@escreva
	ldr r1, =c
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto25
	bl printf
	b _L7
_L9:

	@Condicional
	ldr r1, =b
	ldr r1, [r1]
	ldr r2, =c
	ldr r2, [r2]
	cmp r1, r2
	bhi _L10
	b _L11
_L10:

	@escreva
	ldr r1, =b
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto27
	bl printf

	@escreva
	ldr r1, =c
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto29
	bl printf

	@escreva
	ldr r1, =a
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto31
	bl printf
	b _L7
_L11:

	@escreva
	ldr r1, =c
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto33
	bl printf

	@escreva
	ldr r1, =b
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto35
	bl printf

	@escreva
	ldr r1, =a
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto37
	bl printf
_L7:

	@escreva
	ldr r0, =_texto38
	bl printf

pop {ip, pc}

@Variaveis
.data
	_fmtInt:		.string "%d"
	a:		.word 1
	b:		.word 1
	c:		.word 1
	_texto0:		.string "insira os tres valores:\n"
	_texto1:		.string "\n\n"
	_texto3:		.string ", "
	_texto5:		.string " "
	_texto7:		.string "\n"
	_var0:		.word 1
	_texto9:		.string " "
	_var1:		.word 1
	_texto11:		.string " "
	_var2:		.word 1
	_texto13:		.string "\n"
	_var3:		.word 1
	_texto15:		.string " "
	_var4:		.word 1
	_texto17:		.string " "
	_var5:		.word 1
	_texto19:		.string "\n"
	_texto21:		.string ","
	_texto23:		.string ","
	_texto25:		.string "\n"
	_texto27:		.string ","
	_texto29:		.string ","
	_texto31:		.string "\n"
	_texto33:		.string ","
	_texto35:		.string ","
	_texto37:		.string "\n"
	_texto38:		.string "\n"
