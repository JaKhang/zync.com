package com.zync.network.core.mediator;

public interface RequestHandler <T extends MediatorRequest<R>,R>{
    R handle(T request);
}
