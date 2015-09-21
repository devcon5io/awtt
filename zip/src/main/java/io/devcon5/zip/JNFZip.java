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

import static java.nio.file.FileSystems.newFileSystem;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.write;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This example demonstrates how to operate on zip files using the java.nio.files api. This approach allows to
 * operate on zip files without unpacking them and any utility written for the file api can be used to modify
 * zip content as well
 * Created by Gerald Mücke on 11.09.2015.
 */
public class JNFZip {

    public static void pack(Path zipFile, Map<String, byte[]> data, String... emptyFolders) throws IOException {

        Map<String, String> env = new HashMap<String, String>() {{
            put("create", "true");
        }};

        try(FileSystem zipFs = newFileSystem(URI.create("jar:" + zipFile.toUri()), env)) {
            for(String path : emptyFolders){
                createDirectories(zipFs.getPath(path));
            }
            for(Map.Entry<String, byte[]> entry : data.entrySet()){
                Path entryPath = zipFs.getPath(entry.getKey());
                createDirectories(entryPath.getParent());
                write(entryPath, entry.getValue());
            }
        }
    }
}
