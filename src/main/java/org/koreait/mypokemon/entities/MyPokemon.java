package org.koreait.mypokemon.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;
import org.koreait.pokemon.entities.Pokemon;

import java.util.List;

@Data
@Entity
public class MyPokemon extends BaseEntity {
    /*@ManyToOne(fetch = FetchType.LAZY)
    private Pokemon pokemon;

    @Id
    private */
    @Id
    private Long seq;
}
