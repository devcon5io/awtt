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

package io.devcon5.misc.streams;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

/**
 * Examples for joining lists of items into one
 * Created by Gerald Mücke on 14.09.2015.
 */
public class ListJoinExample {

    public static void main(String... args) {

        List<List<String>> list1 = asList(asList("1", "2"), asList("3"), asList("4", "5"));
        List<List<String>> list2 = asList(asList("a", "b"), asList("c"), asList("d", "e"));
        System.out.println(join(list1, list2));
    }

    /**
     * Joins two collections into one collection containing all elements.
     *
     * @param element the collections of elements to join into one
     * @param <C>     type of the collection
     * @param <E>     type of the collection's elements
     * @return a list containing all elements
     */
    public static <C, E extends Collection<C>> List<C> join(E... element) {
        return stream(element).flatMap(Collection::stream).collect(toList());
    }

}
