package io.devcon5.examples.mixins;

import static java.lang.reflect.Proxy.newProxyInstance;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Set;

/**
 * Created by Gerald Mücke on 11.09.2015.
 */
public class Mixin {

    public static Object addMixin(final Object target, Class... mixins) {

        return newProxyInstance(Mixin.class.getClassLoader(),
                                    mixins,
                                    (proxy, method, args) -> {
                                        if (method.isDefault()) {

                                            final Class<?> declaringClass = method.getDeclaringClass();

                                            return lookupIn(declaringClass)
                                                                .unreflectSpecial(method, declaringClass)
                                                                .bindTo(proxy)
                                                                .invokeWithArguments(args);
                                        }

                                        return method.invoke(target, args);
                                    });
    }

    /**
     * Creates a method lookup that is capable of accessing private members of declaring classes this Mixin factory
     * has usually no access to.
     * @param declaringClass
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private static MethodHandles.Lookup lookupIn(final Class<?> declaringClass)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(
                Class.class,
                int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE);
    }

    public static Object addMixin(final String target, final URL script, final Class... mixinClasses)
            throws IOException, ScriptException {

        final Set<Class> mixins = asList(mixinClasses).stream().collect(toSet());
        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        try(InputStreamReader reader = new InputStreamReader(script.openStream())){
            engine.eval(reader);
        }
        final Invocable invocable = (Invocable) engine;

        return newProxyInstance(Mixin.class.getClassLoader(),
                                mixinClasses,
                                (proxy, method, args) -> {
                                    final Class<?> declaringClass = method.getDeclaringClass();
                                    if (mixins.contains(declaringClass)) {
                                        Object scriptProxy = invocable.getInterface(declaringClass);
                                        if(scriptProxy != null) {
                                            return method.invoke(scriptProxy, args);
                                        }
                                        if (method.isDefault()) {
                                            return lookupIn(declaringClass)
                                                    .unreflectSpecial(method, declaringClass)
                                                    .bindTo(proxy)
                                                    .invokeWithArguments(args);
                                        }
                                    }
                                    return method.invoke(target, args);
                                });

    }
}
