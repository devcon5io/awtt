package io.devcon5.examples.lambdas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Gerald Mücke on 11.09.2015.
 */
public class InitializableCollectionExample {

    public static void main(String... args) {
        Map<String,String> map = init(new HashMap<>(), m -> m.put("test", "value"));
        System.out.println(map);
        Collection<String> col = init(new ArrayList<>(), c -> { c.add("one"); c.add("two");});
        System.out.println(col);

    }

    public static <T> T init(T instance, Consumer<T> initializer){
        initializer.accept(instance);
        return instance;
    }


}
