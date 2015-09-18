package io.devcon5.examples.mixin;

import java.util.Date;

/**
 * This mixin example shows, how default methods on interfaces can be used to extend existing classes with custom
 * functionality. Various interfaces can be combined, adding functionality to a core object.
 * Created by Gerald Mücke on 09.09.2015.
 */
public class DateMixinExample {

    public static void main(String... args) {

        //call the mixin method
        System.out.println(new MyDate().getFormattedDate());

    }

    //define a new class, extending an existing one, adding the mixing (interface with default methods)
    public static class MyDate extends Date implements DateFormatMixin {

        @Override
        public String getDateFormatPattern() {

            return "yyyy-MM-dd";
        }
    }

}
