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

package io.devcon5.misc.scripting.js;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An example showing how to implement a dynamic filter using javascript implemented interfaces for FunctionInterfaces.
 * Created by Gerald Mücke on 09.09.2015.
 */
public class DynamicFilter {
    public static void main(String... args) throws IOException, ScriptException {

        final List<String> list = Arrays.asList("Alice", "Bob", "Eve", "Adam");

        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        final Invocable invocable = (Invocable) engine;
        try(InputStreamReader reader = getScriptReader("/filter.js")) {
            engine.eval(reader);
        }
        final Predicate<String> filter = invocable.getInterface(Predicate.class);
        final List<String> filteredList = list.stream().filter(filter).collect(Collectors.toList());
        System.out.println(filteredList);
    }

    private static InputStreamReader getScriptReader(String scriptName) {

        return new InputStreamReader(
                HelloWorld.class.getResourceAsStream(scriptName));
    }
}
