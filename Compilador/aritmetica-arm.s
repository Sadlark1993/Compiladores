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

	@scanf
	ldr r0, =_fmtInt
	ldr r1, =a
	bl scanf

	@scanf
	ldr r0, =_fmtInt
	ldr r1, =b
	bl scanf

	@escreva
	ldr r0, =_texto0
	bl printf

	@escreva
	ldr r0, =_texto1
	bl printf

	@calcula dois numeros e guarda em _var0
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =b
	ldr r2, [r2]
	add r1, r1, r2
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

	@calcula dois numeros e guarda em _var1
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =b
	ldr r2, [r2]
	sub r1, r1, r2
	ldr r2, =_var1
	str r1, [r2]

	@escreva
	ldr r1, =_var1
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto5
	bl printf

	@calcula dois numeros e guarda em _var2
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =b
	ldr r2, [r2]
	mul r1, r1, r2
	ldr r2, =_var2
	str r1, [r2]

	@escreva
	ldr r1, =_var2
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

	@escreva
	ldr r0, =_texto7
	bl printf

	@calcula dois numeros e guarda em _var3
	ldr r1, =a
	ldr r1, [r1]
	ldr r2, =b
	ldr r2, [r2]
	mov r3, #0
	bl _divisao
	ldr r2, =_var3
	str r1, [r2]

	@escreva
	ldr r1, =_var3
	ldr r1, [r1]
	ldr r0, =_fmtInt
	bl printf

pop {ip, pc}

@Variaveis
.data
	_fmtInt:		.string "%d"
	a:		.word 1
	b:		.word 1
	_texto0:		.string "imprimindo valores:\n"
	_texto1:		.string "soma: "
	_var0:		.word 1
	_texto3:		.string "\nsubtracao: "
	_var1:		.word 1
	_texto5:		.string "\nmultiplicacao: "
	_var2:		.word 1
	_texto7:		.string "\ndivisao "
	_var3:		.word 1
