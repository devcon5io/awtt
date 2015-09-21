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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Created by Gerald Mücke on 21.09.2015.
 */
public class CollectionsTest {

    @Test
    public void testJoin_ListsOfStrings() throws Exception {

        //prepare
        List<List<String>> list1 = asList(asList("1", "2"), asList("3"), asList("4", "5"));
        List<List<String>> list2 = asList(asList("a", "b"), asList("c"), asList("d", "e"));

        //act
        List<List<String>> joined = Collections.join(list1, list2);

        //assert
        assertNotNull(joined);
        assertEquals(6, joined.size());
        assertTrue(joined.get(0).contains("1"));
        assertTrue(joined.get(0).contains("2"));
        assertTrue(joined.get(1).contains("3"));
        assertTrue(joined.get(2).contains("4"));
        assertTrue(joined.get(2).contains("5"));
        assertTrue(joined.get(3).contains("a"));
        assertTrue(joined.get(3).contains("b"));
        assertTrue(joined.get(4).contains("c"));
        assertTrue(joined.get(5).contains("d"));
        assertTrue(joined.get(5).contains("e"));
    }
}
