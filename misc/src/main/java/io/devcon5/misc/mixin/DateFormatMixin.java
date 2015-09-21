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

package io.devcon5.misc.mixin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Mixin that provided a method returning a formatted date.
 * It requires a method to be implemented that delivers a date-format pattern.
 * This use case is somewhat theoretical but is only used to demonstrate how mixins work.
 * Created by Gerald Mücke on 09.09.2015.
 */
public interface DateFormatMixin {

    String getDateFormatPattern();

    /**
     * Default Method that is present on all instances implementing this interface
     *
     * @return
     */
    default String getFormattedDate() {

        DateFormat format = new SimpleDateFormat(getDateFormatPattern());
        return format.format(getThisDate());
    }

    /**
     * Default method to return the date.
     *
     * @return If the implementing class is of type date, it is returned, otherwise
     * the current date is returned.
     */
    default Date getThisDate() {
        if (this instanceof Date) {
            return (Date) this;
        } else {
            return new Date();
        }
    }
}
