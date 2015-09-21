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

import static java.nio.file.Files.exists;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isRegularFile;
import static java.nio.file.Files.readAllBytes;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Created by Gerald Mücke on 11.09.2015.
 */
public class StreamsZipTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testPack() throws Exception {

        //given
        File zipFile = new File(folder.getRoot(), "test.zip");
        Map<String, byte[]> data = new HashMap<>();
        data.put("test/simple.txt", "test".getBytes());

        //when
        StreamsZip.pack(zipFile.toPath(), data, "emptyFolder/");

        //then
        try(FileSystem zipFs = FileSystems.newFileSystem(URI.create("jar:" + zipFile.toURI()), emptyMap())) {

            final Path emptyFolder = zipFs.getPath("/emptyFolder");
            final Path textFile = zipFs.getPath("/test/simple.txt");
            assertTrue(exists(emptyFolder));
            assertTrue(isDirectory(emptyFolder));
            assertTrue(exists(textFile));
            assertTrue(isRegularFile(textFile));
            assertEquals("test", new String(readAllBytes(textFile)));
        }
    }
}
