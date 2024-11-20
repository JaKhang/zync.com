package com.zync.network.media.infrastructure.utils;

import com.zync.network.core.domain.ZID;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileNameUtils {

    /**
     * Changes the extension of the given file name.
     *
     * @param fileName the original file name
     * @param newExt   the new extension (e.g., "jpg", "mp4")
     * @return the file name with the changed extension
     */
    public static String changeExtension(String fileName, String newExt) {
        String baseName = getBaseName(fileName);
        return baseName + "." + newExt;
    }

    /**
     * Changes the base name of the file while keeping the extension.
     *
     * @param fileName the original file name
     * @param newBase  the new base name
     * @return the file name with the changed base name
     */
    public static String changeBaseName(String fileName, String newBase) {
        String ext = getExtension(fileName);
        return newBase + (ext.isEmpty() ? "" : "." + ext);
    }

    /**
     * Generates a random base name for the given file using UUID.
     *
     * @param fileName the original file name
     * @return the file name with a random base name and the original extension
     */
    public static String generateRandomBaseName(String fileName) {
        String randomBase = ZID.fast().toLowerCase();
        return changeBaseName(fileName, randomBase);
    }

    /**
     * Extracts the base name (without the extension) from the file name.
     *
     * @param fileName the original file name
     * @return the base name of the file
     */
    public static String getBaseName(String fileName) {
        Path path = Paths.get(fileName);
        String name = path.getFileName().toString();
        int lastIndex = name.lastIndexOf('.');
        return (lastIndex == -1) ? name : name.substring(0, lastIndex);
    }

    /**
     * Extracts the extension from the file name.
     *
     * @param fileName the original file name
     * @return the file extension (without the dot), or an empty string if no extension is found
     */
    public static String getExtension(String fileName) {
        Path path = Paths.get(fileName);
        String name = path.getFileName().toString();
        int lastIndex = name.lastIndexOf('.');
        return (lastIndex == -1) ? "" : name.substring(lastIndex + 1);
    }

    public static void main(String[] args) {
        // Test cases
        String fileName = "example.png";

        System.out.println("Original: " + fileName);
        System.out.println("Change Extension: " + changeExtension(fileName, "jpg"));
        System.out.println("Change Base Name: " + changeBaseName(fileName, "newFileName"));
        System.out.println("Generate Random Base Name: " + generateRandomBaseName(fileName));
        System.out.println("Base Name: " + getBaseName(fileName));
        System.out.println("Extension: " + getExtension(fileName));
    }
}