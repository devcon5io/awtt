package io.devcon5.examples.el;

import javax.el.*;
import java.lang.reflect.Method;

/**
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
class ExampleELContext extends ELContext {
    private final CompositeELResolver compositeELResolver;
    private final ELFunctionMapper functionMapper;
    private final VariableMapper variableMapper;
    private final ExpressionFactory expressionFactory;

    public ExampleELContext() {
        this.compositeELResolver = new CompositeELResolver();
        this.compositeELResolver.add(new ArrayELResolver());
        this.compositeELResolver.add(new ListELResolver());
        this.compositeELResolver.add(new BeanELResolver());
        this.compositeELResolver.add(new MapELResolver());
        this.functionMapper = new ELFunctionMapper();
        this.variableMapper = new ELVariableMapper();
        this.expressionFactory = ExpressionFactory.newInstance();
    }

    public void addVariable(String name, Object value) {
        getVariableMapper().setVariable(name, expressionFactory.createValueExpression(value, value.getClass()));
    }

    public void addMethod(String prefix, Method method){
        getFunctionMapper().mapFunction(prefix, method.getName(), method);
    }

    public <T> T eval(String expression, Class<? extends T> type) {
        final ValueExpression ve = expressionFactory.createValueExpression(this, expression, type);
        return (T) ve.getValue(this);
    }

    public String eval(String expression) {
        return eval(expression, String.class);
    }

    @Override
    public ELResolver getELResolver() {
        return compositeELResolver;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return variableMapper;
    }
}
