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
