package io.devcon5.examples.zip;

import static java.nio.file.FileSystems.newFileSystem;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.write;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gerald Mücke on 11.09.2015.
 */
public class StreamsZip {

    public static void pack(Path zipFile, Map<String, byte[]> data, String... emptyFolders) throws IOException {

        Map<String, String> env = new HashMap<String, String>() {{
            put("create", "true");
        }};

        try(FileSystem zipFs = newFileSystem(URI.create("jar:" + zipFile.toUri()), env)) {
            asList(emptyFolders).stream().map(zipFs::getPath).forEach(StreamsZip::createDirs);
            data.forEach((name, b) -> createFile(zipFs.getPath(name), b));
        }
    }

    static void createFile(Path path,  byte[] data) {
        try {
            createDirs(path.getParent());
            write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void createDirs(Path path) {
        try {
            createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
