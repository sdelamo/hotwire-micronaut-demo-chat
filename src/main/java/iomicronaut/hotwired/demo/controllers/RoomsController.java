package iomicronaut.hotwired.demo.controllers;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import iomicronaut.hotwired.demo.models.Room;
import iomicronaut.hotwired.demo.repositories.RoomRepository;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller(UrlMappings.SLASH + RoomsController.ROOMS)
public class RoomsController extends ApplicationController<Long> {

    public static final String ROOM = "room";
    public static final String ROOMS = "rooms";


    private final RoomRepository roomRepository;

    public RoomsController(UrlMappings<Long> urlMappings,
                           RoomRepository roomRepository) {
        super(urlMappings);
        this.roomRepository = roomRepository;
    }

    @View(RoomsController.ROOMS + UrlMappings.SLASH + UrlMappings.INDEX + UrlMappings.HTML)
    @Get
    @Produces(MediaType.TEXT_HTML)
    Map<String, Object> index() {
        return Collections.singletonMap(ROOMS, roomRepository.findAll());
    }

    @View(RoomsController.ROOMS + UrlMappings.SLASH + UrlMappings.CREATE + UrlMappings.HTML)
    @Get(UrlMappings.SLASH + UrlMappings.CREATE)
    @Produces(MediaType.TEXT_HTML)
    Map<String, Object> create() {
        return Collections.emptyMap();
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post
    HttpResponse<?> save(@Body("name") String name) {
        return redirectTo(ROOMS, Action.SHOW, roomRepository.save(name).getId());
    }

    @View(RoomsController.ROOMS + UrlMappings.SLASH + UrlMappings.EDIT)
    @Get(UrlMappings.SLASH + UrlMappings.PATH_VARIABLE_ID + UrlMappings.SLASH + UrlMappings.EDIT)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> edit(@PathVariable Long id) {
        return modelResponse(id);
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post(UrlMappings.SLASH + UrlMappings.UPDATE)
    HttpResponse<?> update(@Body("id") Long id, @Body("name") String name) {
        roomRepository.update(id, name);
        return redirectTo(ROOMS, Action.SHOW, id);
    }

    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post(UrlMappings.SLASH + UrlMappings.PATH_VARIABLE_ID + UrlMappings.SLASH + UrlMappings.DELETE)
    HttpResponse<?> delete(@PathVariable Long id) {
        roomRepository.deleteById(id);
        return redirectTo(ROOMS);
    }

    @View(RoomsController.ROOMS + UrlMappings.SLASH + UrlMappings.SHOW)
    @Get(UrlMappings.SLASH + UrlMappings.PATH_VARIABLE_ID)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> show(@PathVariable Long id) {
        return modelResponse(roomRepository.getById(id).orElse(null));
    }

    @NonNull
    private Optional<Map<String, ?>> model(@Nullable Room room) {
        return room == null ? Optional.empty() : Optional.of(Collections.singletonMap(ROOM, room));
    }

    private Optional<Map<String, ?>> model(@NonNull Long id) {
        return model(roomRepository.findById(id).orElse(null));
    }

    private HttpResponse<?> modelResponse(@NonNull Long id) {
        return model(id).map(HttpResponse::ok).orElseGet(HttpResponse::notFound);
    }

    private HttpResponse<?> modelResponse(@Nullable Room room) {
        return model(room).map(HttpResponse::ok).orElseGet(HttpResponse::notFound);
    }
}
