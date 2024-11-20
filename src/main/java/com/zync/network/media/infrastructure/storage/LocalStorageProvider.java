package com.zync.network.media.infrastructure.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.Permission;
@Component
@Qualifier("localStorageProvider")
public class LocalStorageProvider extends AbstractStorageProvider{

    private Path root;


    @Override
    public String store(StoreArg arg) {
        try {
            File file = createFile(arg.path(), arg.permission());
            Files.copy(arg.stream(), file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private File createFile(String src, FilePermission permission) throws IOException {
        File file = root.resolve(src).toFile();
        if (permission == null){
            file.setExecutable(true);
            file.setReadable(true);
            file.setWritable(true);
        } else {
            file.setWritable(permission.writeable());
            file.setReadable(permission.readable());
            file.setExecutable(permission.executable());
        }
        file.createNewFile();
        return file;
    }



    @Override
    public String delete(String reference) {
        Path dest = loadAbsolutePath(reference);
        try {
            FileSystemUtils.deleteRecursively(dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reference;
    }

    private Path loadAbsolutePath(String path){
        return root.resolve(path).normalize();
    }
    @Override
    public OutputStream load(String reference) throws IOException {
        Path dest = loadAbsolutePath(reference);
        return Files.newOutputStream(dest, StandardOpenOption.READ);
    }

    @Override
    public String getSignedUrl(String reference) {
        // TODO (PC, 26/09/2024): To change the body of an implemented method
        return "";
    }

}
