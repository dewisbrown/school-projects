/* Project 2
 *
 * Author: Luis Moreno
 * Date: February 27, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * Flex input file updated to include required 
 * symbols and reserved words for the assigned grammar
 */

%{
#include <string>
#include <vector>

using namespace std;

#include "listing.h"
#include "tokens.h"

%}

%option noyywrap

ws		   [ \t\r]+
comment		\-\-.*\n
line		[\n]
id		   [A-Za-z][A-Za-z0-9]*
digit		[0-9]
int		{digit}+
punc		[\(\),:;]
real		[0-9]+\.[0-9]*([eE][+-]?[0-9]+)?
%%

{ws}		   { ECHO; }
{comment}	{ ECHO; nextLine();}
{line}		{ ECHO; nextLine();}
{real}      { ECHO; return(REAL_LITERAL); }
"<"			{ ECHO; return(RELOP); }
">"			{ ECHO; return(RELOP); }
"="			{ ECHO; return(RELOP); }
"/="		   { ECHO; return(RELOP); }
">="		   { ECHO; return(RELOP); }
"<="		   { ECHO; return(RELOP); }
"+"			{ ECHO; return(ADDOP); }
"-"			{ ECHO; return(ADDOP); }
"**"		   { ECHO; return(EXPOP); }
"*"			{ ECHO; return(MULOP); }
"/"			{ ECHO; return(MULOP); }
"%"         { ECHO; return(REMOP); }
"=>"		   { ECHO; return(ARROW); }
and		   { ECHO; return(ANDOP); }
or          { ECHO; return(OROP); }
not         { ECHO; return(NOTOP); }
begin		   { ECHO; return(BEGIN_); }
boolean		{ ECHO; return(BOOLEAN); }
case        { ECHO; return(CASE); }
else        { ECHO; return(ELSE); }
end		   { ECHO; return(END); }
endcase     { ECHO; return(ENDCASE); }
endif       { ECHO; return(ENDIF); }
endreduce	{ ECHO; return(ENDREDUCE); }
false       { ECHO; return(BOOL_LITERAL); }
function	   { ECHO; return(FUNCTION); }
if          { ECHO; return(IF); }
integer		{ ECHO; return(INTEGER); }
is		      { ECHO; return(IS); }
others      { ECHO; return(OTHERS); }
real        { ECHO; return(REAL); }
reduce		{ ECHO; return(REDUCE); }
rem         { ECHO; return(REMOP); }
returns		{ ECHO; return(RETURNS); }
then        { ECHO; return(THEN); }
true        { ECHO; return(BOOL_LITERAL); }
when        { ECHO; return(WHEN); }
{id}		   { ECHO; return(IDENTIFIER);}
{int}		   { ECHO; return(INT_LITERAL); }
{punc}		{ ECHO; return(yytext[0]); }
.		      { ECHO; appendError(LEXICAL, yytext); }
%%