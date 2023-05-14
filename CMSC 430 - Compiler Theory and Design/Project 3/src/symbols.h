/* Project 3
 *
 * Author: Luis Moreno
 * Date: March 6, 2023
 * Class: CMSC 430 6380 Compilier Theory and Design (2232)
 * 
 * No changes were made to this file.
 * 
 * While trying to implement the use of double values in parser.y,
 * there was an error coming from the find() function of this file.
 * A possible fix was to allow find() to be passed double values,
 * but I could not figure out how to do so without causing more errors.
 */

template <typename T>
class Symbols
{
public:
	void insert(char* lexeme, T entry);
	bool find(char* lexeme, T& entry);
private:
	map<string, T> symbols;
};

template <typename T>
void Symbols<T>::insert(char* lexeme, T entry)
{
	string name(lexeme);
	symbols[name] = entry;
}

template <typename T>
bool Symbols<T>::find(char* lexeme, T& entry)
{
	string name(lexeme);
	typedef typename map<string, T>::iterator Iterator;
	Iterator iterator = symbols.find(name);
	bool found = iterator != symbols.end();
	if (found)
		entry = iterator->second;
	return found;
}