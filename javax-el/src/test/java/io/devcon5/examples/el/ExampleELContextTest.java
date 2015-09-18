package io.devcon5.examples.el;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gerald Mücke on 18.09.2015.
 */
public class ExampleELContextTest {

    private ExampleELContext context;

    @Before
    public void setUp() throws Exception {
        this.context = new ExampleELContext();
    }

    @Test
    public void test_simple_expression() throws Exception {
        //prepare
        String aValue = "#{1 + 2}";

        //act
        final String result = context.eval(aValue);

        //assert
        assertEquals("3", result);
    }

    @Test
    public void test_resolved_variable() throws Exception {
        //prepare
        String aValue = "#{test}";
        context.addVariable("test", "TEST");

        //act
        final String result = context.eval(aValue);

        //assert
        assertEquals("TEST", result);

    }

    @Test
    public void test_resolved_function() throws Exception {
        //prepare
        String aValue = "${t:aMethod()}";
        context.addMethod("t", ExampleELContextTest.class.getMethod("aMethod"));

        //act
        final String result = context.eval(aValue);

        //assert
        assertEquals("METHOD TEST", result);

    }

    public static String aMethod(){
        return "METHOD TEST";
    }
}
