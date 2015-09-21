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

import static io.devcon5.misc.Initializer.init;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Created by Gerald Mücke on 21.09.2015.
 */
public class InitializerTest {

    @Test
    public void double_brace_initialization() throws Exception {
        //prepare
        Collection<String> col = new ArrayList<String>(){{
            add("one");
            add("two");
        }};

        //act

        //assert
        assertNotNull(col);
        assertEquals(2, col.size());
        assertTrue(col.contains("one"));
        assertTrue(col.contains("two"));

    }

    @Test
    public void init_collection() throws Exception {
        //prepare
        Collection target = new ArrayList<>();
        //act
        Collection<String> col = init(target, c -> { c.add("one"); c.add("two");});

        //assert
        assertNotNull(col);
        assertEquals(2, col.size());
        assertTrue(col.contains("one"));
        assertTrue(col.contains("two"));


    }

    @Test
    public void init_map() throws Exception {
        //prepare
        Map<String, String> target = new HashMap<>();

        //act
        Map<String,String> map = init(target   , m -> m.put("test", "value"));

        //assert
        assertNotNull(map);
        assertTrue(map.containsKey("test"));
        assertEquals("value", map.get("test"));
    }

}
