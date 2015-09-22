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

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gerald Mücke on 22.09.2015.
 */
public class ArraysTest {

    @Test
    public void test_cast_intArrayToByteArray() throws Exception {
        //prepare
        int[] input = {1,2,3,4,5,6,7};
        //act
        byte[] output = Arrays.castToByteArray(input);

        //assert
        Assert.assertArrayEquals(new byte[]{1,2,3,4,5,6,7}, output );

    }

    @Test
    public void test_cast_byteArrayToIntArray() throws Exception {
        //prepare
        byte[] input = {1,2,3,4,5, 6,7};
        //act
        int[] output = Arrays.castToIntArray(input);

        //assert
        Assert.assertArrayEquals(new int[]{1,2,3,4,5,6,7}, output);

    }

}
