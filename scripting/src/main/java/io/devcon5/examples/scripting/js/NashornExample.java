package io.devcon5.examples.scripting.js;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This simple example shows, how the java-provided javascript engine is created and a script from an external
 * resource is loaded and evaluated.
 * Created by Gerald Mücke on 09.09.2015.
 */
public class NashornExample {

    public static void main(String... args) throws IOException, ScriptException {

        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

        try(InputStreamReader reader = getScriptReader("/helloworld.js")) {
            engine.put("name", "Bob");
            engine.eval(reader);
        }
    }

    private static InputStreamReader getScriptReader(String scriptName) {

        return new InputStreamReader(
                NashornExample.class.getResourceAsStream(scriptName));
    }

}
