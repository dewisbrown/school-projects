/* Project 3
 *
 * Author: Luis Moreno
 * Date: March 6, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * This file was altered to allow counting different errors
 * and used a queue to allow multiple errors within a single line to
 * be printed to the console.
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
	displayErrors();
	printf("     \n\n");
	printf("Lexical Errors %d\n", lexicalErrors);
	printf("Syntax Errors %d\n", syntaxErrors);
	printf("Semantic Errors %d\n", generalSemanticErrors);
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

	// increments error count based on errorCategory (enum)
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
