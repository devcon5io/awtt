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

package io.devcon5.test;

import static io.devcon5.test.BDD.given;

import org.junit.Test;

/**
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
public class BDDTest {

    @Test
    public void bdd_example_success() throws Exception {
        given(() -> "a String").when(s -> s + " and another String").then(s -> s.equals("a String and another String"));

    }

    @Test
    public void bdd_exampleConversion_success() throws Exception {
        given(() -> "123").when(Integer::valueOf).then(i -> i.equals(Integer.valueOf(123)));

    }

    @Test
    public void bdd_example_fail() throws Exception {
        given(() -> "a String").when(s -> s + " and another String").then(s -> s.equals("something different"));

    }
}
