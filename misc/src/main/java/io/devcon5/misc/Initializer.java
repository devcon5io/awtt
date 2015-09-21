package io.devcon5.misc;

import java.util.function.Consumer;

/**
 * Simple initializer that may be invoked to initialize a specific instance
 * Created by Gerald Mücke on 11.09.2015.
 */
public final class Initializer {

    private Initializer(){}


    public static <T> T init(T instance, Consumer<T> initializer){
        initializer.accept(instance);
        return instance;
    }


}
