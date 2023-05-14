/* Project 3
 *
 * Author: Luis Moreno
 * Date: March 6, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * This file was altered to include the newly added Operators
 * in evaluateReduction(), evaluateRelational(), evaluateArithmetic(), 
 * and the newly added function evaluateUnary().
 * 
 * evaluateUnary() returns the negation of a value.
 */

#include <string>
#include <vector>
#include <cmath>

using namespace std;

#include "values.h"
#include "listing.h"

int evaluateReduction(Operators operator_, int head, int tail)
{
	if (operator_ == ADD)
		return head + tail;
	if (operator_ == SUBTRACT)				// added subtraction operator to reduction
		return head - tail;
	return head * tail;
}


int evaluateRelational(int left, Operators operator_, int right)
{
	int result;
	switch (operator_)
	{
		case LESS:
			result = left < right;
			break;
		case GREATER:						// added greater
			result = left > right;
			break;
		case LESS_OR_EQUAL:					// added less-than-or-equal-to
			result = left <= right;
			break;
		case GREATER_OR_EQUAL:				// added greater-than-or-equal-to
			result = left >= right;
			break;
		case EQUAL:							// added equals
			result = left == right;
			break;
		case UNEQUAL:
			result = left /= right;			// added unequals
			break;
		default:
			result = 100;
			break;
	}
	return result;
}

int evaluateArithmetic(int left, Operators operator_, int right)
{
	int result;
	switch (operator_)
	{
		case ADD:
			result = left + right;
			break;
		case MULTIPLY:
			result = left * right;
			break;
		case SUBTRACT:						// added subtraction
			result = left - right;
			break;
		case DIVIDE:						// added division
			result = left / right;
			break;
		case REM:							// added modulus (remainder)
			result = left % right;
			break;
		case EXPO:							// added exponentiation
			result = pow(left, right);
			break;
		default:
			result = 100;
			break;
	}
	return result;
}

int evaluateUnary(int right)
{
	return !right;							// added unary evaluation
}