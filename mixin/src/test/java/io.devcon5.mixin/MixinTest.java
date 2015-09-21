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

package io.devcon5.mixin;

import static io.devcon5.mixin.Mixin.addMixin;
import static org.junit.Assert.assertEquals;

import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Test;

/**
 * Created by Gerald Mücke on 11.09.2015.
 */

public class MixinTest {

    @Test
    public void testAddJavaMixin() throws Exception {
        //prepare
        String o = "123";

        //act
        MyMixin mx = (MyMixin) addMixin(MyMixin.class).to(o);
        System.out.println(mx);
        System.out.println(mx.hello());

        //assert
        assertEquals("Hello World", mx.hello());

    }

    @Test
    public void testAddJSMixin() throws Exception {
        //prepare
        String o = "world";

        //act
        ScriptedMixin mx = (ScriptedMixin) addMixin(ScriptedMixin.class).withScript(() -> createResourceReader(
                "/dynamicMixin.js")).to(o);
        System.out.println(mx);
        System.out.println(mx.helloWorld());
        System.out.println(mx.badHabbitsDieHard());

        //assert
        assertEquals("Hell of a world!", mx.helloWorld());
        assertEquals("I do nasty stuff", mx.badHabbitsDieHard());

    }

    private Reader createResourceReader(final String resourceName) {

        return new InputStreamReader(getClass().getResourceAsStream(resourceName));
    }

    public interface MyMixin {

        default String hello() {

            return "Hello World";
        }
    }

    public interface ScriptedMixin {

        default String helloWorld() {

            return "Hello world";
        }

        default String badHabbitsDieHard() {

            return "I do nasty stuff";
        }

    }

}
