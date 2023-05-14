#include <iostream>
#include <sstream>
using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "operand.h"
#include "plus.h"
#include "minus.h"
#include "times.h"
#include "divide.h"
#include "and.h"
#include "or.h"
#include "less.h"
#include "greater.h"
#include "equal.h"
#include "conditional.h"
#include "negate.h"

SubExpression::SubExpression(Expression* left, Expression* right)
{
    this->left = left;
    this->right = right;
}

SubExpression::SubExpression(Expression* left)
{
    this->left = left;
}

SubExpression::SubExpression(Expression* left, Expression* right, Expression* condition)
{
    this->left = left;
    this->right = right;
    this->condition = condition;
}

Expression* SubExpression::parse(stringstream& in)
{
    Expression* left;
    Expression* right;
    Expression* condition;          // added to include condition operand
    char operation, paren, qmark;
    
    left = Operand::parse(in);
    in >> operation;

    // checks for new operators
    if (operation == '!')
    {
        in >> paren;
        return new Negate(left);
    }
    if (operation == ':')
    {
        right = Operand::parse(in);
        in >> qmark; // '?'
        condition = Operand::parse(in);
        in >> paren;
        return new Conditional(left, right, condition);
    }
     
    // runs the same as before if new operators are not detected
    right = Operand::parse(in);
    in >> paren;
    

    // includes new operator cases
    switch (operation)
    {
        case '+':
            return new Plus(left, right);
        case '-':
            return new Minus(left, right);
        case '*':
            return new Times(left, right);
        case '/':
            return new Divide(left, right);
        case '>':
            return new Greater(left, right);
        case '<':
            return new Less(left, right);
        case '=':
            return new Equal(left, right);
        case '&':
            return new And(left, right);
        case '|':
            return new Or(left, right);
    }
    return 0;
}
        