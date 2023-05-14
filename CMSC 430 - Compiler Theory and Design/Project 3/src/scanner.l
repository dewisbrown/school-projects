/* Project 3
 *
 * Author: Luis Moreno
 * Date: March 6, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 *
 * This flex file was altered to allow the following:
 * recognition of real and boolean literals,
 * recognition of all operators and new tokens,
 * recognition of both comment types (// or --).
 */

%{
#include <string>

using namespace std;

#include "values.h"
#include "listing.h"
#include "tokens.h"

%}

%option noyywrap

ws		   	[ \t\r]+
comment		(\-\-|\/\/).*\n
line		[\n]
id		   	[A-Za-z][A-Za-z0-9]*
digit		[0-9]
int			{digit}+
punc		[\(\),:;]
real		[0-9]+\.[0-9]*([eE][+-]?[0-9]+)?
%%

{ws}		{ ECHO; }
{comment}	{ ECHO; nextLine();}
{line}		{ ECHO; nextLine();}
"<"		   	{ ECHO; yylval.oper = LESS; return(RELOP); }
">"			{ ECHO; yylval.oper = GREATER; return(RELOP); }
"<="		{ ECHO; yylval.oper = LESS_OR_EQUAL; return(RELOP); }
">="		{ ECHO; yylval.oper = GREATER_OR_EQUAL; return(RELOP); }
"="			{ ECHO; yylval.oper = EQUAL; return(RELOP); }
"/="		{ ECHO; yylval.oper = UNEQUAL; return(RELOP); }
"+"		   	{ ECHO; yylval.oper = ADD; return(ADDOP); }
"-"			{ ECHO; yylval.oper = SUBTRACT; return(ADDOP); }
"*"		   	{ ECHO; yylval.oper = MULTIPLY; return(MULOP); }
"/"			{ ECHO; yylval.oper = DIVIDE; return(MULOP); }
rem			{ ECHO; yylval.oper = REM; return(MULOP); }
"**"		{ ECHO; yylval.oper = EXPO; return(EXPOP); }
"=>"		{ ECHO; return(ARROW); }
and			{ ECHO; return(ANDOP); }
or 			{ ECHO; return(OROP); }
not 		{ ECHO; return(NOTOP); }
begin		{ ECHO; return(BEGIN_); }
boolean		{ ECHO; return(BOOLEAN); }
case		{ ECHO; return(CASE); }
else 		{ ECHO; return(ELSE); }
end		   	{ ECHO; return(END); }
endcase 	{ ECHO; return(ENDCASE); }
endif 		{ ECHO; return(ENDIF); }
endreduce	{ ECHO; return(ENDREDUCE); }
function	{ ECHO; return(FUNCTION); }
if 			{ ECHO; return(IF); }
integer		{ ECHO; return(INTEGER); }
is		    { ECHO; return(IS); }
others 		{ ECHO; return(OTHERS); }
real 		{ ECHO; return(REAL); }
reduce		{ ECHO; return(REDUCE); }
returns		{ ECHO; return(RETURNS); }
then 		{ ECHO; return(THEN); }
when 		{ ECHO; return(WHEN); }
true		{ ECHO; yylval.value = 1; return(BOOL_LITERAL); }
false 		{ ECHO; yylval.value = 0; return(BOOL_LITERAL); }
{id}		{ ECHO; yylval.iden = (CharPtr)malloc(yyleng + 1); 
			      strcpy(yylval.iden, yytext); return(IDENTIFIER);}
{int}		{ ECHO; yylval.value = atoi(yytext); return(INT_LITERAL); }
{real}		{ ECHO; yylval.value = atoi(yytext); return(REAL_LITERAL); }
{punc}		{ ECHO; return(yytext[0]); }
.		    { ECHO; appendError(LEXICAL, yytext); }
%%
