package com.zync.network.user.domain.profile;

import com.zync.network.core.domain.ZID;import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;

public class ProfileFactory {
    public static Profile crate(ZID id, String firstName, String lastName, String middle, LocalDate dateOfBirth, Locale locale){
        return new Profile(id, new Name(firstName, middle, lastName), null, null, NameOrder.LF, true, Strings.EMPTY, dateOfBirth, HashSet.newHashSet(0), locale);
    }

    public static Profile merge(ZID id, String firstName, String lastName, String middle, LocalDate dateOfBirth, Profile profile){
        return profile;
    }
}
