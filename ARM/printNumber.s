.global _start

_start:
	MOV R8, #8
	ADD R8, R8, #48

	LDR R9, =num
	STR R8, [R9]

	MOV R0, #1
	LDR R1, =num
	MOV R2, #1
	MOV R7, #4
	SWI 0

.data

num:
	.ascii " "