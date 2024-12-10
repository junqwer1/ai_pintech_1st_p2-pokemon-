package org.koreait.global.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // 모든 값을 매개변수 상태로?
public class ListData<T> {
    private T items; // 목록 데이터
    private Pagination pagination; // 페이징 기초 데이터
}
