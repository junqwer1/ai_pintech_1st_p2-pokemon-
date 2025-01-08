package org.koreait.mypokemon.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.entities.Member;

@Data
@Entity
@IdClass(MyPokemonId.class)
public class MyPokemon extends BaseEntity {
    @Id
    private Long seq; //선택할 포켓몬 도감 번호

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
