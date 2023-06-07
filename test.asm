pushr FP
pushr AL
subi SP 1
pushr FP
move AL T1
pushr T1
move SP FP
addi FP 2
move FP AL
subi AL 1
jsub function0
move AL T1
subi T1 1
store A0 0(T1)
halt 

function0: 
pushr RA
storei A0 1
move AL T1
store T1 0(T1)
subi T1 1
load A0 0(T1)
addi SP 0
popr RA
addi SP 1
popr FP
move FP AL
subi AL 1
rsub RA
