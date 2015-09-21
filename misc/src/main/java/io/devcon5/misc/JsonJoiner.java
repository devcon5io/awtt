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

package io.devcon5.misc;

import java.util.StringJoiner;

/**
 * A collection of factory methods to create StringJoiners for assembling a JSON string.
 * Created by Gerald Mücke on 18.09.2015.
 */
public class JsonJoiner {

    /**
     * Creates a new StringJoiner for specifying Json Objects, which are properties, separated by , and surrounded with
     * braces.
     * @return
     */
    public static StringJoiner object() {

        return new StringJoiner(",", "{", "}");
    }

    /**
     * Creates a new StringJoiner for specifying Json Arrays, which are values, separated by , and surrounded with []
     * @return
     */
    public static StringJoiner array() {

        return new StringJoiner(",", "[", "]");
    }

    /**
     * Creates a JsonProperty, which is a name : value pair.
     * @param name
     * @param value
     * @return
     */
    public static String property(String name, CharSequence value) {

        return new StringJoiner(":").add("\"" + name + "\"").add(value).toString();
    }

    /**
     * Creates a value CharSequence from the given object.
     * @param value
     * @return
     *  if the value is null, &quot;null&quot; is returned. If the value is a string, the string itself wrapped in
     *  quotes is returned. In all other cases the value of the toString() method is invoked.
     */
    public static CharSequence $(final Object value) {

        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return value.toString();
    }

}
