pushr FP
pushr AL
halt 

function0: 
pushr RA
storei A0 1
popr RA
addi SP 1
popr FP
move FP AL
subi AL 1
rsub RA
