package org.koreait.mypokemon.services;

import lombok.RequiredArgsConstructor;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.member.repositories.MemberRepository;
import org.koreait.mypokemon.entities.MyPokemon;
import org.koreait.mypokemon.entities.MyPokemonId;
import org.koreait.mypokemon.repositories.MyPokemonRepository;
import org.koreait.pokemon.entities.Pokemon;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Lazy
@Service
@RequiredArgsConstructor
public class MyPokemonService {
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;
    private final MyPokemonRepository myPokemonRepository;
    private final MyPokemonRepository repository;

    public void process(String mode, Long seq) {
        if (!memberUtil.isLogin()) {
            return;
        }

        mode = StringUtils.hasText(mode) ? mode : "add";
        Member member = memberUtil.getMember();
        member = memberRepository.findByEmail(member.getEmail()).orElse(null);
        try {
            if (mode.equals("remove")) { // 마이포켓몬 해제
                MyPokemonId myPokemonId = new MyPokemonId(seq, member);
                myPokemonRepository.deleteById(myPokemonId);
            } else { // 마이포켓몬 추가
                MyPokemon myPokemon = new MyPokemon();
                myPokemon.setSeq(seq);
                myPokemon.setMember(member);
                repository.save(myPokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void viewPokemon(Long seq,)

}
