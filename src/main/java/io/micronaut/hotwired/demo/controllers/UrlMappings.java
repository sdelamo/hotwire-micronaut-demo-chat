package io.micronaut.hotwired.demo.controllers;

import io.micronaut.context.annotation.DefaultImplementation;
import io.micronaut.core.annotation.NonNull;

import java.net.URI;

@DefaultImplementation(DefaultUrlMappings.class)
public interface UrlMappings<ID> {
    String SLASH = "/";

    String DELETE = "delete";

    String HTML = ".html";

    String CREATE = "create";

    String UPDATE = "update";

    String SHOW = "show";

    String EDIT = "edit";

    String INDEX = "index";

    String PATH_VARIABLE_ID = "{id}";

    @NonNull
    URI index(@NonNull String resource);

    @NonNull
    URI create(@NonNull String resource);

    @NonNull
    URI show(@NonNull String resource, @NonNull ID id);

    @NonNull
    URI edit(@NonNull String resource, @NonNull ID id);
}
