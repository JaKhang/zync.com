package com.zync.network.post.api.models;

import com.zync.network.core.domain.ZID;
import com.zync.network.post.domain.Visibility;

import java.util.List;
import java.util.Set;

public record PostRequest(String content,
                          List<ZID> mediaIds,
                          Visibility visibility) {

}
