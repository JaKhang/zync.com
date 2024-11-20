package com.zync.network.media.infrastructure.storage;

import com.zync.network.core.domain.ZID;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public abstract class AbstractStorageProvider implements StorageProvider{

    @Value("${application.media.storage.tmp}")
    private String tmp;

    @Override
    public Path saveToTmp(String name, InputStream is) {
        Path dest = Path.of(tmp, name);
        try {
            Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Store to tmp error !");
        }
        return dest;
    }
}
