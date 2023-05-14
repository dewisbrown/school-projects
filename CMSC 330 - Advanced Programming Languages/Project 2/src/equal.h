class Equal : public SubExpression
{
public:
    Equal(Expression* left, Expression* right) : SubExpression(left, right)
    {
    }
    int evaluate()
    {
        // similar to skeleton "plus.h", using '==' instead of '+'
        return left->evaluate() == right->evaluate();
    }
};