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

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Simple BDD-style test builder based on lambda expressions.
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
public class BDD {

    private BDD() {
    }

    /**
     * Creates a precondition for a behavior-driven test. The supplier creates the subject under test.
     * @param precondition
     *  the precondition definition providing the prepared subject-under-test
     * @param <PRECONDITION>
     *  the type of the subject-under-test
     * @return
     *  a precondition handler for defining the action to perform
     */
    public static <PRECONDITION> PreconditionHandler<PRECONDITION> given(Supplier<PRECONDITION> precondition) {
        return new PreconditionHandler<>(precondition);
    }

    public static class PreconditionHandler<PRECONDITION> {
        private final Supplier<PRECONDITION> precondition;

        PreconditionHandler(Supplier<PRECONDITION> precondition) {
            this.precondition = precondition;
        }

        /**
         * Creates an action handler execution the action statement. The action function performing the
         * activity-under-test.
         * @param action
         *  the action function mapping the prepared subject-under-test creating a testable result.
         * @param <RESULT>
         *  the type of the outcome of the action to be verified.
         * @return
         *  an action handler for defining the post condition or test.
         */
        public <RESULT> ActionHandler<PRECONDITION, RESULT> when(Function<PRECONDITION, RESULT> action) {
            return new ActionHandler<>(precondition, action);
        }
    }

    public static class ActionHandler<PRECONDITION, RESULT> {

        private final Supplier<PRECONDITION> precondition;
        private final Function<PRECONDITION, RESULT> action;

        ActionHandler(Supplier<PRECONDITION> precondition, Function<PRECONDITION, RESULT> action) {
            this.precondition = precondition;
            this.action = action;
        }

        /**
         * Defines the post-condition containing testing the outcome of the activity-under-test
         * @param consequence
         *  the activities to perform the assertions to test the result.
         */
        public void then(Predicate<RESULT> consequence) {
            if (!consequence.test(action.apply(precondition.get()))) {
                throw new AssertionError("Test failed");
            }
        }
    }
}
