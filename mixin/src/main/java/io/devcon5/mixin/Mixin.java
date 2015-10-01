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

package io.devcon5.mixin;

import static java.lang.reflect.Proxy.newProxyInstance;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Utility for adding mixins to instances at runtime. But not only default methods can be added but dynamic
 * implementations as well by providing a javascript based implementation for the methods. Created by Gerald Mücke on
 * 11.09.2015.
 */
public class Mixin {

    /**
     * Creates a builder for defining the mixin assignment. The builder can be applied to multiple objects using the
     * {@code to()} method
     *
     * @param mixins the mixin interfaces to be added
     * @return a builder for creating a mxin
     */
    public static OngoingMixinCreation addMixin(Class... mixins) {

        return new OngoingMixinCreation(mixins);
    }

    /**
     * Invokes the specified method on the specified object and wrapping all exceptions in a RuntimeException.
     *
     * @param target the target object on which the method should be invoked. May be null for static method invocation
     * @param method the method to be invoked
     * @param args   the arguments to be passed to the method. May be empty
     * @return the result of the invocation
     */
    private static Object invoke(final Object target, final Method method, Object... args) {

        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invokes a default method. Default methods are implemented in an interface which is overrided by applying the
     * interface to the proxy. This method allows to invoke the default method on the proxy itselfs bypassing the
     * overriden method.
     *
     * @param proxy  the proxy object on which the method should be invoked
     * @param method the method to be invoked
     * @param args   arguments that are passed to the method
     * @return the return value of the invocation
     * @throws Throwable
     */
    private static Object invokeDefault(final Object proxy, final Method method, final Object[] args) {

        final Class<?> declaringClass = method.getDeclaringClass();
        try {
            return lookupIn(declaringClass).unreflectSpecial(method, declaringClass)
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    /**
     * Creates a method lookup that is capable of accessing private members of declaring classes this Mixin factory has
     * usually no access to.
     *
     * @param declaringClass
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private static MethodHandles.Lookup lookupIn(final Class<?> declaringClass) {

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

    /**
     * Creates a new invocable proxy. The proxy may be used to create interface implementations that are implemented in
     * javascript.
     *
     * @param target  the target object that is passed into the scripting context so that the methods in the script may access
     *                the target object.
     * @param scripts scripts to be loaded into the engine
     * @return the invocable instance
     */
    private static Optional<Invocable> newInvocable(final Object target, final Collection<Supplier<Reader>> scripts) {

        Optional<Invocable> invocable;
        if (!scripts.isEmpty()) {
            final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            engine.put("target", target);
            scripts.stream().forEach(script -> loadScript(engine, script));
            invocable = Optional.of((Invocable) engine);
        } else {
            invocable = Optional.empty();
        }

        return invocable;
    }

    /**
     * Loads a single script from an URL into the script engine
     *
     * @param engine       the engine that should evaluate the script
     * @param scriptReader the script source to be loaded into the script
     */
    private static void loadScript(ScriptEngine engine, Supplier<Reader> scriptReader) {

        try (Reader reader = scriptReader.get()) {
            engine.eval(reader);
        } catch (ScriptException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Builder to create a mixin
     */
    public static class OngoingMixinCreation {

        private final List<Class> mixins;
        private final Set<Supplier<Reader>> scripts = new HashSet<>();

        private OngoingMixinCreation(Class... mixinInterfaces) {

            this.mixins = Arrays.asList(mixinInterfaces);
        }

        /**
         * Adds reader suppliers for script sources. The suppliers provide a reader that is evaluated by the script
         * engine in order to dynamicaly implement interfaces.
         *
         * @param scriptSource the source of a javascript  to be added to the object
         * @return this builder
         */
        public OngoingMixinCreation withScript(Supplier<Reader>... scriptSource) {

            this.scripts.addAll(Arrays.asList(scriptSource));

            return this;
        }

        /**
         * Adds the mixins to the target object. The method can be invoked on different objects creating a new proxy for
         * each.
         *
         * @param target the target object for the mixins
         * @return the object with mixins
         */
        public Object to(Object target) {

            final Optional<Invocable> inv = newInvocable(target, this.scripts);

            return newProxyInstance(Mixin.class.getClassLoader(),
                    this.mixins.toArray(new Class[0]),
                    (proxy, method, args) -> handleInvocation(target,
                            proxy,
                            mixins,
                            inv,
                            method,
                            args));
        }

        /**
         * Performs the dynamic resolution of default and script-implemented-methods. The order of execution is <ol>
         * <li>script implemented method (if found)</li> <li>implemented method</li> <li>default method</li> </ol>
         *
         * @param target the target object
         * @param proxy  the mxing proxy
         * @param mixins list of mixin interface classes
         * @param inv    the invocable script engine
         * @param method the method to be invoked
         * @param args   the arguments to be passed to the method
         * @return
         */
        private static Object handleInvocation(final Object target,
                                               final Object proxy,
                                               final List<Class> mixins,
                                               final Optional<Invocable> inv,
                                               final Method method,
                                               final Object[] args) {

            Class declaringClass = method.getDeclaringClass();
            if (mixins.contains(declaringClass)) {
                final Optional<Object> result = inv.map(invocable -> invocable.getInterface(declaringClass))
                                                   .map(scriptProxy -> invoke(scriptProxy, method, args));
                if(result.isPresent()){
                    return result.get();
                }
                if (method.isDefault()) {
                    return invokeDefault(proxy, method, args);
                }
            }
            return invoke(target, method, args);
        }
    }
}
