package com.api.sample.restful.api;

import com.api.sample.restful.service.PokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pokemon")
@Slf4j
@RequiredArgsConstructor
public class PokemonAPI {

    private final PokemonService pokemonService;

    @GetMapping()
    public ResponseEntity<List<HashMap<String, Object>>> find(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(pokemonService.findByNameStartsWith(name));
    }
}
