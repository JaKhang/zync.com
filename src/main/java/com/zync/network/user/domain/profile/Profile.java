package com.zync.network.user.domain.profile;

import com.zync.network.core.domain.ZID;import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.exceptions.AbstractSystemException;
import com.zync.network.core.exceptions.Error;
import com.zync.network.media.infrastructure.storage.StorageException;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends AggregateRoot {
    Name name;
    ZID avatarId;
    ZID coverId;
    NameOrder nameOrder = NameOrder.LF;
    boolean isPublic;
    String bio;
    LocalDate dateOfBirth;
    @ElementCollection
    Set<String> links = new HashSet<>();
    @ElementCollection
    Set<ZID> avatars = new HashSet<>();
    Locale locale;

    Profile(ZID id, Name name, ZID avatarId, ZID coverId, NameOrder nameOrder, boolean isPublic, String bio, LocalDate dateOfBirth, Set<String> links, Locale locale) {
        super(id);
        this.name = name;
        this.avatarId = avatarId;
        this.coverId = coverId;
        this.nameOrder = nameOrder;
        this.isPublic = isPublic;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
        this.links = links;
        this.locale =  locale;
    }

    public void addLink(String link){
        links.add(link);
    }

    public void setAvatarId(ZID avatarId) {
        avatars.add(avatarId);
        this.avatarId = avatarId;
    }



    public void makePrivate(){
        this.isPublic = false;

    }

    public void makePublic(){
        this.isPublic = true;
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
}
