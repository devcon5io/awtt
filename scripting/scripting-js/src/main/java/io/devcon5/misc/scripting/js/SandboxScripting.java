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
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

/**
 * Example for how to limit the classes accessible in the scripting engine
 * Created by Gerald Mücke on 24.09.2015.
 */
public class SandboxScripting {
    public static void main(String... args) throws IOException, ScriptException {

        final NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        final ScriptEngine engine = factory.getScriptEngine(s -> {
            if(!s.startsWith("java.util.ArrayList")){
                return true;
            }
            return false;
        });

        //this will work, as it will create the ArrayList using a factory
        try(InputStreamReader reader = getScriptReader("/java.js")) {
            engine.eval(reader);
        }
        //this will not work, as it will create the ArrayList directly
        try(InputStreamReader reader = getScriptReader("/java_fail.js")) {
            engine.eval(reader);
        }
    }

    private static InputStreamReader getScriptReader(String scriptName) {

        return new InputStreamReader(
                HelloWorld.class.getResourceAsStream(scriptName));
    }
}
