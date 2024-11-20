package com.zync.network.media.infrastructure.utils;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MediaTypeUtil {
    // Mapping file extensions to MediaType
    private static final Map<String, MediaType> MEDIA_TYPE_MAP = new HashMap<>();

    static {
        // Image Types
        MEDIA_TYPE_MAP.put("jpg", MediaType.IMAGE_JPEG);
        MEDIA_TYPE_MAP.put("jpeg", MediaType.IMAGE_JPEG);
        MEDIA_TYPE_MAP.put("png", MediaType.IMAGE_PNG);
        MEDIA_TYPE_MAP.put("gif", MediaType.IMAGE_GIF);
        MEDIA_TYPE_MAP.put("svg", MediaType.valueOf("image/svg+xml"));
        MEDIA_TYPE_MAP.put("bmp", MediaType.valueOf("image/bmp"));
        MEDIA_TYPE_MAP.put("tiff", MediaType.valueOf("image/tiff"));
        MEDIA_TYPE_MAP.put("webp", MediaType.valueOf("image/webp"));

        // Document Types
        MEDIA_TYPE_MAP.put("pdf", MediaType.APPLICATION_PDF);
        MEDIA_TYPE_MAP.put("doc", MediaType.valueOf("application/msword"));
        MEDIA_TYPE_MAP.put("docx", MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        MEDIA_TYPE_MAP.put("xls", MediaType.valueOf("application/vnd.ms-excel"));
        MEDIA_TYPE_MAP.put("xlsx", MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        MEDIA_TYPE_MAP.put("ppt", MediaType.valueOf("application/vnd.ms-powerpoint"));
        MEDIA_TYPE_MAP.put("pptx", MediaType.valueOf("application/vnd.openxmlformats-officedocument.presentationml.presentation"));
        MEDIA_TYPE_MAP.put("txt", MediaType.TEXT_PLAIN);
        MEDIA_TYPE_MAP.put("csv", MediaType.valueOf("text/csv"));
        MEDIA_TYPE_MAP.put("html", MediaType.TEXT_HTML);
        MEDIA_TYPE_MAP.put("xml", MediaType.APPLICATION_XML);
        MEDIA_TYPE_MAP.put("json", MediaType.APPLICATION_JSON);

        // Video Types
        MEDIA_TYPE_MAP.put("mp4", MediaType.valueOf("video/mp4"));
        MEDIA_TYPE_MAP.put("avi", MediaType.valueOf("video/x-msvideo"));
        MEDIA_TYPE_MAP.put("mov", MediaType.valueOf("video/quicktime"));
        MEDIA_TYPE_MAP.put("wmv", MediaType.valueOf("video/x-ms-wmv"));
        MEDIA_TYPE_MAP.put("mkv", MediaType.valueOf("video/x-matroska"));
        MEDIA_TYPE_MAP.put("webm", MediaType.valueOf("video/webm"));
        MEDIA_TYPE_MAP.put("flv", MediaType.valueOf("video/x-flv"));

        // Audio Types
        MEDIA_TYPE_MAP.put("mp3", MediaType.valueOf("audio/mpeg"));
        MEDIA_TYPE_MAP.put("wav", MediaType.valueOf("audio/wav"));
        MEDIA_TYPE_MAP.put("ogg", MediaType.valueOf("audio/ogg"));
        MEDIA_TYPE_MAP.put("aac", MediaType.valueOf("audio/aac"));
        MEDIA_TYPE_MAP.put("flac", MediaType.valueOf("audio/flac"));

        // Archive Types
        MEDIA_TYPE_MAP.put("zip", MediaType.valueOf("application/zip"));
        MEDIA_TYPE_MAP.put("rar", MediaType.valueOf("application/vnd.rar"));
        MEDIA_TYPE_MAP.put("tar", MediaType.valueOf("application/x-tar"));
        MEDIA_TYPE_MAP.put("gz", MediaType.valueOf("application/gzip"));
        MEDIA_TYPE_MAP.put("7z", MediaType.valueOf("application/x-7z-compressed"));
    }

    /**
     * Get the MediaType for a given file extension.
     * @param extension The file extension (without the dot).
     * @return MediaType corresponding to the file extension, or application/octet-stream as a default.
     */
    public static MediaType getMediaTypeForFileName(String extension) {
        return Optional.ofNullable(MEDIA_TYPE_MAP.get(extension.toLowerCase()))
                .orElse(MediaType.APPLICATION_OCTET_STREAM);  // Default to binary content type
    }

    /**
     * Get MediaType from a filename.
     * @param fileName The name of the file.
     * @return MediaType based on file extension, or application/octet-stream if unknown.
     */
    public static MediaType getMediaTypeForFileNameFromFileName(String fileName) {
        String extension = getExtensionFromFileName(fileName);
        return getMediaTypeForFileName(extension);
    }

    /**
     * Get the extension from a file name.
     * @param fileName The file name (e.g., "example.jpg").
     * @return The extension (e.g., "jpg"), or empty string if not found.
     */
    public static String getExtensionFromFileName(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    /**
     * Check if a given file is a video based on its MediaType.
     * @param mediaType The MediaType to check.
     * @return true if the MediaType is a video type.
     */
    public static boolean isVideo(MediaType mediaType) {
        return mediaType != null && mediaType.getType().equalsIgnoreCase("video");
    }

    /**
     * Check if a given file is an image based on its MediaType.
     * @param mediaType The MediaType to check.
     * @return true if the MediaType is an image type.
     */
    public static boolean isImage(MediaType mediaType) {
        return mediaType != null && mediaType.getType().equalsIgnoreCase("image");
    }

    /**
     * Check if a given file is an audio file based on its MediaType.
     * @param mediaType The MediaType to check.
     * @return true if the MediaType is an audio type.
     */
    public static boolean isAudio(MediaType mediaType) {
        return mediaType != null && mediaType.getType().equalsIgnoreCase("audio");
    }

    /**
     * Check if a given file is a document based on its MediaType.
     * @param mediaType The MediaType to check.
     * @return true if the MediaType is a known document type.
     */
    public static boolean isDocument(MediaType mediaType) {
        return mediaType != null && (
                mediaType.equals(MediaType.APPLICATION_PDF) ||
                        mediaType.getSubtype().contains("msword") ||
                        mediaType.getSubtype().contains("vnd.openxmlformats-officedocument") ||
                        mediaType.equals(MediaType.TEXT_PLAIN) ||
                        mediaType.equals(MediaType.APPLICATION_XML) ||
                        mediaType.equals(MediaType.APPLICATION_JSON)
        );
    }

    /**
     * Check if a given file is an archive based on its MediaType.
     * @param mediaType The MediaType to check.
     * @return true if the MediaType is an archive type.
     */
    public static boolean isArchive(MediaType mediaType) {
        return mediaType != null && (
                mediaType.getSubtype().equalsIgnoreCase("zip") ||
                        mediaType.getSubtype().equalsIgnoreCase("x-7z-compressed") ||
                        mediaType.getSubtype().equalsIgnoreCase("x-tar") ||
                        mediaType.getSubtype().equalsIgnoreCase("gzip") ||
                        mediaType.getSubtype().equalsIgnoreCase("vnd.rar")
        );
    }

    public static boolean isVideoFile(String fileName) {
        MediaType mediaType = getMediaTypeForFileNameFromFileName(fileName);
        return isVideo(mediaType);
    }

    public static boolean isImageFile(String fileName) {
        MediaType mediaType = getMediaTypeForFileNameFromFileName(fileName);
        return isImage(mediaType);
    }

    public static boolean isAudioFile(String fileName) {
        MediaType mediaType = getMediaTypeForFileNameFromFileName(fileName);
        return isAudio(mediaType);
    }

    public static boolean isDocumentFile(String fileName) {
        MediaType mediaType = getMediaTypeForFileNameFromFileName(fileName);
        return isDocument(mediaType);
    }

    public static boolean isArchiveFile(String fileName) {
        MediaType mediaType = getMediaTypeForFileNameFromFileName(fileName);
        return isArchive(mediaType);
    }
}
