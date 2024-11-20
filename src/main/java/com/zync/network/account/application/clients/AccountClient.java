package com.zync.network.account.application.clients;

import com.zync.network.core.domain.ZID;

public interface AccountClient {
    ZID register(AccountRegisterRequest request);
}
