pushr FP
pushr AL
subi SP 1
storei A0 1
move AL T1
subi T1 1
load A0 0(T1)
pushr FP
move AL T1
pushr T1
move AL T1
subi T1 1
store A0 0(T1)
pushr A0
move SP FP
addi FP 3
move FP AL
subi AL 1
jsub function0
halt 

function0: 
pushr RA
subi SP 1
subi SP 1
storei A0 1
storei T1 1 
beq A0 T1 label0
storei A0 3
move AL T1
subi T1 3
load A0 0(T1)
storei A0 1
move AL T1
subi T1 4
load A0 0(T1)
storei A0 3
b label1 
label0: 
storei A0 1
move AL T1
subi T1 3
load A0 0(T1)
storei A0 1
move AL T1
subi T1 4
load A0 0(T1)
storei A0 2
label1: 
pushr A0
move AL T1
subi T1 4
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
addi SP 2
popr RA
addi SP 2
popr FP
move FP AL
subi AL 1
rsub RA
