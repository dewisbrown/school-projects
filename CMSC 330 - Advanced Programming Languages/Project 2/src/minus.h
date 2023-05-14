class Minus : public SubExpression
{
public:
    Minus(Expression* left, Expression* right) : SubExpression(left, right)
    {
    }
    int evaluate()
    {
        // similar to skeleton "plus.h", using '-' instead of '+'
        return left->evaluate() - right->evaluate();
    }
};