package io.devcon5.examples.strings;

import static org.junit.Assert.assertEquals;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.StringReader;
import java.util.StringJoiner;

import org.junit.Test;

/**
 * Created by Gerald Mücke on 18.09.2015.
 */
public class StringJoinerJsonExample {

    @Test
    public void test_createJson() throws Exception {
        //prepare

        //act
        String jsonString = newJsonObject().add(newProperty("propertyA", value("aValue")))
                                           .add(newProperty("propertyB", value("bValue")))
                                           .add(newProperty("aArray",
                                                            newJsonArray().add(value("a")).add(value("b")).toString()))
                                           .toString();

        System.out.println(jsonString);

        //assert
        JsonObject json = Json.createReader(new StringReader(jsonString)).readObject();
        assertEquals("aValue", json.getString("propertyA"));
        assertEquals("bValue", json.getString("propertyB"));
        JsonArray array = json.getJsonArray("aArray");
        assertEquals(2, array.size());
        assertEquals("a", array.getString(0));
        assertEquals("b", array.getString(1));

    }

    public static StringJoiner newJsonObject() {

        return new StringJoiner(",", "{", "}");
    }

    public static StringJoiner newJsonArray() {

        return new StringJoiner(",", "[", "]");
    }

    public static String newProperty(String name, CharSequence value) {

        return new StringJoiner(":").add("\"" + name + "\"").add(value).toString();
    }

    public static CharSequence value(final Object value) {

        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return value.toString();
    }

}
