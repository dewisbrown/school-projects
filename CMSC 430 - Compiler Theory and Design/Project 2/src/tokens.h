/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     INT_LITERAL = 258,
     REAL_LITERAL = 259,
     BOOL_LITERAL = 260,
     IDENTIFIER = 261,
     ADDOP = 262,
     MULOP = 263,
     REMOP = 264,
     EXPOP = 265,
     RELOP = 266,
     ANDOP = 267,
     OROP = 268,
     NOTOP = 269,
     ARROW = 270,
     BEGIN_ = 271,
     BOOLEAN = 272,
     CASE = 273,
     ELSE = 274,
     END = 275,
     ENDCASE = 276,
     ENDIF = 277,
     ENDREDUCE = 278,
     FUNCTION = 279,
     IF = 280,
     INTEGER = 281,
     IS = 282,
     OTHERS = 283,
     REAL = 284,
     REDUCE = 285,
     RETURNS = 286,
     THEN = 287,
     WHEN = 288
   };
#endif
/* Tokens.  */
#define INT_LITERAL 258
#define REAL_LITERAL 259
#define BOOL_LITERAL 260
#define IDENTIFIER 261
#define ADDOP 262
#define MULOP 263
#define REMOP 264
#define EXPOP 265
#define RELOP 266
#define ANDOP 267
#define OROP 268
#define NOTOP 269
#define ARROW 270
#define BEGIN_ 271
#define BOOLEAN 272
#define CASE 273
#define ELSE 274
#define END 275
#define ENDCASE 276
#define ENDIF 277
#define ENDREDUCE 278
#define FUNCTION 279
#define IF 280
#define INTEGER 281
#define IS 282
#define OTHERS 283
#define REAL 284
#define REDUCE 285
#define RETURNS 286
#define THEN 287
#define WHEN 288




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

