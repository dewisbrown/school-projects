class Negate : public SubExpression
{
public:
    Negate(Expression* left) : SubExpression(left)
    {
    }
    int evaluate()
    {
        // negates the evaluated expression
        return !(left->evaluate());
    }
};