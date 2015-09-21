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
