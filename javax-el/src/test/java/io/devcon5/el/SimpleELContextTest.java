/*
 * Copyright 2015 DevCon5 GmbH, info@devcon5.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.devcon5.el;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gerald Mücke on 18.09.2015.
 */
public class SimpleELContextTest {

    private SimpleELContext context;

    @Before
    public void setUp() throws Exception {
        this.context = new SimpleELContext();
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
        context.addMethod("t", SimpleELContextTest.class.getMethod("aMethod"));

        //act
        final String result = context.eval(aValue);

        //assert
        assertEquals("METHOD TEST", result);

    }

    public static String aMethod() {
        return "METHOD TEST";
    }
}
