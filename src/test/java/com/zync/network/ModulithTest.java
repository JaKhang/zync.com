package com.zync.network;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithTest {

    ApplicationModules modules = ApplicationModules.of(ZyncSocialNetworkApplication.class);

    @Test
    void verify() {
        System.out.println(modules.toString());
        System.out.println("test");
        modules.verify();

    }
}
