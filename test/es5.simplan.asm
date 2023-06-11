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
storei A0 10
pushr A0
move SP FP
addi FP 3
move FP AL
subi AL 1
jsub function0
addi SP 3
halt 

function0: 
pushr RA
move AL T1
subi T1 1
store A0 0(T1)
pushr A0
storei A0 0
popr T1
beq A0 T1 label2
storei A0 0
b label3
label2:
storei A0 1
label3:
storei T1 1 
beq A0 T1 label0
move AL T1
store T1 0(T1)
subi T1 1
store A0 0(T1)
pushr A0 
move AL T1
subi T1 1
store A0 0(T1)
popr T1 
mul A0 T1 
popr A0 
move AL T1
store T1 0(T1)
subi T1 1
load A0 0(T1)
pushr FP
move AL T1
store T1 0(T1)
pushr T1
move AL T1
subi T1 1
store A0 0(T1)
pushr A0 
storei A0 1
popr T1 
sub T1 A0 
popr A0 
pushr A0
move SP FP
addi FP 3
move FP AL
subi AL 1
jsub function0
b label1 
label0: 
storei A0 0
move AL T1
subi T1 1
load A0 0(T1)
label1: 
addi SP 0
popr RA
addi SP 2
popr FP
move FP AL
subi AL 1
rsub RA
