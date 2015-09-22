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

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by Gerald Mücke on 22.09.2015.
 */
public class Arrays {

    /**
     * Casts an array of ints to an array of bytes. As bytes are 8-bit and ints are 32-bit the same limitation
     * applies as to cast an int to a byte. This cast is useful if an array of 8-bit values that are kept as
     * ints for computation should be kept in memory, i.e. as an instance field. For bytes and ints 32-bit of memory
     * are allovated, while for each element of a byte[] array only 8-bit are used.
     * @param input
     *  an int arrays that should be cast to a byte array
     * @return
     *  a byte array with the int values cast to byte.
     */
    public static byte[] castToByteArray(final int[] input) {

        final ByteBuffer bytes = ByteBuffer.allocate(input.length);
        for(int i : input){
            bytes.put((byte)i);
        }

        return bytes.array();
    }

    /**
     * Casts an array of bytes to an array of ints.
     * @param input
     *  an input array of bytes
     * @return
     *  an array of ints resulting from an by-element cast of the input array.
     */
    public static int[] castToIntArray(final byte[] input) {

        final IntBuffer ints = IntBuffer.allocate(input.length);
        for(byte b : input) {
            ints.put(b);
        }
        return ints.array();
    }
}
