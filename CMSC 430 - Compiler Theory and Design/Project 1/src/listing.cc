/* Project 1
 *
 * Author: Luis Moreno
 * Date: January 24, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * This program was modified to include more tokens and lexemes,
 * which can be found in scanner.l.
 * 
 * listing.cc was modified to use a queue for error messages. Before, 
 * only one error for a given line was printed with the displayErrors() 
 * function. Now, each error detected in a line is added to a queue. Then, 
 * displayErrors() prints and clears each item from the queue.
 * 
 * The lastLine() function was modified to display the count for each 
 * type of error. For now, the program can only detect lexical errors.
 * A switch statement in the appendError() function increments the count of 
 * whichever error is detected.
 */

#include <cstdio>
#include <queue>
#include <string>

using namespace std;

#include "listing.h"

static int lineNumber;
static string error = "";
static int totalErrors = 0;

// added error counters for each type of error
static int lexicalErrors = 0;
static int syntaxErrors = 0;
static int generalSemanticErrors = 0;
static int duplicateErrors = 0;
static int undeclaredErrors = 0;

// using a queue to display multiple error messages for a line
static queue<string> errorQueue;

static void displayErrors();

void firstLine()
{
	lineNumber = 1;
	printf("\n%4d  ",lineNumber);
}

void nextLine()
{
	displayErrors();
	lineNumber++;
	printf("%4d  ",lineNumber);
}

int lastLine()
{
	printf("\r");
	if (totalErrors == 0)
		printf("\nCompilied Successfully.\n");
	else
	{
		displayErrors();
		printf("\nLexical Errors: %d\n", lexicalErrors);
		printf("Syntax Errors: %d\n", syntaxErrors);
		printf("General Semantic Errors: %d\n", generalSemanticErrors);
		printf("Duplicate Identifier Errors: %d\n", duplicateErrors);
		printf("Undeclared Errors: %d\n", undeclaredErrors);
		printf("\nTotal Errors: %d\n\n", totalErrors);
	}
	return totalErrors;
}
    
void appendError(ErrorCategories errorCategory, string message)
{
	string messages[] = { "Lexical Error, Invalid Character ", "",
		"Semantic Error, ", "Semantic Error, Duplicate Identifier: ",
		"Semantic Error, Undeclared " };

	error = messages[errorCategory] + message;
	
	// add error to queue for current line
	errorQueue.push(error);

	// increments error count based on (enum) errorCategory 
	switch(errorCategory)
	{
		case LEXICAL:
			lexicalErrors++;
			break;
		case SYNTAX:
			syntaxErrors++;
			break;
		case GENERAL_SEMANTIC:
			generalSemanticErrors++;
			break;
		case DUPLICATE_IDENTIFIER:
			duplicateErrors++;
			break;
		default:
			undeclaredErrors++;
			break;
	}
	totalErrors++;
}

void displayErrors()
{
	while(!errorQueue.empty())
	{
		printf("[%s ]\n", errorQueue.front().c_str());
		errorQueue.pop();
	}
	error = "";
}