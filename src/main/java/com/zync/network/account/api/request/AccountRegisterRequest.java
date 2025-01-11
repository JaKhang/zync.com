package com.zync.network.account.api.request;


import com.zync.network.core.domain.ZID;

import java.util.Locale;
import java.util.Set;

public record AccountRegisterRequest(String email, String password) {

}
