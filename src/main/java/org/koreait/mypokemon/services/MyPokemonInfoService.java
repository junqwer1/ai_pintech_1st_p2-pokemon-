package org.koreait.mypokemon.services;

import lombok.RequiredArgsConstructor;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.member.repositories.MemberRepository;
import org.koreait.mypokemon.entities.MyPokemon;
import org.koreait.mypokemon.entities.MyPokemonId;
import org.koreait.mypokemon.repositories.MyPokemonRepository;
import org.koreait.pokemon.services.PokemonInfoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Lazy
@Service
@RequiredArgsConstructor
public class MyPokemonInfoService {

    private final MemberUtil memberUtil;
    private final MyPokemonRepository repository;
    private final PokemonInfoService pokemonInfoService;
    private final MemberRepository memberRepository;

    public void process(String mode, Long seq) {
        if (!memberUtil.isLogin()){ return;}

        mode = StringUtils.hasText(mode) ? mode : "add";
        Member member = memberUtil.getMember();
        member = memberRepository.findByEmail(member.getEmail()).orElse(null);
        try {
            if (mode.equals("remove")) { // 나의 포켓몬 해제
                MyPokemonId pokemonId = new MyPokemonId(seq, member);
                repository.deleteById(pokemonId);
            } else { // 나의 포켓몬 추가
                MyPokemon myPokemon = new MyPokemon();
                myPokemon.setSeq(seq);
                myPokemon.setMember(member);
                repository.save(myPokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void paty(Long seq) {
        MyPokemon myPokemon = pokemonInfoService.getMyPokemons();
    }*/


}
