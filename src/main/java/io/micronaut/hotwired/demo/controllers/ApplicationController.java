package io.micronaut.hotwired.demo.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.views.turbo.http.TurboMediaType;

import java.net.URI;

public abstract class ApplicationController {
    @NonNull
    protected HttpResponse<?> redirectTo(@NonNull CharSequence uri,
                                         @NonNull Long id) {
        return HttpResponse.seeOther(UriBuilder.of(uri)
                .path("" + id)
                .build());
    }

    protected boolean accepts(@NonNull HttpRequest<?> request,
                              @NonNull MediaType mediaType) {
        return request.accept().stream().anyMatch(mt -> mt.equals(mediaType));
    }
}
