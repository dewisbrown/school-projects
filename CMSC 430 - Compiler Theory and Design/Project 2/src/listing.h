/* Project 2
 *
 * Author: Luis Moreno
 * Date: February 27, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 */

enum ErrorCategories {LEXICAL, SYNTAX, GENERAL_SEMANTIC, DUPLICATE_IDENTIFIER,
	UNDECLARED};

void firstLine();
void nextLine();
int lastLine();
void appendError(ErrorCategories errorCategory, string message);

