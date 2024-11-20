package com.zync.network.user.domain.relationship;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.ZID;


public class Relationship extends AggregateRoot {
    ZID follower;
    ZID followee;

}
