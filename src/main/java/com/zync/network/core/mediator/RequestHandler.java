package com.zync.network.core.mediator;

public interface RequestHandler <T extends Request<R>,R>{
    R handle(T request);
}
