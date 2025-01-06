package org.koreait.mypokemon.services;

import lombok.RequiredArgsConstructor;
import org.koreait.member.libs.MemberUtil;
import org.koreait.mypokemon.repositories.MyPokemonRepository;
import org.koreait.pokemon.services.PokemonInfoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class MypokemonDeleteService {
    private PokemonInfoService pokemonInfoService;
    private MyPokemonRepository repository;
    private MemberUtil memberUtil;

}
