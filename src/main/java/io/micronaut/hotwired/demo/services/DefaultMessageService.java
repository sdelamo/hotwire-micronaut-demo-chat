package io.micronaut.hotwired.demo.services;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.hotwired.demo.entities.Message;
import io.micronaut.hotwired.demo.models.MessageForm;
import io.micronaut.hotwired.demo.models.RoomMessage;
import io.micronaut.hotwired.demo.repositories.MessageRepository;
import io.micronaut.hotwired.demo.repositories.RoomRepository;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class DefaultMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final ApplicationEventPublisher<RoomMessage> eventPublisher;

    public DefaultMessageService(MessageRepository messageRepository,
                                 RoomRepository roomRepository,
                                 ApplicationEventPublisher<RoomMessage> eventPublisher) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @NonNull
    public Optional<RoomMessage> save(@NonNull @NotNull @Valid MessageForm form) {
        return roomRepository.findById(form.getRoom())
                .map(room -> {
                    Message message = messageRepository.save(new Message(form.getContent(), room));
                    RoomMessage roomMessage = new RoomMessage(message.getId(),
                            form.getRoom(),
                            form.getContent(),
                            message.getDateCreated());
                    eventPublisher.publishEvent(roomMessage);
                    return roomMessage;
                });
    }
}
