pushr FP
pushr AL
subi SP 1
subi SP 1
storei A0 2
pushr A0
storei A0 1
storei T1 1 
beq A0 T1 label0
storei A0 3
move AL T1
subi T1 1
load A0 0(T1)
storei A0 3
b label1 
label0: 
storei A0 1
move AL T1
subi T1 1
load A0 0(T1)
storei A0 2
move AL T1
subi T1 2
load A0 0(T1)
storei A0 2
label1: 
popr T1
add A0 T1
popr A0
pushr A0
move AL T1
subi T1 1
store A0 0(T1)
pushr A0
storei A0 1
popr T1
beq A0 T1 label4
storei A0 0
b label5
label4:
storei A0 1
label5:
storei T1 1 
beq A0 T1 label2
storei A0 0
b label3 
label2: 
storei A0 1
label3: 
popr T1
add A0 T1
popr A0
halt 
