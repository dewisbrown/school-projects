#include <cctype>
#include <sstream>
#include <iostream>
#include <string>
using namespace std;

#include "parse.h"

string parseName(stringstream& in)
{
    char alnum;
    string name = "";

    // replaced cin with stringstream& in
    in >> ws;
    while (isalnum(in.peek()))
    {
        in >> alnum;
        name += alnum;
    }
    return name;
}