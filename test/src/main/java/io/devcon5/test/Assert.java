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

package io.devcon5.test;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Simple helper to define assertions.
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
public class Assert {

    private Assert() {
    }

    public static <T> PredicateHandler<T> assertThat(T subject) {
        return new PredicateHandler<>(subject);
    }

    public static class PredicateHandler<T> {

        private final T subject;

        PredicateHandler(T subject) {
            this.subject = subject;
        }

        public ErrorHandler fulfills(Predicate<T> predicate) {
            return new ErrorHandler(subject, predicate, a -> a);
        }

    }

    public static class ErrorHandler<T> {
        private final Predicate<T> predicate;
        private final T subject;

        private final UnaryOperator<Predicate<T>> op;

        ErrorHandler(T subject, Predicate<T> predicate, UnaryOperator<Predicate<T>> op) {
            this.subject = subject;
            this.predicate = predicate;
            this.op = op;
        }

        public void otherwise(Runnable consequence) {
            if (!op.apply(predicate).test(subject)) {
                consequence.run();
            }
        }
    }
}
