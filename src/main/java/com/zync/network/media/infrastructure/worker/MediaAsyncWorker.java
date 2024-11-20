package com.zync.network.media.infrastructure.worker;

import org.checkerframework.checker.units.qual.A;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MediaAsyncWorker implements AsyncWorker{
    @Override
    @Async
    public void doWorkAsync(Runnable runnable) {
        runnable.run();
    }
}
