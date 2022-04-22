.text
.align 4
.global main
main:
	@keep stack 8-byte aligned
	push {ip, lr}

	@print for operand1
	ldr r0, =message
	bl printf

	@scanf for operand1
	ldr r0, =fmtInt
	ldr r1, =num1
	bl scanf

	@print for operand2
	ldr r0, =message2
	bl printf

	@scanf for operand2
	ldr r0, =fmtInt
	ldr r1, =num2
	bl scanf

	@add
	ldr r1, =num1
	ldr r2, =num2
	ldr r1, [r1]
	ldr r2, [r2]
	add r1, r2, r1

	@print add
	ldr r0, =rSoma
	bl printf

	@sub
	ldr r1, =num1
	ldr r2, =num2
	ldr r1, [r1]
	ldr r2, [r2]
	sub r1, r1, r2

	@print sub
	ldr r0, =rSub
	bl printf

	@mult
	ldr r1, =num1
	ldr r2, =num2
	ldr r1, [r1]
	ldr r2, [r2]
	mul r1, r2, r1

	@print mult
	ldr r0, =rMult
	bl printf

	@div
	ldr r1, =num1
	ldr r2, =num2
	ldr r1, [r1]
	ldr r2, [r2]
	mov r3, #0
_division:
	sub r1, r1, r2
	add r3, r3, #1
	CMP r1, r2
	BHS _division @branch if(r1<r2)
	mov r5, r1
	mov r1, r3

_endDivision:
	@print div
	ldr r0, =rDiv
	bl printf

	@print resto
	mov r1, r5
	ldr r0, =rResto
	bl printf

	@return 0
	mov r0, #0

	@reverse align
	pop {ip, pc}

@vars and stuff
.data
	message: 	.asciz "Operando 1: \n"
	fmtInt: 	.string "%d"
	textOut: 	.asciz "num: %d\n"
	num1: 		.word 1
	num2:		.word 1
	message2: 	.asciz "Operando 2: \n"
	rSoma: 		.asciz "soma: %d\n"
	rSub:		.asciz "subtracao: %d\n"
	rMult:		.asciz "multiplicacao: %d\n"
	rDiv:		.asciz "divisao %d\n"
	rResto:		.asciz "resto %d\n"
