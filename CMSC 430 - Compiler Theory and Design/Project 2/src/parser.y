/* Project 2
 *
 * Author: Luis Moreno
 * Date: February 27, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 *
 * Bison file updated to include required tokens
 * and new grammar rules/productions
 */

%{

#include <string>

using namespace std;

#include "listing.h"

int yylex();
void yyerror(const char* message);

%}

%error-verbose

%token INT_LITERAL REAL_LITERAL BOOL_LITERAL IDENTIFIER

%token ADDOP MULOP REMOP EXPOP
%token RELOP
%token ANDOP OROP NOTOP

%token ARROW BEGIN_ BOOLEAN CASE ELSE END ENDCASE ENDIF ENDREDUCE 
%token FUNCTION IF INTEGER IS OTHERS REAL REDUCE RETURNS THEN WHEN

%%

function:	
	function_header variable_list body;
	
function_header:	
	FUNCTION IDENTIFIER optional_parameters RETURNS type ';' |
	error ';' ;

optional_parameters:
	parameters |
	;

variable:
	IDENTIFIER ':' type IS statement |
	error ';' ;

variable_list:
	variable variable_list |
	;

parameters:
	parameter |
	parameters ',' parameter
	;

parameter:
	IDENTIFIER ':' type ;

type:
	INTEGER |
	REAL	|
	BOOLEAN ;

body:
	BEGIN_ statement END ';'
	;
	
statement:
	expression ';' |
	REDUCE operator statement_list ENDREDUCE ';' |
	IF expression THEN statement ELSE statement ENDIF ';' |
	CASE expression IS case_list OTHERS ARROW statement ENDCASE ';' |
	error ';' ;
	
	/* error production in statement rule was not included in the project
	 * requirements where it lists where to add error productions to allow
	 * detection/recovery from errors. It was failing to detect errors after 
	 * a statement error was discovered (E.g. would not detect missing 'END'
	 * in sample text from project requirements document) */

statement_list:
	statement statement_list |
	;

operator:
	ADDOP |
	MULOP ;

case:
	WHEN INT_LITERAL ARROW statement |
	error ';' ;
		    
case_list:
	case case_list |
	;
	
expression:
	or_expression ;

or_expression:
	and_expression |
	or_expression OROP and_expression
	;

and_expression:
	rel_expression |
	and_expression ANDOP rel_expression
	;

rel_expression:
	add_expression |
	rel_expression RELOP add_expression
	;

add_expression:
	mul_expression |
	add_expression ADDOP mul_expression
	;

mul_expression:
	expo_expression |
	mul_expression MULOP expo_expression |
	mul_expression REMOP expo_expression
	;

expo_expression:
	not_expression |
	expo_expression EXPOP not_expression
	;

not_expression:
	base_expression |
	NOTOP not_expression
	;

base_expression:
	INT_LITERAL |
	REAL_LITERAL |
	BOOL_LITERAL |
	IDENTIFIER |
	'(' expression ')'
	;
	
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