/* Project 4
 *
 * Author: Luis Moreno
 * Date: March 7, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * Added functions to type check newly added grammars and productions.
 */

typedef char* CharPtr;

enum Types {MISMATCH, INT_TYPE, BOOL_TYPE, REAL_TYPE};

void checkAssignment(Types lValue, Types rValue, string message);
Types checkArithmetic(Types left, Types right);
Types checkLogical(Types left, Types right);
Types checkRelational(Types left, Types right);
Types checkRemainder(Types left, Types right);
Types checkStatements(Types left, Types right);
Types checkNot(Types right);
Types checkIfStatement(Types expr, Types left, Types right);
Types checkCaseStatement(Types expr, Types left, Types right);
void checkFunction(Types left, Types right);