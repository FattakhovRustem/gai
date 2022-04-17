package ru.gai.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gai.service.NumberService;

@RestController
@RequestMapping("/number")
public class NumberController {

    private final NumberService numberService;

    public NumberController(@Qualifier(value = "number-service-new") NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping(value = "/random")
    String random() {
        String result = numberService.random();
        return result;
    }

    @GetMapping(value = "/next")
    String next() {
        String result = numberService.next();
        return result;
    }

}
