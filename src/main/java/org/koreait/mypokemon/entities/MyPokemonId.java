package org.koreait.mypokemon.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.koreait.member.entities.Member;
import org.koreait.pokemon.entities.Pokemon;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MyPokemonId {
    private Long seq;
    private Member member;
}
