package org.koreait.pokemon.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.utils.LogNode;
import org.koreait.global.libs.Utils;
import org.koreait.global.paging.ListData;
import org.koreait.global.paging.Pagination;
import org.koreait.pokemon.controllers.PokemonSearch;
import org.koreait.pokemon.entities.Pokemon;
import org.koreait.pokemon.entities.QPokemon;
import org.koreait.pokemon.exceptions.PokemonNotFoundException;
import org.koreait.pokemon.repositories.PokemonRepository;
import org.koreait.wishlist.constants.WishType;
import org.koreait.wishlist.services.WishService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;

import java.util.*;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Lazy
@Service
@RequiredArgsConstructor
public class PokemonInfoService {

    private final PokemonRepository pokemonRepository;
    private final HttpServletRequest request;
    private final Utils utils;
    private final JPAQueryFactory queryFactory;
    private final WishService wishService;

    /**
     * 포켓몬 목록 조회
     * */
    public ListData<Pokemon> getList(PokemonSearch search) {

        int page = Math.max(search.getPage(), 1); // 페이지 번호
        int limit = search.getLimit(); // 한페이지 당 레코드 갯수
        limit = limit < 1 ? 18 : limit; // limit -1경우 18

        QPokemon pokemon = QPokemon.pokemon;

        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        String skey = search.getSkey();
        if (StringUtils.hasText(skey)) { // 키워드 검색
            andBuilder.and(pokemon.name
                    .concat(pokemon.nameEn)
                    .concat(pokemon.flavorText)
                    .contains(skey));
        }

        List<Long> seq = search.getSeq();
        if (seq != null && !seq.isEmpty()) {
            andBuilder.and(pokemon.seq.in(seq));
        }
        /* 검색 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(asc("seq")));

        Page<Pokemon> data = pokemonRepository.findAll(andBuilder, pageable);
        List<Pokemon> items = data.getContent(); // 조회된 목록

//        추가 정보 처리
        items.forEach(this::addInfo);

        int ranges = utils.isMobile() ? 5 : 10;
        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), ranges, limit, request);

        return new ListData<>(items, pagination);
    }

    // 내가 찜한 포켓몬 목록
    public ListData<Pokemon> getMyPokemons(PokemonSearch search) {
        List<Long> seq = wishService.getMyWish(WishType.POKEMON);
        if (seq == null || seq.isEmpty()) {
            return new ListData<>();
        }

        search.setSeq(seq);

        return getList(search);
    }

    // 내 6마리 포켓몬 목록
    public List<Pokemon> getPokemons() {
        List<Long> seq = wishService.getMyWish(WishType.MYPOKEMON);
        if (seq == null || seq.isEmpty()) {
            return List.of();
        }
        PokemonSearch search = new PokemonSearch();
        search.setSeq(seq);

        ListData<Pokemon> data = getList(search);

        List<Pokemon> items = data.getItems();

        List<Pokemon> items2 = seq.stream().map(s -> {
            for (Pokemon item : items) {
                if (item.getSeq().equals(s)) return item;
            }

            return null;
        }).filter(item -> item != null).toList();


        return items2;
    }

    /**
     * 포켓몬 단일 조회
     * */
    public Pokemon get(Long seq) {

        Pokemon item = pokemonRepository.findById(seq).orElseThrow(PokemonNotFoundException::new);

        // 추가 정보 처리
        addInfo(item, true);

        return item;
    }

    /**
     * 추가 정보 처리
     * */
    private void addInfo(Pokemon item) {
//        abilities
        String abilities = item.getAbilities();
        if (StringUtils.hasText(abilities)) {
            item.set_abilities(Arrays.stream(abilities.split("\\|\\|")).toList());
        }

//        types
        String types = item.getTypes();
        if (StringUtils.hasText(types)) {
            item.set_types(Arrays.stream(types.split("\\|\\|")).toList());
        }
    }

    private void addInfo(Pokemon item, boolean isView) {
        addInfo(item);
        if (!isView) return;

        long seq = item.getSeq();
        long lastSeq = getLastSeq();

//        이전 포켓몬 정보 - prevItem
        long prevSeq = seq - 1L;
        prevSeq = prevSeq < 1L ? lastSeq : prevSeq;

//        다음 포켓몬 정보 - nextItem
        long nextSeq = seq + 1L;
        nextSeq = nextSeq > lastSeq ? 1L : nextSeq;

        QPokemon pokemon = QPokemon.pokemon;
        List<Pokemon> items = (List<Pokemon>) pokemonRepository.findAll(pokemon.seq.in(prevSeq, nextSeq));

        Map<String, Object> prevItem = new HashMap<>();
        Map<String, Object> nextItem = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            Pokemon _item = items.get(i);

            Map<String, Object> data = _item.getSeq().longValue() == prevSeq ? prevItem : nextItem;
            data.put("seq", _item.getSeq());
            data.put("name", _item.getName());
            data.put("nameEn", _item.getNameEn());
        }

        item.setPrevItem(prevItem);
        item.setNextItem(nextItem);

    }

    private Long getLastSeq() {
        QPokemon pokemon = QPokemon.pokemon;

        return queryFactory.select(pokemon.seq.max())
                .from(pokemon)
                .fetchFirst();
    }
}
