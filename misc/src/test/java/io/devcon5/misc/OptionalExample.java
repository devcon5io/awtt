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

package io.devcon5.misc;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

/**
 * Good examples can be found at <a href="http://stackoverflow
 * .com/questions/30864583/java-8-difference-between-optional-flatmap-and-optional-map">Java 8 Difference betwen
 * Optional flatmap and Optional map</a>
 * Created by Gerald Mücke on 01.10.2015.
 */
public class OptionalExample {

    @Test
    public void testOptional_toString_nullValue() throws Exception {
        //prepare
        String maybeNull = null;
        Optional<String> optionalString = Optional.ofNullable(maybeNull);

        //act
        String result = optionalString.flatMap(Optional::of).orElse("null");

        //assert
        assertEquals("null", result);

    }
    @Test
    public void testOptional_toString_nonNullValue() throws Exception {
        //prepare
        String maybeNull = "notNull";
        Optional<String> optionalString = Optional.ofNullable(maybeNull);

        //act
        String result = optionalString.flatMap(s -> Optional.of("isNotNull"+s)).orElse("null");

        //assert
        assertEquals("isNotNull", result);
    }

    @Test
    public void testOptional_mapLength_nonNullValue() throws Exception {
        //prepare
        String maybeNull = "notNull";
        Optional<String> optionalString = Optional.ofNullable(maybeNull);

        //act
        Integer length = optionalString.map(String::length).orElse(-1);

        //assert
        assertEquals(Integer.valueOf(7), length);

    }


    @Test
    public void testOptional_mapLength_nullValue() throws Exception {
        //prepare
        String maybeNull = null;
        Optional<String> optionalString = Optional.ofNullable(maybeNull);

        //act
        Integer length = optionalString.map(String::length).orElse(-1);

        //assert
        assertEquals(Integer.valueOf(-1), length);

    }
}
