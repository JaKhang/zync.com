package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UploadAllMediaCommandHandler implements RequestHandler<UploadAllMediaCommand, List<ZID>> {

    private final Mediator mediator;

    @Override
    @Transactional
    public List<ZID> handle(UploadAllMediaCommand request) {

        return request.mediaFiles().stream().map((m) -> mediator.send(new UploadMediaCommand(request.userId(), m))).toList();


    }

}
