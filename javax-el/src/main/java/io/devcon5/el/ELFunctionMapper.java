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
