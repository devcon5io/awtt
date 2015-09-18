package io.devcon5.test;

import static io.devcon5.test.BDD.given;

import org.junit.Test;

/**
 * Created by Gerald M&uuml;cke on 17.09.2015.
 */
public class BDDTest {

    @Test
    public void bdd_example_success() throws Exception {
        given(() -> "a String").when(s -> s + " and another String").then(s -> s.equals("a String and another String"));

    }

    @Test
    public void bdd_exampleConversion_success() throws Exception {
        given(() -> "123").when(Integer::valueOf).then(i -> i.equals(Integer.valueOf(123)));

    }

    @Test
    public void bdd_example_fail() throws Exception {
        given(() -> "a String").when(s -> s + " and another String").then(s -> s.equals("something different"));

    }
}
