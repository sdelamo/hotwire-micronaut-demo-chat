package iomicronaut.hotwired.demo.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Status;
import io.micronaut.views.View;
import iomicronaut.hotwired.demo.models.Message;
import iomicronaut.hotwired.demo.repositories.MessageRepository;
import iomicronaut.hotwired.demo.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller(UrlMappings.SLASH + RoomsController.ROOMS)
class MessagesController extends ApplicationController<Long> {
    private static final Logger LOG = LoggerFactory.getLogger(MessagesController.class);
    private static final String MESSAGES = "messages";
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

    public MessagesController(UrlMappings<Long> urlMappings,
                              MessageRepository messageRepository, RoomRepository roomRepository) {
        super(urlMappings);
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    @View(UrlMappings.SLASH + MESSAGES + UrlMappings.SLASH + UrlMappings.CREATE + UrlMappings.HTML)
    @Produces(MediaType.TEXT_HTML)
    @Get(UrlMappings.SLASH + UrlMappings.PATH_VARIABLE_ID + UrlMappings.SLASH + MESSAGES + UrlMappings.SLASH + UrlMappings.CREATE)
    HttpResponse<?> create(@PathVariable Long id) {
        return modelResponse(id);
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post(UrlMappings.SLASH + UrlMappings.PATH_VARIABLE_ID + UrlMappings.SLASH + MESSAGES)
    @Status(HttpStatus.OK)
    HttpResponse<?> save(@PathVariable Long id, @Body("content") String content) {
        return roomRepository.findById(id)
                .map(room -> {
                    messageRepository.save(new Message(content, room));
                    return redirectTo(RoomsController.ROOMS, Action.SHOW, id);
                }).orElseGet(HttpResponse::notFound);
    }

    private HttpResponse<?> modelResponse(@NonNull Long id) {
        return model(id).map(HttpResponse::ok).orElseGet(HttpResponse::notFound);
    }

    private Optional<Map<String, Object>> model(@NonNull Long id) {
        return roomRepository.findById(id)
                .map(room -> Collections.singletonMap(RoomsController.ROOM, room));
    }
}
