package io.devcon5.test;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Simple BDD-style test builder based on lambda expressions.
 * Created by m4g on 17.09.2015.
 */
public class BDD {

    private BDD() {
    }

    public static <PRECONDITION> PreconditionHandler<PRECONDITION> given(Supplier<PRECONDITION> precondition) {
        return new PreconditionHandler<>(precondition);
    }

    public static class PreconditionHandler<PRECONDITION> {
        private final Supplier<PRECONDITION> precondition;

        PreconditionHandler(Supplier<PRECONDITION> precondition) {
            this.precondition = precondition;
        }

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

        public void then(Predicate<RESULT> consequence) {
            if (!consequence.test(action.apply(precondition.get()))) {
                throw new AssertionError("Test failed");
            }
        }
    }
}
