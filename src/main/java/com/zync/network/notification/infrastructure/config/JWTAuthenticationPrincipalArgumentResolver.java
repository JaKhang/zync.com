package com.zync.network.notification.infrastructure.config;

import com.zync.network.core.security.JwtPrincipal;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.security.messaging.context.AuthenticationPrincipalArgumentResolver;

public class JWTAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationPrincipalArgumentResolver resolver;

    public JWTAuthenticationPrincipalArgumentResolver(AuthenticationPrincipalArgumentResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return resolver.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        Object argument = resolver.resolveArgument(parameter, message);
        System.out.println(argument);
        if (argument instanceof JwtPrincipal principal)
            return principal.id();
        return argument;
    }
}
