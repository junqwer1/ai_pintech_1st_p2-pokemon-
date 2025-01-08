package org.koreait.mypokemon.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.member.repositories.MemberRepository;
import org.koreait.mypokemon.entities.MyPokemon;
import org.koreait.mypokemon.entities.MyPokemonId;
import org.koreait.mypokemon.entities.QMyPokemon;
import org.koreait.mypokemon.repositories.MyPokemonRepository;
import org.koreait.pokemon.entities.Pokemon;
import org.koreait.pokemon.services.PokemonInfoService;
import org.koreait.wishlist.constants.WishType;
import org.koreait.wishlist.entities.QWish;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class MyPokemonService {
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;
    private final MyPokemonRepository myPokemonRepository;
    private final MyPokemonRepository repository;
    private final JPAQueryFactory queryFactory;
    private final SpringTemplateEngine templateEngine;
    private final PokemonInfoService pokemonInfoService;

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

    public List<Long> getMyPokemon() {
        if (!memberUtil.isLogin()) {
            return List.of();
        }
        QMyPokemon myPokemon = QMyPokemon.myPokemon;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(myPokemon.member.eq(memberUtil.getMember()));
        List<Long> items = queryFactory.select(myPokemon.seq)
                .from(myPokemon)
                .where(builder)
                .fetch();

        return items;
    }

    public String showMyPokemon(Long seq) {
        return showMyPokemon(seq, null);
    }

    public String showMyPokemon(Long seq, List<Long> myPokemons) {
        myPokemons = myPokemons == null || myPokemons.isEmpty() ? getMyPokemon() : myPokemons;

        Context context = new Context();
        context.setVariable("seq", seq);
        context.setVariable("myPokemons", myPokemons);
        context.setVariable("isMyPokemon", myPokemons.contains(seq));
        context.setVariable("isLogin", memberUtil.isLogin());

        return templateEngine.process("common/_mypokemon", context);
    }

    public void viewPokemon(Long seq) {

    }

}
