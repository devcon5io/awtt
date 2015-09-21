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

package io.devcon5.zip;

import static java.lang.System.nanoTime;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Created by Gerald Mücke on 11.09.2015.
 */
public class ZipPerfTest {

    @Test
    public void test() throws Exception {
        final JUnitCore junit = new JUnitCore();

        int runCount = 100000;

        perftest("JUZ", runCount, () -> junit.run(JUZip.class));
        perftest("JNF", runCount, () -> junit.run(JNFZip.class));
        perftest("Streams", runCount, () -> junit.run(StreamsZip.class));

    }

    static void perftest(String name, int count, Runnable r){
        long start = nanoTime();
        for (int i = 0; i < count; i++) {
            r.run();
        }
        long end = nanoTime();
        System.out.println(name +": " + (end-start)/1_000_000 + "ms");
    }

}
