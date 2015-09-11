package io.devcon5.examples.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This example demonstrates how to create zip file using a the jdk java.util.zip api, which was available prior to
 * JDK7. The example should be compilable with compiler settinges lower that 1.7
 * Created by Gerald Mücke on 11.09.2015.
 */
public class JUZip {

    /**
     * Writes the entries from the map into the specified file as zip entries.
     * @param zipFile
     *  the zip file to be created
     * @param data
     *  a map of entry name (including the relative path inside the zip) and the corresponding data to be
     *  written. the binary data may be null, in order to create empty dirs.
     *  @param emptyFolders
     *   list of path entries to be created. Paths entries are optional as they are implicitly derived from
     *   the relative paths of the entries. But they are required for empty folders.
     * @throws IOException
     */
    public static void pack(File zipFile, Map<String, byte[]> data, String... emptyFolders) throws IOException {

        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for(String pathEntry : emptyFolders) {
                writePathEntry(zos, pathEntry);
            }
            for(Map.Entry<String, byte[]> entry : data.entrySet()){
                writeFileEntry(zos, entry.getKey(), entry.getValue());
            }
        } finally {
            if(zos != null){
                zos.close();
            }
        }
    }

    /**
     * Writes a File entry into a zip file with the given name and the provided binary data.
     * @param zos
     *  the zip output stream to add the file entry to
     * @param name
     *  the name of the file in the zip file, including relative path. That path should not start with '/'
     * @param data
     *  the binary data to be written. May not be null but may be empty
     * @throws IOException
     */
    public static void writeFileEntry(final ZipOutputStream zos, final String name, final byte[] data)
            throws IOException {
        zos.putNextEntry(new ZipEntry(normalize(name)));
        zos.write(data, 0, data.length);
        zos.closeEntry();
    }

    /**
     * Removes all leading '/' and '.' chars. If nothing remains an {@link IllegalArgumentException} is thrown
     * @param name
     *  the path name to be normalized
     * @return
     *  the normalized name
     */
    private static String normalize(final String name) {
        int startIdx = -1;
        for(int i = 0; i < name.length(); i++){
            char c = name.charAt(i);
            if(c != '/' && c != '.'){
                startIdx = i;
                break;
            }
        }
        if(startIdx == -1){
            throw new IllegalArgumentException(name + " is no valid entry for a zip");
        }

        return name.substring(startIdx);
    }

    /**
     * Writes a path entry into the zip file with the given path
     * @param zos
     *  the zip output stream to add the path entry to
     * @param path
     *  the path to be added, may end with '/' or not
     * @throws IOException
     */
    public static void writePathEntry(final ZipOutputStream zos, final String path) throws IOException {
        String pathEntry = path;
        if(!path.endsWith("/")){
            pathEntry += '/';
        }
        zos.putNextEntry(new ZipEntry(normalize(pathEntry)));
        zos.closeEntry();
    }

    public static Map<String, byte[]> unpack(File zipFile){



        return null;
    }

}
