package com.zync.network.user.domain.user;

import com.zync.network.core.domain.ZID;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;

public class UserFactory {
    public static User crate(ZID id, String username, String firstName, String lastName, String middleName, LocalDate dateOfBirth, Locale locale, Gender gender) {
        return new User(id, username, new Name(firstName, middleName, lastName), null, null, NameOrder.LF, Strings.EMPTY, dateOfBirth, HashSet.newHashSet(0), locale, gender);
    }

    public static User merge(ZID id, String firstName, String lastName, String middle, LocalDate dateOfBirth, User user) {
        return user;
    }
}
