package io.devcon5.examples.el;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.VariableMapper;

/**
 * Created by m4g on 17.09.2015.
 */
class ExampleELContext extends ELContext {
    private final CompositeELResolver compositeELResolver;
    private final ELFunctionMapper functionMapper;
    private final VariableMapper variableMapper;

    public ExampleELContext() {
        this.compositeELResolver = new CompositeELResolver();
        this.compositeELResolver.add(new ArrayELResolver());
        this.compositeELResolver.add(new ListELResolver());
        this.compositeELResolver.add(new BeanELResolver());
        this.compositeELResolver.add(new MapELResolver());
        this.functionMapper = new ELFunctionMapper();
        this.variableMapper = new ELVariableMapper();
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
