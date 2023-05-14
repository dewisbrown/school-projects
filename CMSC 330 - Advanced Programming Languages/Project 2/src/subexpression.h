class SubExpression: public Expression
{
public:
    SubExpression(Expression* left, Expression* right);
    
    // added to support negation
    SubExpression(Expression* left);

    // added to support conditions
    SubExpression(Expression* left, Expression* right, Expression* condition);
    
    static Expression* parse(stringstream& in);

protected: 
    Expression* left;
    Expression* right;

    // added to support conditions
    Expression* condition;
};    