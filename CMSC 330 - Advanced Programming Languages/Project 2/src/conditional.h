class Conditional : public SubExpression
{
public:
    Conditional(Expression* left, Expression* right, Expression* condition) : SubExpression(left, right, condition)
    {
    }
    int evaluate()
    {
        if (condition->evaluate())  // if the condition is true
        {
            return left->evaluate();// evaluate the left expression
        }
        return right->evaluate();   // if not, evaluate the right expression
    }
};