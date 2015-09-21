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

import java.util.HashMap;
import java.util.Map;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

/**
 * Simple Variable mapper, mapping variable names to ValueExpressions.
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
class ELVariableMapper extends VariableMapper {

    private final Map<String, ValueExpression> expressions = new HashMap<String, ValueExpression>();

    @Override
    public ValueExpression resolveVariable(final String variable) {
        return expressions.get(variable);
    }

    @Override
    public ValueExpression setVariable(final String variable, final ValueExpression expression) {
        return expressions.put(variable, expression);
    }
}
