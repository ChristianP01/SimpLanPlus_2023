pushr FP
pushr AL
subi SP 1
subi SP 1
storei A0 2
move AL T1
subi T1 1
load A0 0(T1)
storei A0 3
move AL T1
subi T1 2
load A0 0(T1)
pushr FP
move AL T1
pushr T1
storei A0 1
pushr A0
move SP FP
addi FP 3
move FP AL
subi AL 1
jsub function0
addi SP 4
halt 

function0: 
pushr RA
move AL T1
store T1 0(T1)
subi T1 1
store A0 0(T1)
pushr A0
move AL T1
store T1 0(T1)
subi T1 2
store A0 0(T1)
popr T1
add A0 T1
popr A0
pushr A0
move AL T1
subi T1 1
store A0 0(T1)
popr T1
add A0 T1
popr A0
addi SP 0
popr RA
addi SP 2
popr FP
move FP AL
subi AL 1
rsub RA
