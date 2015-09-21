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
