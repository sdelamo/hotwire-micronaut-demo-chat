package iomicronaut.hotwired.demo.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;

public abstract class ApplicationController<ID> {
    private final UrlMappings<ID> urlMappings;

    public ApplicationController(UrlMappings<ID> urlMappings) {
        this.urlMappings = urlMappings;
    }

    @NonNull
    public HttpResponse<?> redirectTo(@NonNull String resource) {
        return HttpResponse.seeOther(urlMappings.index(resource));
    }

    public HttpResponse<?> redirectTo(@NonNull String resource, @NonNull Action action, ID id) {
        switch (action) {
            case EDIT:
                return HttpResponse.seeOther(urlMappings.edit(resource, id));
            case SHOW:
                return HttpResponse.seeOther(urlMappings.show(resource, id));
            default:
                throw new RuntimeException("should not reach this path");
        }

    }
}
