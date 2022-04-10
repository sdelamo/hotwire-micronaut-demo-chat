package io.micronaut.hotwired.demo.services;

import io.micronaut.context.annotation.DefaultImplementation;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.hotwired.demo.models.MessageForm;
import io.micronaut.hotwired.demo.models.RoomMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
@DefaultImplementation(DefaultMessageService.class)
public interface MessageService {
    @NonNull
    Optional<RoomMessage> save(@NonNull @NotNull @Valid MessageForm form);
}
