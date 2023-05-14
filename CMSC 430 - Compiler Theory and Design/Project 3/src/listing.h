/* Project 3
 *
 * Author: Luis Moreno
 * Date: March 6, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * No changes were made to this file.
 */

enum ErrorCategories {LEXICAL, SYNTAX, GENERAL_SEMANTIC, DUPLICATE_IDENTIFIER,
	UNDECLARED};

void firstLine();
void nextLine();
int lastLine();
void appendError(ErrorCategories errorCategory, string message);

