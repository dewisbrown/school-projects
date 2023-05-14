/* Project 4
 *
 * Author: Luis Moreno
 * Date: March 7, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 *
 * This file was updated to include semantic actions that perform
 * type checking as well as identifier checking (duplicate, undeclared).
 */

%{

#include <string>
#include <vector>
#include <map>

using namespace std;

#include "types.h"
#include "listing.h"
#include "symbols.h"

int yylex();
void yyerror(const char* message);

Symbols<Types> symbols;

%}

%error-verbose

%union
{
	CharPtr iden;
	Types type;
}

%token <iden> IDENTIFIER
%token <type> INT_LITERAL REAL_LITERAL BOOL_LITERAL

%token ADDOP MULOP RELOP ANDOP OROP NOTOP EXPOP REMOP
%token ARROW BEGIN_ BOOLEAN CASE ELSE END ENDCASE ENDIF ENDREDUCE
%token FUNCTION IF INTEGER IS OTHERS REAL REDUCE RETURNS THEN WHEN

%type <type> body function_header type statement statement_ reductions expression 
	relation term factor primary case cases and_expression expo_expression not_expression

%%

function:	
	function_header optional_variable body {checkFunction($1, $3)};
	
function_header:	
	FUNCTION IDENTIFIER optional_parameters RETURNS type ';'{$$ = $5};

optional_parameters:
	parameters |
	;

parameters:
	parameters ',' parameter |
	parameter ;

parameter:
	IDENTIFIER ':' type 
	{if (symbols.find($1, $3)) 
		appendError(DUPLICATE_IDENTIFIER, $1);
	else
		symbols.insert($1, $3);} ;

optional_variable:
	optional_variable variable |
	;

variable:	
	IDENTIFIER ':' type IS statement_ 
		{checkAssignment($3, $5, "Variable Initialization");
		if (symbols.find($1, $3)) 
			appendError(DUPLICATE_IDENTIFIER, $1);
		else
			symbols.insert($1, $3);} ;

type:
	INTEGER {$$ = INT_TYPE;}  |
	BOOLEAN {$$ = BOOL_TYPE;} |
	REAL 	{$$ = REAL_TYPE;} ;

body:
	BEGIN_ statement_ END ';' {$$ = $2};
    
statement_:
	statement ';' {$$ = $1} |
	error ';' {$$ = MISMATCH;} ;
	
statement:
	expression {$$ = $1} |
	REDUCE operator reductions ENDREDUCE {$$ = $3;}		|
	IF expression THEN statement_ ELSE statement_ ENDIF 
	{$$ = checkIfStatement($2, $4, $6);} 					|
	CASE expression IS cases OTHERS ARROW statement_ ENDCASE 
	{$$ = checkCaseStatement($2, $4, $7);}						;

operator:
	ADDOP |
	MULOP ;

reductions:
	reductions statement_ {$$ = checkArithmetic($1, $2);} |
	{$$ = INT_TYPE;} ;
    
cases:
	cases case {$$ = checkStatements($1, $2);} |
	{$$ = REAL_TYPE;}
	;

case:
	WHEN INT_LITERAL ARROW statement_  {$$ = $4};

expression:
	expression OROP and_expression {$$ = checkLogical($1, $3);} |
	and_expression ;

and_expression:
	and_expression ANDOP relation {$$ = checkLogical($1, $3);} |
	relation;

relation:
	relation RELOP term {$$ = checkRelational($1, $3);} |
	term ;

term:
	term ADDOP factor {$$ = checkArithmetic($1, $3);} |
	factor ;
      
factor:
	factor MULOP expo_expression  {$$ = checkArithmetic($1, $3);} |
	factor REMOP expo_expression  {$$ = checkRemainder($1, $3);}  |
	expo_expression ;

expo_expression:
	expo_expression EXPOP not_expression {$$ = checkArithmetic($1, $3);} |
	not_expression ;

not_expression:
	NOTOP not_expression {$$ = checkNot($2);} |
	primary ;

primary:
	'(' expression ')' {$$ = $2;} |
	INT_LITERAL  |
	BOOL_LITERAL |
	REAL_LITERAL |
	IDENTIFIER {if (!symbols.find($1, $$)) appendError(UNDECLARED, $1);} ;
    
%%

void yyerror(const char* message)
{
	appendError(SYNTAX, message);
}

int main(int argc, char *argv[])    
{
	firstLine();
	yyparse();
	lastLine();
	return 0;
} 
