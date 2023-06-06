pushr FP
pushr AL
subi SP 1
subi SP 1
subi SP 1
storei A0 2
move AL T1
subi T1 3
load A0 0(T1)
move AL T1
subi T1 3
store A0 0(T1)
pushr A0 
storei A0 1
popr T1 
bleq A0 T1 label3
label4:
storei A0 0
b label2
label3:
beq A0 T1 label4
storei A0 1
label2:
storei T1 1 
beq A0 T1 label0
move AL T1
subi T1 2
store A0 0(T1)
move AL T1
subi T1 1
load A0 0(T1)
b label1 
label0: 
move AL T1
subi T1 3
store A0 0(T1)
move AL T1
subi T1 2
load A0 0(T1)
label1: 
halt 
