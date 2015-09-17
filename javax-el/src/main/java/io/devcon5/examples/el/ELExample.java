package io.devcon5.examples.el;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

/**
 * Created by m4g on 17.09.2015.
 */
public class ELExample {

    public static void main(String... args) {
        String aValue = "#{1 + 2}";
        final String result = eval(aValue);
        System.out.println(result);
        final Integer iresult = eval(aValue, Integer.class);
        System.out.println(iresult);
    }

    private static <T> T eval(String expression, Class<? extends T> type) {
        final ELContext elContext = new ExampleELContext();
        final ExpressionFactory expressionFactory = ExpressionFactory.newInstance();
        final ValueExpression ve = expressionFactory.createValueExpression(elContext, expression, type);
        return (T) ve.getValue(elContext);
    }

    private static String eval(String expression) {
        return eval(expression, String.class);
    }
}
