package com.api.sample.restful.service;

import com.api.sample.restful.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public List<HashMap<String, Object>> findByNameStartsWith(String name) {
        return pokemonRepository.findByIdentifierStartsWith(name);
    }
}
