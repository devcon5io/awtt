package io.devcon5.examples.mixin;

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
