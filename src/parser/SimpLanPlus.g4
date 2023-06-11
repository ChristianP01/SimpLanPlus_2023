grammar SimpLanPlus ;

prog   : exp                                                        #singleExpProg
       | (dec)+ (stm)* (exp)?                                       #decStmExpProg
       ;

dec    : type ID ';'                                                #varDec
       | type ID '(' ( param ( ',' param)* )? ')' '{' body '}'      #funDec
       ;
         
param  : type ID ;

body   : (dec)* (stm)* (exp)?
	   ;

ifbody : (stm)* exp
       ;

stmifbody : (stm)+
          ;

type   : 'int'  
       | 'bool' 
       | 'void'
       ;  

stm    : ID '=' exp ';'                                             #assignStm
       | ID '(' (exp (',' exp)* )? ')' ';'                          #funCallStm
       | 'if' '(' cond=exp ')' '{' thenBranch=stmifbody '}' ('else' '{' elseBranch=stmifbody '}')?   #ifStm
	   ;
           
exp    :  INTEGER #intExp | ('true' | 'false')                          #boolExp
       | ID                                                             #varExp
       | '!' exp                                                        #negExp
       | left=exp op=('*' | '/') right=exp                                            #multDivExp
       | left=exp op=('+' | '-') right=exp                                            #sumSubExp
       | left=exp op=('>' | '<' | '>=' | '<=' | '==') right=exp                       #intCompExp
       | left=exp op=('&&' | '||') right=exp                                          #boolBinExp
       | 'if' '(' cond=exp ')' '{' thenBranch=ifbody '}' 'else' '{' elseBranch=ifbody '}'        #ifExp
       | '(' exp ')'                                                    #baseExp
       | ID '(' (exp (',' exp)* )? ')'                                  #funExp
       ;
 
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

//Numbers
fragment DIGIT  : '0'..'9';    
INTEGER         : DIGIT+;

//IDs
fragment CHAR   : 'a'..'z' |'A'..'Z' ;
ID              : CHAR (CHAR | DIGIT)* ;

//ESCAPE SEQUENCES
WS              : (' '|'\t'|'\n'|'\r')-> skip;
LINECOMENTS     : '//' (~('\n'|'\r'))* -> skip;
BLOCKCOMENTS    : '/*'( ~('/'|'*')|'/'~'*'|'*'~'/'|BLOCKCOMENTS)* '*/' -> skip;