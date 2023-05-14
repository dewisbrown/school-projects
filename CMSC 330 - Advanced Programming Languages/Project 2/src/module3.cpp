#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "symboltable.h"
#include "parse.h"

SymbolTable symbolTable;

/* 
 * for an input file to be used to parse expressions
 * a stringstream must be supplied to all previous
 * parsing methods
 * 
 * all instances of "cin" are replaced with the stringsteam in
 * 
 */ 

void parseAssignments(stringstream& in);

int main()
{
    Expression* expression;
    
    // constant representing characters per line
    const int LINE_SIZE = 256;

    char paren, comma;
    
    // array of characters for a line
    char line[LINE_SIZE];
    
    // used to print case numbers
    // int i = 1;
    
    // open file "input.txt" from project directory
    ifstream inputFile("input.txt");
    
    while (true)
    {
        symbolTable.clear(); // clear the symbol table

        inputFile.getline(line, LINE_SIZE);

        // exit loop if inputFile is null
        if (!inputFile)
            break; 

        // allows the stringstream to read file lines (char[])
        stringstream in(line, ios_base::in);

        in >> paren;
        // cout << "Case " << i << ":" << endl; // prints case numbers
        cout << line << " " << endl;
        expression = SubExpression::parse(in);
        in >> comma;
        parseAssignments(in);
        double result = expression->evaluate();
        cout << "Value = " << result << endl << endl;
        // i++;
    }
    return 0;
}

void parseAssignments(stringstream& in)
{
    char assignop, delimiter;
    string variable;
    double value;
    do
    {
        variable = parseName(in);
        in >> ws >> assignop >> value >> delimiter;
        symbolTable.insert(variable, value);
    }
    while (delimiter == ',');
}
   
