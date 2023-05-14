/* Project 4
 *
 * Author: Luis Moreno
 * Date: March 7, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * Functions were added to type check if and case statements, 
 * and type check the function return.
 * 
 * checkAssignment() was updated to allow type coercion and
 * recognize type narrowing (real -> int).
 */

#include <string>
#include <vector>
#include <iostream>

using namespace std;

#include "types.h"
#include "listing.h"

void checkAssignment(Types lValue, Types rValue, string message)
{
	if (lValue == INT_TYPE && rValue == REAL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Narrowing " + message + " Not Allowed");
		return;
	}
		
	if (lValue != MISMATCH && rValue != MISMATCH && lValue != rValue)
	{
		if (lValue != REAL_TYPE && rValue != INT_TYPE)
			appendError(GENERAL_SEMANTIC, "Type Mismatch on " + message);
	}
}

Types checkArithmetic(Types left, Types right)
{
	if (left == MISMATCH || right == MISMATCH)
		return MISMATCH;
	if (left == BOOL_TYPE || right == BOOL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Numeric Type Required");
		return MISMATCH;
	}
	if (left == REAL_TYPE || right == REAL_TYPE)
		return REAL_TYPE;
	return INT_TYPE;
}

Types checkLogical(Types left, Types right)
{
	if (left == MISMATCH || right == MISMATCH)
		return MISMATCH;
	if (left != BOOL_TYPE || right != BOOL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Boolean Type Required");
		return MISMATCH;
	}	
		return BOOL_TYPE;
	return MISMATCH;
}

Types checkRelational(Types left, Types right)
{
	if (checkArithmetic(left, right) == MISMATCH)
		return MISMATCH;
	return BOOL_TYPE;
}

Types checkRemainder(Types left, Types right)
{
	if (left == INT_TYPE && right == INT_TYPE)
		return INT_TYPE;
	else
		appendError(GENERAL_SEMANTIC, "Int Types Required for REMOP");
	return MISMATCH;
}

Types checkStatements(Types left, Types right)
{
	if (left == MISMATCH || right == MISMATCH)
		return MISMATCH;
	if (left == right) 		// if the types match, return one of them
		return left;
	else
		appendError(GENERAL_SEMANTIC, "Statement Type Mismatch");
	return MISMATCH;
}

Types checkNot(Types right)
{
	if (right == BOOL_TYPE)
		return BOOL_TYPE;
	else
		appendError(GENERAL_SEMANTIC, "Negation Requires Boolean Type");
	return MISMATCH;
}

Types checkIfStatement(Types expr, Types left, Types right)
{
	if (expr != BOOL_TYPE)
		appendError(GENERAL_SEMANTIC, "Boolean Type Required for If Expression");
	return checkStatements(left, right);
}

Types checkCaseStatement(Types expr, Types left, Types right)
{
	if (expr != INT_TYPE)
		appendError(GENERAL_SEMANTIC, "Int Type Required for Case Expression");
	return checkStatements(left, right);
}

void checkFunction(Types left, Types right)
{
	if (left == INT_TYPE && right == REAL_TYPE)
	{
		appendError(GENERAL_SEMANTIC, "Narrowing Function Return Not Allowed");
		return;
	}
	if (left == REAL_TYPE && right == INT_TYPE)
		return;

	if (left != MISMATCH && right != MISMATCH && left != right)
	{
		appendError(GENERAL_SEMANTIC, "Type Mismatch on Function and Body");
	}
	
}