package io.devcon5.examples.el;

import java.util.HashMap;
import java.util.Map;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

/**
 * Simple Variable mapper, mapping variable names to ValueExpressions.
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
class ELVariableMapper extends VariableMapper {

    private final Map<String, ValueExpression> expressions = new HashMap<String, ValueExpression>();

    @Override
    public ValueExpression resolveVariable(final String variable) {
        return expressions.get(variable);
    }

    @Override
    public ValueExpression setVariable(final String variable, final ValueExpression expression) {
        return expressions.put(variable, expression);
    }
}
