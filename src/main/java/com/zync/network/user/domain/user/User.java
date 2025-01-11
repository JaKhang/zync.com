package com.zync.network.user.domain.user;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.BadRequestException;
import com.zync.network.core.exceptions.Error;
import com.zync.network.user.domain.events.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AggregateRoot {
    Name name;

    String fullName;
    @Column(unique = true, nullable = false)

    String username;
    ZID avatarId;
    ZID coverId;
    boolean verify;
    NameOrder nameOrder = NameOrder.LF;
    @Column(columnDefinition = "TEXT")
    String bio;
    LocalDate dateOfBirth;
    @ElementCollection
    Set<String> links = new HashSet<>();
    @ElementCollection
    Set<ZID> avatars = new HashSet<>();
    Locale locale;
    @Embedded
    PrivacySettings privacySettings;
    @ElementCollection
    Set<ZID> followers = new HashSet<>();
    @ElementCollection
    Set<ZID> followings = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    Set<ZID> pendingFollowRequests = new HashSet<>();
    Gender gender;

    @ElementCollection
    Set<ZID> blockedUsers = new HashSet<>();

    // Follow another actor
    public void follow(User user) {
        if (this.id.equals(user.getId())) {
            throw new IllegalArgumentException("A actor cannot follow themselves.");
        }
        if (this.followings.contains(user.getId())) {
            throw new IllegalStateException("Already following this actor.");
        }

        this.followings.add(user.getId());
        user.addFollower(this.id);

        // Trigger domain events
        this.registerEvents(new UserFollowedEvent(this.id, user.id, LocalDateTime.now()));
    }


    // Request to follow another actor
    public void requestToFollow(User targetUser) {

        if (this.id.equals(targetUser.getId())) {
            throw new IllegalArgumentException("A actor cannot follow themselves.");
        }
        if (targetUser.isPrivate() && !targetUser.pendingFollowRequests.contains(this.id)) {
            targetUser.addPendingRequest(this.id);
            registerEvents(new FollowRequestCreatedEvent(this.id, targetUser.getId(), LocalDateTime.now()));
        } else if (targetUser.isPublic()) {
            this.follow(targetUser);
        }
    }

    public boolean isPrivate() {
        return privacySettings.isPrivate();
    }

    public boolean canFollowRequestBeAutoApproved() {
        return !privacySettings.isPrivate();
    }

    public boolean isUserBlocked(ZID userId) {
        return this.blockedUsers.contains(userId);
    }

    User(ZID id, String username, Name name, ZID avatarId, ZID coverId, NameOrder nameOrder, String bio, LocalDate dateOfBirth, Set<String> links, Locale locale, Gender gender) {
        super(id);
        this.name = name;
        this.avatarId = avatarId;
        this.coverId = coverId;
        this.nameOrder = nameOrder;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
        this.links = links;
        this.locale =  locale;
        this.username = username;
        this.privacySettings = PrivacySettings.defaultSettings();
        this.gender = gender;
        this.fullName = getFormatedName();
    }

    public void addLink(String link){
        links.add(link);
    }

    public void setAvatarId(ZID avatarId) {
        avatars.add(avatarId);
        this.avatarId = avatarId;
    }



    public void makePrivate(){
        this.privacySettings = new PrivacySettings(true, this.privacySettings.hideActivity());

    }

    public void makePublic(){
        this.privacySettings = new PrivacySettings(false, this.privacySettings.hideActivity());

    }

    public void changeAvatar(ZID zid) {
        if (!avatars.contains(zid))
            throw new AbstractSystemException() {
                @Override
                public Error getError() {
                    return Error.BAD_ACTION;
                }
            };

        avatarId = zid;
    }

    // Accept a follow request
    public void acceptFollowRequest(User requester) {
        if (!pendingFollowRequests.contains(requester.id)) {
            throw new IllegalStateException("No pending request from this actor.");
        }
        pendingFollowRequests.remove(requester.id);
        this.followers.add(requester.id);
        requester.followings.add(this.id);
        registerEvents(new FollowRequestAcceptedEvent(requester.id,this.id, LocalDateTime.now()));
    }

    // Reject a follow request
    public void rejectFollowRequest(ZID followerId) {
        if (!pendingFollowRequests.contains(followerId)) {
            throw new IllegalStateException("No pending request from this actor.");
        }

        pendingFollowRequests.remove(followerId);
        registerEvents(new FollowRequestRejectedEvent(followerId, this.id));
    }

    private void addPendingRequest(ZID requesterId) {
        pendingFollowRequests.add(requesterId);
    }

    public boolean isPublic() {
        return !privacySettings.isPrivate();
    }


    // Internal method to add a follower
    private void addFollower(ZID followerId) {
        this.followers.add(followerId);
    }

    // Internal method to remove a follower
    private void removeFollower(ZID followerId) {
        this.followers.remove(followerId);
    }

    public String getFormatedName() {
        if (name.lastName() == null)
            return name.firstName();
        if (nameOrder == NameOrder.FL)
            return new StringJoiner(" ").add(name.firstName()).add(name.middleName()).add(name.lastName()).toString();
        return new StringJoiner(" ").add(name.lastName()).add(name.middleName()).add(name.firstName()).toString();

    }

    public void unfollow(User target) {
        this.followings.remove(target.id);
        target.followers.remove(this.id);
        registerEvents(new UnfollowedEvent(this.id, target.id));
    }

    public boolean isFollowRequesting(ZID selfId) {
        return pendingFollowRequests.contains(selfId);
    }

    public void removeFollowRequest(User target) {
        if (!target.getPendingFollowRequests().contains(this.id)) throw new BadRequestException();
        target.pendingFollowRequests.remove(this.id);
        registerEvents(new RemovedFollowRequestEvent(this.id, target.id));

    }

    public Relationship relationship(User user) {
        if (this.pendingFollowRequests.contains(user.id))
            return Relationship.PENDING;
        if (user.pendingFollowRequests.contains(this.id))
            return Relationship.REQUESTED;
        if (this.followings.contains(user.id))
            return Relationship.FOLLOWING;
        if (this.followers.contains(user.id))
            return Relationship.FOLLOWED;
        return Relationship.NONE;
    }

    public void update(Name name, String bio, List<String> links) {
        this.name = name;
        this.fullName = name.firstName();
        this.bio = bio;
        this.links = new HashSet<>(links);
    }
}
