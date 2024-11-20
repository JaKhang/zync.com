package com.zync.network.media.infrastructure.worker;

public interface AsyncWorker {
    void doWorkAsync(Runnable runnable);
}
