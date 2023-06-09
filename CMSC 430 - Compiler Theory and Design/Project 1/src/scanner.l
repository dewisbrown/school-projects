/* Project 1
 *
 * Author: Luis Moreno
 * Date: January 24, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232) 
 */

/* This file contains flex input file */

%{
#include <cstdio>
#include <string>

using namespace std;

#include "listing.h"
#include "tokens.h"

%}

%option noyywrap

ws			[ \t\r]+
comment		\-\-.*\n
commentb 	\/\/.*\n
line		[\n]
id			[A-Za-z]([_]?[A-Za-z0-9])*
digit		[0-9]
int			{digit}+
punc		[\(\),:;]
real		[0-9]+\.[0-9]*([eE][+-]?[0-9]+)?
%%

{ws}		{ ECHO; }
{comment}	{ ECHO; nextLine(); }
{commentb}	{ ECHO; nextLine(); }
{line}		{ ECHO; nextLine(); }
{real}		{ ECHO; return(REAL_LITERAL); }
"<"			{ ECHO; return(RELOP); }
"+"			{ ECHO; return(ADDOP); }
"-"			{ ECHO; return(ADDOP); }
"**"		{ ECHO; return(EXPOP); }
"*"			{ ECHO; return(MULOP); }
"/"			{ ECHO; return(MULOP); }
"="			{ ECHO; return(RELOP); }
"/="		{ ECHO; return(RELOP); }
">"			{ ECHO; return(RELOP); }
">="		{ ECHO; return(RELOP); }
"<="		{ ECHO; return(RELOP); }
"=>"		{ ECHO; return(ARROW); }
begin		{ ECHO; return(BEGIN_); }
boolean		{ ECHO; return(BOOLEAN); }
case		{ ECHO; return(CASE); }
else		{ ECHO; return(ELSE); }
end			{ ECHO; return(END); }
endcase		{ ECHO; return(ENDCASE); }
endif		{ ECHO; return(ENDIF); }
endreduce	{ ECHO; return(ENDREDUCE); }
false		{ ECHO; return(BOOL_LITERAL); }
function	{ ECHO; return(FUNCTION); }
integer		{ ECHO; return(INTEGER); }
if 			{ ECHO; return(IF); }
is			{ ECHO; return(IS); }
not			{ ECHO; return(NOTOP); }
or			{ ECHO; return(OROP); }
others		{ ECHO; return(OTHERS); }
real		{ ECHO; return(REAL); }
reduce		{ ECHO; return(REDUCE) ; }
rem			{ ECHO; return(REMOP);}
returns		{ ECHO; return(RETURNS); }
then		{ ECHO; return(THEN); }
true		{ ECHO; return(BOOL_LITERAL);}
when		{ ECHO; return(WHEN); }
and			{ ECHO; return(ANDOP); }
{id}		{ ECHO; return(IDENTIFIER);}
{int}		{ ECHO; return(INT_LITERAL); }
{punc}		{ ECHO; return(yytext[0]); }
.			{ ECHO; appendError(LEXICAL, yytext); }
%%

int main()
{
	firstLine();
	
	FILE *file = fopen("lexemes.txt", "wa"); 
	int token = yylex();
	while (token)
	{
		fprintf(file, "%d %s\n", token, yytext);
		token = yylex();
	}
	lastLine();
	fclose(file);
	return 0;
}