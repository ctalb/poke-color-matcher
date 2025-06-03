package io.github.ctalb.pokecolormatcher.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PokemonController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
