
.global _start
_start:
	mov r2, #3
	mov r3, #65
	ldr r9, =num
	str r3, [r9]

_print:
	mov r7, #4
	mov r0, #1
	mov r2, #2
	ldr r1, =num
	swi 0

end:
	mov r7, #1
	swi 0

.data
num:
	.word 55