package org.koreait.mypokemon.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.entities.Member;
import org.koreait.pokemon.entities.Pokemon;

import java.util.List;

@Data
@Entity
@IdClass(MyPokemonId.class)
public class MyPokemon extends BaseEntity {
    @Id
    private Long seq; //선택할 포켓몬 도감 번호

    /*@Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Pokemon pokemon;*/

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Transient
    private int number;

}
