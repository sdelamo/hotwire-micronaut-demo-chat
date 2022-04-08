package iomicronaut.hotwired.demo.controllers;

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
import iomicronaut.hotwired.demo.repositories.RoomRepository;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

@Controller(UrlMappings.SLASH + RoomsController.ROOMS)
public class RoomsController extends ApplicationController<Long> {

    public static final String ROOM = "room";
    public static final String ROOMS = "rooms";
    public static final String PATH_VARIABLE_ID = "{id}";

    private final RoomRepository roomRepository;

    public RoomsController(UrlMappings urlMappings,
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

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post
    HttpResponse<?> save(@Body("name") String name) {
        return redirectTo(ROOMS, Action.SHOW, roomRepository.save(name).getId());
    }

    @View(RoomsController.ROOMS + UrlMappings.SLASH + UrlMappings.SHOW)
    @Get(UrlMappings.SLASH + PATH_VARIABLE_ID)
    @Produces(MediaType.TEXT_HTML)
    HttpResponse<?> show(@PathVariable Long id) {
        return roomRepository.findById(id).map(room -> HttpResponse.ok(Collections.singletonMap("room", room)))
                .orElseGet(HttpResponse::notFound);
    }

    @Consumes(MediaType.TEXT_HTML)
    @Post(UrlMappings.SLASH + PATH_VARIABLE_ID + UrlMappings.SLASH + UrlMappings.DELETE)
    HttpResponse<?> delete(@Body("id") Long id) {
        roomRepository.deleteById(id);
        return redirectTo(ROOMS);
    }
}
