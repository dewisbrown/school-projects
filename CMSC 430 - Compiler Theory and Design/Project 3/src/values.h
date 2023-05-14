/* Project 3
 *
 * Author: Luis Moreno
 * Date: March 6, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * New operators were added to enum Operators as well as
 * the function evaluateUnary(), which negates a value.
 */

typedef char* CharPtr;

enum Operators {LESS, ADD, MULTIPLY, GREATER, LESS_OR_EQUAL, GREATER_OR_EQUAL, 
                EQUAL, SUBTRACT, DIVIDE, REM, EXPO, UNEQUAL};

int evaluateReduction(Operators operator_, int head, int tail);
int evaluateRelational(int left, Operators operator_, int right);
int evaluateArithmetic(int left, Operators operator_, int right);
int evaluateUnary(int right);