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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This simple example shows, how the java-provided javascript engine is created and a script from an external
 * resource is loaded and evaluated.
 * Created by Gerald M�cke on 09.09.2015.
 */
public class HelloWorld {

    public static void main(String... args) throws IOException, ScriptException {

        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

        try(InputStreamReader reader = getScriptReader("/helloworld.js")) {
            engine.put("name", "Bob");
            engine.eval(reader);
        }
    }

    private static InputStreamReader getScriptReader(String scriptName) {

        return new InputStreamReader(
                HelloWorld.class.getResourceAsStream(scriptName));
    }

}
