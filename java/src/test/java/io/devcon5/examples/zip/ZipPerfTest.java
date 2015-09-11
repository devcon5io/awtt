package io.devcon5.examples.zip;

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
