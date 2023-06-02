pushr FP
pushr AL
subi SP 1
subi SP 1
storei A0 5
move AL T1
subi T1 1
load A0 0(T1)
storei A0 6
move AL T1
subi T1 2
load A0 0(T1)
move AL T1
subi T1 1
store A0 0(T1)
halt 
