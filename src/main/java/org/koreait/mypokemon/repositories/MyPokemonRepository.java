package org.koreait.mypokemon.repositories;

import org.koreait.mypokemon.entities.MyPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MyPokemonRepository extends JpaRepository<MyPokemon, Long>, QuerydslPredicateExecutor<MyPokemon> {
}
