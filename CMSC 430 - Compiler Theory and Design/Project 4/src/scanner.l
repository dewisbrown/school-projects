/* Project 4
 *
 * Author: Luis Moreno
 * Date: March 7, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 *
 * This file was updated to include all necessary tokens.
 * Real and Boolean literals are recognized and assigned a value.
 */

%{
#include <string>

using namespace std;

#include "types.h"
#include "listing.h"
#include "tokens.h"

%}

%option noyywrap

ws		    [ \t\r]+
comment		(\-\-|\/\/).*\n
line		[\n]
id		    [A-Za-z][A-Za-z0-9]*
digit		[0-9]
int		    {digit}+
real		[0-9]+\.[0-9]*([eE][+-]?[0-9]+)?
punc		[\(\),:;]
%%

{ws}		{ ECHO; }
{comment}	{ ECHO; nextLine();}
{line}		{ ECHO; nextLine();}
"+"		    { ECHO; return(ADDOP); }
"-"         { ECHO; return(ADDOP); }
"*"		    { ECHO; return(MULOP); }
"/"         { ECHO; return(MULOP); }
"**"        { ECHO; return(EXPOP); }
"<"		    { ECHO; return(RELOP); }
">"         { ECHO; return(RELOP); }
">="        { ECHO; return(RELOP); }
"<="        { ECHO; return(RELOP); }
"/="        { ECHO; return(RELOP); }
"="         { ECHO; return(RELOP); }
rem         { ECHO; return(REMOP); }
and         { ECHO; return(ANDOP); }
or          { ECHO; return(OROP); }
not         { ECHO; return(NOTOP); }
"=>"        { ECHO; return(ARROW); }
begin		{ ECHO; return(BEGIN_); }
boolean		{ ECHO; return(BOOLEAN); }
case        { ECHO; return(CASE); }
else        { ECHO; return(ELSE); }
end		    { ECHO; return(END); }
endif       { ECHO; return(ENDIF); }
endcase     { ECHO; return(ENDCASE); }
endreduce	{ ECHO; return(ENDREDUCE); }
function	{ ECHO; return(FUNCTION); }
if          { ECHO; return(IF); }
integer		{ ECHO; return(INTEGER); }
is		    { ECHO; return(IS); }
others      { ECHO; return(OTHERS); }
real        { ECHO; return(REAL); }
reduce		{ ECHO; return(REDUCE); }
returns		{ ECHO; return(RETURNS); }
then        { ECHO; return(THEN); }
when        { ECHO; return(WHEN); }
true        { ECHO; yylval.type = BOOL_TYPE; return(BOOL_LITERAL); }
false       { ECHO; yylval.type = BOOL_TYPE; return(BOOL_LITERAL); }
{id}		{ ECHO; yylval.iden = (CharPtr)malloc(yyleng + 1); strcpy(yylval.iden, yytext); return(IDENTIFIER);}
{int}		{ ECHO; yylval.type = INT_TYPE; return(INT_LITERAL); }
{real}      { ECHO; yylval.type = REAL_TYPE; return(REAL_LITERAL); }
{punc}		{ ECHO; return(yytext[0]); }
.		    { ECHO; appendError(LEXICAL, yytext); }

%%
