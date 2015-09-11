package io.devcon5.examples.mixins;

import static java.lang.reflect.Proxy.newProxyInstance;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gerald Mücke on 11.09.2015.
 */
public class Mixin {

    public static MixinBuilder addMixin(Class... mixins) {
        return new MixinBuilder(mixins);
    }


    private static void loadScript(ScriptEngine engine, URL script){
        try(InputStreamReader reader = new InputStreamReader(script.openStream())){
            engine.eval(reader);
        } catch (IOException  | ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object invoke(final Object target, final Method method, Object[] args){

        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Invocable newInvocable(final Object target, final Collection<URL> scripts)  {

        Invocable invocable = null;
        if(!scripts.isEmpty()){
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            engine.put("target", target);
            scripts.stream().forEach(u -> loadScript(engine, u));
            invocable = (Invocable)engine;
        }

        return invocable;
    }

    /**
     * Invokes a default method
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    private static Object invokeDefault(final Object proxy, final Method method, final Object[] args)  {

        final Class<?> declaringClass = method.getDeclaringClass();
        try {
            return lookupIn(declaringClass)
                                .unreflectSpecial(method, declaringClass)
                                .bindTo(proxy)
                                .invokeWithArguments(args);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
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
    private static MethodHandles.Lookup lookupIn(final Class<?> declaringClass){


        try {
            final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(
                    Class.class,
                    int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public static class MixinBuilder {

        private final List<Class> mixins;
        private final Set<URL> scripts = new HashSet<>();

        private MixinBuilder(Class... mixinInterfaces){
            this.mixins = Arrays.asList(mixinInterfaces);
        }

        public MixinBuilder withScript(URL... scriptSource) {
            this.scripts.addAll(Arrays.asList(scriptSource));
            return this;
        }

        /**
         * Adds the mixins to the target object
         * @param target
         *  the target object for the mixins
         * @return
         *  the object with mixins
         */
        public Object to(Object target){
            final Invocable inv = newInvocable(target, this.scripts);

            return newProxyInstance(Mixin.class.getClassLoader(), this.mixins.toArray(new Class[0]),
                                    (proxy, method,args) -> {
                final Class<?> declaringClass = method.getDeclaringClass();
                if (mixins.contains(declaringClass)) {

                    if(inv != null){
                        final Object scriptProxy = inv.getInterface(declaringClass);
                        if (scriptProxy != null) {
                            return invoke(scriptProxy, method, args);
                        }
                        if (method.isDefault()) {
                            return invokeDefault(proxy, method, args);
                        }
                    }
                }
                return invoke(target, method, args);
            });
        }
    }
}
