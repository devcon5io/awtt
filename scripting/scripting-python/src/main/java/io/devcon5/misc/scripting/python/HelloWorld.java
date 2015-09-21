package io.devcon5.misc.scripting.python;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple example how to create a python (jython) scripting engine and execute an external jython script.
 * Created by Gerald Mücke on 09.09.2015.
 */
public class HelloWorld {

    public static void main(String... args) throws IOException, ScriptException {

        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("python");

        try(InputStreamReader reader = getScriptReader("/helloworld.py")) {
            engine.put("name", "Bob");
            engine.eval(reader);
        }
    }

    private static InputStreamReader getScriptReader(String scriptName) {

        return new InputStreamReader(
                HelloWorld.class.getResourceAsStream(scriptName));
    }

}
