package com.zync.network.media.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public interface StorageProvider {
    String store(StoreArg arg);
    String delete(String reference);
    OutputStream load(String reference) throws IOException;
    String getSignedUrl(String reference);
    Path saveToTmp(String name, InputStream is);
}
