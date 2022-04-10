package io.micronaut.hotwired.demo.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.hotwired.demo.models.MessageForm;
import io.micronaut.hotwired.demo.models.RoomMessage;
import io.micronaut.hotwired.demo.services.MessageService;
import io.micronaut.http.HttpRequest;
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
import io.micronaut.hotwired.demo.repositories.RoomRepository;
import io.micronaut.views.turbo.TurboStreamAction;
import io.micronaut.views.turbo.TurboView;
import io.micronaut.views.turbo.http.TurboMediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller("/rooms")
class MessagesController extends ApplicationController {
    private static final Logger LOG = LoggerFactory.getLogger(MessagesController.class);
    private final MessageService messageService;
    private final RoomRepository roomRepository;

    public MessagesController(MessageService messageService,
                              RoomRepository roomRepository) {
        this.messageService = messageService;
        this.roomRepository = roomRepository;
    }

    @View("/messages/create")
    @Produces(MediaType.TEXT_HTML)
    @Get("/{id}/messages/create")
    HttpResponse<?> create(@PathVariable Long id) {
        return modelResponse(id);
    }

    @TurboView(value = "/messages/_message.html", action = TurboStreamAction.APPEND, targetDomId = "messages")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/{id}/messages")
    @Status(HttpStatus.OK)
    HttpResponse<?> save(@PathVariable Long id,
                         @Body("content") String content,
                         HttpRequest<?> request) {
        Optional<RoomMessage> roomMessageOptional = messageService.save(new MessageForm(id, content));
        return roomMessageOptional.map(roomMessage -> {
            if (accepts(request, TurboMediaType.TURBO_STREAM_TYPE)) {
                return HttpResponse.ok(Collections.singletonMap("message", roomMessage));
            }
            return redirectTo("/rooms", id);
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
