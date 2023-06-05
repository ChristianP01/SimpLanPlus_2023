pushr FP
pushr AL
subi SP 1
move AL T1
subi T1 1
store A0 0(T1)
pushr A0
storei A0 2
popr T1
add A0 T1
popr A0
halt 
