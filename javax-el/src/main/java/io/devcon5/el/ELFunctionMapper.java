package io.devcon5.el;

import static io.devcon5.test.Assert.assertThat;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.FunctionMapper;

/**
 * Simple implementation of mapping static functions.
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
class ELFunctionMapper extends FunctionMapper {
    private final Map<String, Method> functionMap = new HashMap<String, Method>();

    @Override
    public Method resolveFunction(final String prefix, final String localName) {
        return functionMap.get(prefix + ":" + localName);
    }

    @Override
    public void mapFunction(final String prefix, final String localName, final Method method) {
        assertThat(method).fulfills(m ->
                        isPublic(m.getModifiers())
                                && isStatic(m.getModifiers())
                                && m.getReturnType() != Void.TYPE
        ).otherwise(() -> {
            throw new IllegalArgumentException("method is not public static non-void");
        });

        functionMap.put(prefix + ":" + localName, method);
    }

}
