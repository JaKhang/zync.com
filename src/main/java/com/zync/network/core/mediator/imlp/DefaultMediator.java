package com.zync.network.core.mediator.imlp;

import com.zync.network.core.mediator.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DefaultMediator implements Mediator {

    ApplicationContext context;

    @Override
    @SuppressWarnings("unchecked")
    public void send(Notification notification) {
        if (notification == null) {
            throw new NullPointerException("The given request object cannot be null");
        }

        // Types used to find the exact handler that is required to handle the given request.
        // We are able to search all application beans by type from the Spring ApplicationContext.
        final Class<? extends Notification> requestType = notification.getClass();

        // Retrieve RequestHandler beans based on request and response types.
        final String[] beanNames = context.getBeanNamesForType(ResolvableType.forClassWithGenerics(NotificationHandler.class, requestType));

        // There must be a registered handler.
        if (beanNames.length == 0) {
            throw new IllegalStateException("No handlers seemed to be registered to handle the given request. Make sure the handler is defined and marked as a spring component");
        }

        // There may not be more than one handler.
        if (beanNames.length > 1) {
            throw new IllegalStateException("More than one handlers found. Only one handler per request is allowed.");
        }

        final NotificationHandler<Notification> requestHandler = (NotificationHandler<Notification>) context.getBean(beanNames[0]);

        requestHandler.handle(notification);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R send(Request<R> request) {
        if (request == null) {
            throw new NullPointerException("The given request object cannot be null");
        }

        // Types used to find the exact handler that is required to handle the given request.
        // We are able to search all application beans by type from the Spring ApplicationContext.
        final Class<? extends Request> requestType = request.getClass();
        final Class<R> responseType = (Class<R>) ((ParameterizedType) requestType.getGenericInterfaces()[0]).getActualTypeArguments()[0];

        // Retrieve RequestHandler beans based on request and response types.
        final String[] beanNames = context.getBeanNamesForType(ResolvableType.forClassWithGenerics(RequestHandler.class, requestType, responseType));

        // There must be a registered handler.
        if (beanNames.length == 0) {
            throw new IllegalStateException("No handlers seemed to be registered to handle the given request. Make sure the handler is defined and marked as a spring component");
        }

        // There may not be more than one handler.
        if (beanNames.length > 1) {
            throw new IllegalStateException("More than one handlers found. Only one handler per request is allowed.");
        }

        final RequestHandler<Request<R>, R> requestHandler = (RequestHandler<Request<R>, R>) context.getBean(beanNames[0]);

        return requestHandler.handle(request);
    }
}
