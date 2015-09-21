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

package io.devcon5.misc;

import static io.devcon5.misc.JsonJoiner.$;
import static io.devcon5.misc.JsonJoiner.array;
import static io.devcon5.misc.JsonJoiner.object;
import static io.devcon5.misc.JsonJoiner.property;
import static org.junit.Assert.assertEquals;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.StringReader;

import org.junit.Test;

/**
 * Created by Gerald Mücke on 21.09.2015.
 */
public class JsonJoinerTest {

    @Test
    public void test_createJson() throws Exception {
        //prepare

        //act
        String jsonString = object().add(property("propertyA", $("aValue")))
                                    .add(property("propertyB", $("bValue")))
                                    .add(property("aArray", $(array().add($("a")).add($("b")))))
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
}
