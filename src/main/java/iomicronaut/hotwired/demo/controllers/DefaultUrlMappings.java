package iomicronaut.hotwired.demo.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import java.net.URI;

@Singleton
public class DefaultUrlMappings<ID> implements UrlMappings<ID> {

    @NonNull
    public UriBuilder uriBuilder(@NonNull String resource) {
        if (!resource.startsWith(SLASH)) {
            return UriBuilder.of(SLASH + resource);
        }
        return UriBuilder.of(resource);
    }

    @NonNull
    public URI index(@NonNull String resource) {
        return uriBuilder(resource).build();
    }

    @Override
    public URI show(String resource, @NonNull ID id) {
        return uriBuilder(resource).path(id.toString()).build();
    }

    @Override
    public URI edit(String resource, @NonNull ID id) {
        return uriBuilder(resource).path(id.toString()).path(EDIT).build();
    }

    @NonNull
    public URI create(@NonNull String resource) {
        return uriBuilder(resource).path(CREATE).build();
    }
}
