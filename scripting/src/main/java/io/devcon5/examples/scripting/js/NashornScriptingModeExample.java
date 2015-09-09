package io.devcon5.examples.scripting.js;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

/**
 * This example shows how Nashorn engine is created with scripting mode enabled. The scripting mode
 * supports an expression syntax {@code ${expression}} inside strings that evalated.
 * Created by Gerald Mücke on 09.09.2015.
 */
public class NashornScriptingModeExample {

    public static void main(String... args) throws IOException, ScriptException {

        final NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        final ScriptEngine engine = factory.getScriptEngine(new String[] { "-scripting" });

        try(InputStreamReader reader = getScriptReader("/helloworld-scripting.js")) {
            engine.put("name", "Bob");
            engine.eval(reader);
        }
    }

    private static InputStreamReader getScriptReader(String scriptName) {

        return new InputStreamReader(
                NashornScriptingModeExample.class.getResourceAsStream(scriptName));
    }

}
