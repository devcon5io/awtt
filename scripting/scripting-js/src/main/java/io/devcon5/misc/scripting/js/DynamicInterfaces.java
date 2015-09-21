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

/**
 * An example for interfaces that are implemented by an external javascript
 * Created by Gerald Mücke on 09.09.2015.
 */
public class DynamicInterfaces {
    public static void main(String... args) throws IOException, ScriptException {

        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        final Invocable invocable = (Invocable) engine;

        try(InputStreamReader reader = getScriptReader("/runnable.js")) {
            engine.put("name", "Bob");
            engine.eval(reader);
        }

        final Runnable runnable = invocable.getInterface(Runnable.class);
        runnable.run();
    }

    private static InputStreamReader getScriptReader(String scriptName) {

        return new InputStreamReader(
                HelloWorld.class.getResourceAsStream(scriptName));
    }
}
