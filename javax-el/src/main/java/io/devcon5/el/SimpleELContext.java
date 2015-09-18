package io.devcon5.el;

import java.lang.reflect.Method;
import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

/**
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
class SimpleELContext extends ELContext {
    private final CompositeELResolver compositeELResolver;
    private final ELFunctionMapper functionMapper;
    private final VariableMapper variableMapper;
    private final ExpressionFactory expressionFactory;

    public SimpleELContext() {
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

    public void addMethod(String prefix, Method method) {
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
