/*
 * Copyright 2015,2016 DevCon5 GmbH, info@devcon5.io
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
 *
 */

/**
 * Optional monad for Javascript.
 * @type {{EMPTY: Object, empty: Optional.empty, of: Optional.of}}
 */
var Optional = {
    /**
     * Creates an optional of the specified value. The value may be undefined or null.
     * @param value
     *  the value of the otional
     * @returns {{get: get, orElse: orElse, ifPresent: ifPresent, isPresent: isPresent, filter: filter, map: map, flatMap: flatMap}}
     */
    of: function (value) {

        function nullOrUndefined(value) {
            return value == undefined || value == null;
        }

        return {
            /**
             * The value of the optional
             * @returns {*}
             *  the value if it is not null or throws an Exception if the value is undefined or null
             */
            get: function () {
                if (nullOrUndefined(value)) {
                    throw "Optional value is undefined or null";
                }
                return value;
            },
            /**
             * Checks if the optional contains a value.
             * @param elseValue
             *  the value to be returned if the optional contains no value
             * @returns {*}
             */
            orElse: function (elseValue) {
                if (nullOrUndefined(value)) {
                    return elseValue;
                }
                return value;
            },
            /**
             * Applies the function if the optional value is present.
             * @param func
             *  the function to be executed. Must accept a single parameter
             */
            ifPresent: function (func) {
                if (!nullOrUndefined(value)) {
                    func(value);
                }
            },
            /**
             * Checks if the Optional contains a value.
             * @returns {boolean}
             *  true if the value is defined and not null
             */
            isPresent: function () {
                return !nullOrUndefined(value);
            },
            /**
             * Applies a filter function to this Optional.
             * @param filterFunction
             *  the filter function must accept a single parameter (the value to test) must return a boolean value
             *  indicating whether the test was successful or not
             * @returns {*}
             *  this optional if the filter test succeeded or empty if not
             */
            filter: function (filterFunction) {
                if (nullOrUndefined(value) || filterFunction(value)) {
                    return this;
                } else {
                    return Optional.EMPTY;
                }
            },
            /**
             * Maps the value of the optional onto another optional with another value. The mapping is done by the
             * supplied mapping function. The resulting value is wrapped in an Optional
             * @param mapperFunction
             *  the mapping function used to map the value. The function must accept a single parameter (the value)
             * @returns {*}
             *  an optional of the mapped value or the empty optional if the value is null or undefined
             */
            map: function (mapperFunction) {
                if (nullOrUndefined(value)) {
                    return Optional.EMPTY;
                } else {
                    return Optional.of(mapperFunction(value));
                }
            },
            /**
             * Similar to the map method except that the value of the mapperFunction is returned directly.
             * @param mapperFunction
             *  the mapping function used to map the value. The function must accept a single parameter (the value)
             * @returns {*}
             *  the mapped value
             */
            flatMap: function (mapperFunction) {
                return mapperFunction(value);
            }
        }
    },
    /**
     * An empty optional
     * @returns {*}
     */
    empty: function () {
        return Optional.EMPTY;
    },
}
Optional.EMPTY = Object.freeze(Optional.of(null));

