package com.zync.network.media.infrastructure.storage;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.MethodNotImplementException;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.StringJoiner;

@Component
@Qualifier("minioStorageProvider")
@Primary
@RequiredArgsConstructor
public class MinioStorageProvider extends AbstractStorageProvider{
    private final MinioClient minioClient;
    private static final String PATH_SEPARATOR = "/";
   @Override
    // path /<<bucket>>/<<sub path>>/filename.ext
    public String store(StoreArg arg) {
        S3FilePath s3FilePath = extract(arg.path());
        var param = PutObjectArgs.builder()
                .stream(arg.stream(), arg.size(), 0L)
                .contentType(arg.contentType())
                .bucket(s3FilePath.bucket())
                .object(s3FilePath.object())
                .build();
        try {
            var rp = minioClient.putObject(param);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
        return arg.path();
    }

    private static S3FilePath extract(String path){
        if (path.startsWith(PATH_SEPARATOR)){
            path = path.substring(1);
        }

        var paths = path.split(PATH_SEPARATOR);
        if (paths.length < 2)
            throw new StorageException("file path invalid");
        String bucket = paths[0];
        String fileName = paths[paths.length - 1];
        StringJoiner joiner = new StringJoiner(PATH_SEPARATOR);
        for (int i = 1; i < paths.length - 1; i++) {
            joiner.add(paths[i]);
        }
        String rPath = joiner.toString();
        return new S3FilePath(bucket, rPath , fileName);
    }


    @Override
    public String delete(String reference) {
        S3FilePath s3FilePath = extract(reference);
        minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket(s3FilePath.bucket())
                        .bucket(s3FilePath.filename)
                        .build()
        );
        return reference;
    }

    @Override
    public OutputStream load(String reference) throws IOException {
        throw new MethodNotImplementException();
    }

    @Override
    public String getSignedUrl(String reference) {
        throw new MethodNotImplementException();
    }

    private record S3FilePath(String bucket, String path, String filename){

        public String object() {
            if (Strings.isEmpty(path))
                return filename;
            return path + PATH_SEPARATOR + filename;
        }
    }
}
