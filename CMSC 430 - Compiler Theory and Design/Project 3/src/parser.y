/* Project 3
 *
 * Author: Luis Moreno
 * Date: March 6, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 *
 * This bison file was altered to allow the following:
 * implementation of all arithmetic, relational, and logical operators,
 * compiled text files return a value,
 * parameters can be supplied a value at compilation,
 * evaluations can be done on expressions,
 * if-statements and case statements can be evaluated.
 */

%{

#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <cmath>

using namespace std;

#include "values.h"
#include "listing.h"
#include "symbols.h"

int yylex();
void yyerror(const char* message);

Symbols<int> symbols;

int result;
int i;
int the_exp;
double * params;

%}

%error-verbose

%union
{
	CharPtr iden;
	Operators oper;
	int value;
	double dvalue;
}

%token <iden> IDENTIFIER
%token <value> INT_LITERAL BOOL_LITERAL REAL_LITERAL

%token <oper> ADDOP MULOP RELOP REMOP EXPOP
%token ANDOP OROP NOTOP

%token ARROW BEGIN_ BOOLEAN CASE ELSE END ENDCASE ENDIF ENDREDUCE 
%token FUNCTION IF INTEGER IS OTHERS REAL REDUCE RETURNS THEN WHEN

%type <value> body statement reductions relation term
%type <value> expression factor primary and_expression not_expression 
%type <value> expo_expression or_expression
%type <dvalue> case cases

%type <oper> operator

%%

function:	
	function_header optional_variable body {result = $3;} ;
	
function_header:	
	FUNCTION IDENTIFIER optional_parameters RETURNS type ';' {i = 0;}|
	error ';' ;

optional_parameters:
	parameters |
	;

parameters:
	parameters ',' parameter |
	parameter ;


parameter:
	IDENTIFIER ':' type {symbols.insert($1, params[i++]);}
	;

optional_variable:
	variable optional_variable |
	error ';' |
	;

variable:	
	IDENTIFIER ':' type IS statement {symbols.insert($1, $5);} ;

type:
	INTEGER |
	REAL 	|
	BOOLEAN ;

body:
	BEGIN_ statement END ';' {$$ = $2;} ;
    
	
statement:
	expression ';' |
	REDUCE operator reductions ENDREDUCE ';' {$$ = $3;} |
	IF expression THEN statement ELSE statement ENDIF ';' {$$ = $2 == 1 ? $4 : $6} |
	CASE expression IS cases OTHERS ARROW statement ENDCASE ';' 
	{the_exp = $2; $$ = isnan($4) ? $7 : $4} |
	error ';' {$$ = 0;}
	;

cases:
	cases case {$$ = isnan($1) ? $2 : $1} |
	{$$ = nan("");}
	;

case:
	WHEN INT_LITERAL ARROW statement {$$ = (the_exp == $2) ? $4 : nan("");} |
	error ';' {$$ = 0}
	;

operator:
	ADDOP |
	MULOP ;

reductions:
	reductions statement {$$ = evaluateReduction($<oper>0, $1, $2);} |
	{$$ = $<oper>0 == ADD ? 0 : 1;} ;

expression:
	or_expression;

or_expression:
	or_expression OROP and_expression {$$ = $1 || $3;} |
	and_expression ;

and_expression:
	and_expression ANDOP relation {$$ = $1 && $3;} |
	relation ;

relation:
	relation RELOP term {$$ = evaluateRelational($1, $2, $3);} |
	term ;

term:
	term ADDOP factor {$$ = evaluateArithmetic($1, $2, $3);} |
	factor ;
      
factor:
	factor MULOP expo_expression {$$ = evaluateArithmetic($1, $2, $3);} |
	factor REMOP expo_expression {$$ = evaluateArithmetic($1, $2, $3);} |
	expo_expression ;

expo_expression:
	expo_expression EXPOP not_expression {$$ = evaluateArithmetic($1, $2, $3);} |
	not_expression ;

not_expression:
	NOTOP not_expression {$$ = evaluateUnary($2);} |
	primary ;

primary:
	'(' expression ')' {$$ = $2;} |
	INT_LITERAL 	|
	REAL_LITERAL 	|
	BOOL_LITERAL 	|
	IDENTIFIER {if (!symbols.find($1, $$)) appendError(UNDECLARED, $1);} 
	;

%%

void yyerror(const char* message)
{
	appendError(SYNTAX, message);
}

int main(int argc, char *argv[])    
{
	// create array based on argc
	params = new double[argc - 1];
	for (int i = 1; i < argc; i++)
		params[i - 1] = atof(argv[i]);

	firstLine();
	yyparse();
	if (lastLine() == 0)
		cout << "\nResult = " << result << endl;
	return 0;
} 
