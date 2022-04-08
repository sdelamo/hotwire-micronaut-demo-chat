package iomicronaut.hotwired.demo.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class HomeController extends ApplicationController {

    public HomeController(UrlMappings urlMappings) {
        super(urlMappings);
    }

    @Get
    HttpResponse<?> index() {
        return redirectTo(RoomsController.ROOMS);
    }
}
