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

package io.devcon5.misc.mixins;

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
