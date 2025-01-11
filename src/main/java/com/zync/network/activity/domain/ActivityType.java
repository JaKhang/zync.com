package com.zync.network.activity.domain;

public enum ActivityType {
    LIKE,             // User liked a post
    REPLY,            // Explicitly indicates a reply to a comment or post
    SHARE,            // User shared a post
    REPOST,           // User reposted a post
    FOLLOW,           // User followed another actor
    REQUEST_FOLLOW,   // User sent a follow request
    ACCEPT_FOLLOW,    // User accepted a follow request
    UNFOLLOW,         // User unfollowed another actor
    MENTION,          // User mentioned another actor
    BOOKMARK,         // User bookmarked a post
    REPORT,
    RECOMMEND_USER,
    RECOMMEND_POST
}
